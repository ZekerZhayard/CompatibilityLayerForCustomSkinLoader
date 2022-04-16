package io.github.zekerzhayard.cflcsl.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraftforge.gradle.delayed.DelayedFile;
import net.minecraftforge.gradle.tasks.ExtractConfigTask;
import net.minecraftforge.gradle.tasks.GenSrgTask;
import net.minecraftforge.gradle.tasks.ProcessJarTask;
import net.minecraftforge.gradle.tasks.abstractutil.EtagDownloadTask;
import net.minecraftforge.gradle.user.UserConstants;
import net.minecraftforge.gradle.user.patch.ForgeUserPlugin;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.NonNullApi;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.internal.tasks.ContextAwareTaskAction;

@NonNullApi
public class DeobfPlugin implements Plugin<Project> {
    public static Project project;
    public static ForgeUserPlugin forge;
    public static File srgForge;
    private final static Map<String, String> CONFIG = ImmutableMap.of("deobfCompile", "compile", "deobfProvided", "compile", "deobfCompileOnly", "compileOnly");

    private boolean preTaskRun = false;

    @Override
    public void apply(Project project) {
        DeobfPlugin.project = project;
        DeobfPlugin.forge = (ForgeUserPlugin) project.getPlugins().getPlugin("forge");
        ExtractExtension ext = project.getExtensions().create("extract", ExtractExtension.class, project);

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
            this.unmapForge(p);

            for (String name : CONFIG.keySet()) {
                this.resolveArtifact(p.getConfigurations().getByName(name), p, ext);
            }
        });
    }

    // remap minecraft and forge from mcp to srg
    private void unmapForge(Project p) {
        ProcessJarTask task = (ProcessJarTask) p.task(ImmutableMap.of("type", ProcessJarTask.class), "unmapForge");

        srgForge = new DelayedFile(p, p.getProjectDir().getAbsolutePath() + "/.gradle/remap-repo/{API_NAME}-{API_VERSION}-srg.jar", forge).resolveDelayed();
        srgForge.getParentFile().mkdirs();
        task.setInJar(new DelayedFile(p, "{USER_DEV}/binaries.jar", forge));
        task.setOutCleanJar(new DelayedFile(srgForge));
        task.setExceptorCfg(new DelayedFile(p, UserConstants.EXC_SRG, forge));
        task.setExceptorJson(new DelayedFile(p, "{USER_DEV}/conf/exceptor.json", forge));
        task.setSrg(new DelayedFile(p, UserConstants.DEOBF_SRG_SRG, forge));

        this.runProcessJarTask(p, task);
    }

    private void resolveArtifact(Configuration config, Project p, ExtractExtension ext) {
        for (ResolvedArtifact artifact : config.getResolvedConfiguration().getResolvedArtifacts()) {
            String group = artifact.getModuleVersion().getId().getGroup();
            String name = artifact.getModuleVersion().getId().getName();
            String version = artifact.getModuleVersion().getId().getVersion();
            String classifier = artifact.getClassifier();
            String extension = artifact.getExtension();

            File resolvedFile = artifact.getFile();
            String extractFileName = ext.match(group, name, version);
            if (extractFileName != null) {
                try (ZipFile zf = new ZipFile(resolvedFile)) {
                    ZipEntry ze = zf.getEntry(extractFileName);
                    if (ze != null) {
                        Path extractFile = resolvedFile.getParentFile().toPath().resolve(extractFileName);
                        Files.copy(zf.getInputStream(ze), extractFile, StandardCopyOption.REPLACE_EXISTING);
                        resolvedFile = extractFile.toFile();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            File deobfFile = new File(p.getProjectDir(), ".gradle/remap-repo/deobf/" + group.replace('.', '/') + "/" + name + "/" + version + "/" + name + "-" + version + (StringUtils.isBlank(classifier) ? "" : "-" + classifier) + (StringUtils.isBlank(extension) ? ".jar" : "." + extension));
            deobfFile.getParentFile().mkdirs();

            ProcessJarTask task = (ProcessJarTask) p.task(ImmutableMap.of("type", ProcessJarTask.class), "deobfMod_" + String.format("%08x", artifact.hashCode()));
            task.setInJar(new DelayedFile(resolvedFile));
            task.setOutCleanJar(new DelayedFile(deobfFile));
            task.setExceptorCfg(new DelayedFile(p, UserConstants.EXC_SRG, forge));
            task.setExceptorJson(new DelayedFile(p, "{USER_DEV}/conf/exceptor.json", forge));
            task.setSrg(new DelayedFile(p, UserConstants.DEOBF_SRG_MCP_SRG, forge));

            this.runProcessJarTask(p, task);

            p.getDependencies().add(CONFIG.get(config.getName()), "deobf." + group + ":" + name + ":" + version + (StringUtils.isBlank(classifier) ? "" : ":" + classifier) + (StringUtils.isBlank(extension) ? "" : "@" + extension));
        }
    }

    private void runProcessJarTask(Project p, ProcessJarTask task) {
        try {
            if (!this.preTaskRun) {
                // TODO: Make these hardcoded tasks better
                EtagDownloadTask edt = (EtagDownloadTask) p.getTasks().getByName("getVersionJson");
                edt.doTask();
                for (ContextAwareTaskAction action : edt.getTaskActions()) {
                    action.execute(edt);
                }

                ExtractConfigTask ect = (ExtractConfigTask) p.getTasks().getByName("extractUserDev");
                ect.doTask();
                for (ContextAwareTaskAction action : ect.getTaskActions()) {
                    action.execute(ect);
                }

                ExtractConfigTask emd = (ExtractConfigTask) p.getTasks().getByName("extractMcpData");
                emd.doTask();

                ((GenSrgTask) p.getTasks().getByName("genSrgs")).doTask();
                this.preTaskRun = true;
            }

            File inJar = task.getInJar();
            if (inJar.exists() && task.getExceptorCfg().exists() && task.getExceptorJson().exists() && task.getSrg().exists()) {
                File outCleanJar = task.getOutCleanJar();
                File inJarSHA = new File(outCleanJar.getParent(), outCleanJar.getName() + ".sha1");
                if (!outCleanJar.exists() || !inJarSHA.exists() || !new String(Files.readAllBytes(inJarSHA.toPath()), StandardCharsets.UTF_8).equals(DigestUtils.shaHex(Files.newInputStream(inJar.toPath())))) {
                    System.out.println("Deobf: " + inJar.getAbsolutePath() + " -> " + outCleanJar.getAbsolutePath());
                    task.doTask();
                    Files.write(inJarSHA.toPath(), DigestUtils.shaHex(Files.newInputStream(inJar.toPath())).getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
