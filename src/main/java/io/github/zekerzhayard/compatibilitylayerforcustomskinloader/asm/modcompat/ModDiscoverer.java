package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.FileUtils;

public class ModDiscoverer {
    private final static String MIXIN_PACKAGE = "io/github/zekerzhayard/compatibilitylayerforcustomskinloader/mixins/";
    private final static List<String> targetClasses = getAllMixinTargetClasses();

    public static void scanMods(File mcDir) {
        scanMods(mcDir, true);
    }

    public static void scanMods(File mcDir, boolean shouldScanVersionFolder) {
        List<URL> classpath = Arrays.asList(Launch.classLoader.getURLs());

        // scan mod folder
        for (File modFile : FileUtils.listFiles(new File(mcDir, "mods"), new String[] { "jar", "zip" }, false)) {
            try (ZipFile zf = new ZipFile(modFile)) {
                URL url = modFile.toURI().toURL();
                for (String targetClass : targetClasses) {
                    if (zf.getEntry(targetClass) != null && !classpath.contains(url)) {
                        Launch.classLoader.addURL(url);
                        CoreModManager.getReparseableCoremods().add(modFile.getName());
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (shouldScanVersionFolder) {
            File versionFolder = new File(mcDir, (String) FMLInjectionData.data()[4]);
            if (versionFolder.isDirectory()) {
                scanMods(versionFolder, false);
            }
        }
    }

    private static List<String> getAllMixinTargetClasses() {
        try {
            File self = new File(ModDiscoverer.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            try (ZipFile zf = new ZipFile(self)) {
                return zf.stream()
                    .map(ZipEntry::getName)
                    .filter(n -> n.startsWith(MIXIN_PACKAGE) && n.endsWith(".class"))
                    .map(n -> n.substring(MIXIN_PACKAGE.length()))
                    .map(n -> {
                        int index = n.lastIndexOf("/") + 1;
                        // mixin class name -> original class name
                        return n.substring(0, index) + n.substring(index + "Mixin".length());
                    })
                    .collect(Collectors.toList());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
