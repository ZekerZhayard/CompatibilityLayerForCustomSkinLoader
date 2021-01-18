package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat;

import java.io.File;
import java.util.List;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class LazyTweaker implements ITweaker {
    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {

    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {

    }

    @Override
    public String getLaunchTarget() {
        return "";
    }

    @Override
    public String[] getLaunchArguments() {
        // All things are safe.
        ((Runnable) Launch.blackboard.getOrDefault(OpenModsCorePluginWrapper.LAZY_INJECT_DATA, (Runnable) () -> {})).run();
        return new String[0];
    }
}
