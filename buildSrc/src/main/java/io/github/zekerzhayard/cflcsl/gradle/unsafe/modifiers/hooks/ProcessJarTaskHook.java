package io.github.zekerzhayard.cflcsl.gradle.unsafe.modifiers.hooks;

import java.io.File;
import java.io.IOException;

import io.github.zekerzhayard.cflcsl.gradle.DeobfPlugin;
import net.md_5.specialsource.Jar;
import net.md_5.specialsource.provider.InheritanceProvider;
import net.md_5.specialsource.provider.JarProvider;
import net.md_5.specialsource.provider.JointProvider;
import net.minecraftforge.gradle.delayed.DelayedFile;

public class ProcessJarTaskHook {
    public static InheritanceProvider addProvider(InheritanceProvider jar, JointProvider provider) throws IOException {
        provider.add(jar);
        if (DeobfPlugin.forge != null) {
            File forgeSrg = new DelayedFile(DeobfPlugin.project, DeobfPlugin.project.getProjectDir().getAbsolutePath() + "/.gradle/remap-repo/{API_NAME}-{API_VERSION}-srg.jar", DeobfPlugin.forge).resolveDelayed();
            File mcSrg = new DelayedFile(DeobfPlugin.project, "{API_CACHE_DIR}/{API_NAME}-{API_VERSION}-srg.jar", DeobfPlugin.forge).resolveDelayed();
            if (forgeSrg.exists() && mcSrg.exists()) {
                provider.add(new JarProvider(Jar.init(mcSrg)));
                return new JarProvider(Jar.init(forgeSrg));
            }
        }
        return className -> null;
    }
}
