package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.mmmlibx.lib;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import customskinloader.forge.ForgeTweaker;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.FMLLoadingPlugin;
import mmmlibx.lib.FileManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
    value = FileManager.class,
    remap = false
)
public abstract class MixinFileManager {
    @Inject(
        method = "Lmmmlibx/lib/FileManager;getAllmodsFiles(Ljava/lang/ClassLoader;Z)Ljava/util/List;",
        at = @At("RETURN")
    )
    private static void inject$getAllmodsFiles$0(CallbackInfoReturnable<List<File>> cir) {
        List<File> modFiles = cir.getReturnValue();
        if (modFiles != null) {
            try {
                Path currentPath = Paths.get(FMLLoadingPlugin.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                Path cslPath = Paths.get(ForgeTweaker.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                modFiles.removeIf(f -> {
                    try {
                        return Files.isSameFile(f.toPath(), currentPath) || Files.isSameFile(f.toPath(), cslPath);
                    } catch (Throwable t) {
                        t.printStackTrace();
                        return false;
                    }
                });
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
