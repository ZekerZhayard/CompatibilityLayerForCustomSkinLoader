package io.github.zekerzhayard.cflcsl.gradle;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.gradle.common.BasePlugin;
import net.minecraftforge.gradle.delayed.DelayedFile;
import net.minecraftforge.gradle.tasks.ProcessJarTask;
import net.minecraftforge.gradle.user.UserConstants;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.NonNullApi;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;

@NonNullApi
public class DeobfPlugin implements Plugin<Project> {
    private final static Map<String, String> CONFIG = ImmutableMap.of("deobfCompile", "compile", "deobfProvided", "provided");

    @Override
    public void apply(Project project) {
        project.getRepositories().maven(mar -> {
            try {
                mar.setUrl(new URI(project.getProjectDir().getAbsoluteFile().toURI() + "/.gradle/remap-repo"));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        project.getConfigurations().create("provided", c -> project.getConfigurations().getByName("compile").extendsFrom(c));
        for (String name : CONFIG.keySet()) {
            project.getConfigurations().create(name);
        }

        project.afterEvaluate(p -> {
            for (String name : CONFIG.keySet()) {
                this.resolveArtifact(project.getConfigurations().getByName(name), p);
            }
        });
    }

    private void resolveArtifact(Configuration config, Project p) {
        BasePlugin<?> basePlugin = (BasePlugin<?>) p.getPlugins().getPlugin("forge");
        for (ResolvedArtifact artifact : config.getResolvedConfiguration().getResolvedArtifacts()) {
            String group = artifact.getModuleVersion().getId().getGroup();
            String name = artifact.getModuleVersion().getId().getName();
            String version = artifact.getModuleVersion().getId().getVersion();
            String classifier = artifact.getClassifier();
            String extension = artifact.getExtension();

            File deobfFile = new File(p.getProjectDir(), ".gradle/remap-repo/deobf/" + group.replace('.', '/') + "/" + name + "/" + version + "/" + name + "-" + version + (StringUtils.isBlank(classifier) ? "" : "-" + classifier) + (StringUtils.isBlank(extension) ? ".jar" : "." + extension));
            deobfFile.getParentFile().mkdirs();
            ProcessJarTask task = (ProcessJarTask) p.task(ImmutableMap.of("type", ProcessJarTask.class), "reobfMod_" + artifact.hashCode());
            task.setInJar(new DelayedFile(artifact.getFile()));
            task.setOutCleanJar(new DelayedFile(deobfFile));
            task.setExceptorCfg(new DelayedFile(p, UserConstants.EXC_SRG, basePlugin));
            task.setExceptorJson(new DelayedFile(p, "{USER_DEV}/conf/exceptor.json", basePlugin));
            task.setSrg(new DelayedFile(p, UserConstants.DEOBF_SRG_MCP_SRG, basePlugin));
            task.dependsOn("genSrgs");
            task.execute();

            p.getDependencies().add("compile", "deobf." + group + ":" + name + ":" + version + (StringUtils.isBlank(classifier) ? "" : ":" + classifier) + (StringUtils.isBlank(extension) ? "" : "@" + extension));
        }
    }
}
