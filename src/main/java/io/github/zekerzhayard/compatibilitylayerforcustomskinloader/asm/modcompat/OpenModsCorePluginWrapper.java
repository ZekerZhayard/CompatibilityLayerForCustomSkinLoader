package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat;

import java.util.Map;

import net.minecraft.launchwrapper.Launch;
import openmods.core.OpenModsCorePlugin;

public class OpenModsCorePluginWrapper extends OpenModsCorePlugin {
    final static String LAZY_INJECT_DATA = "cflcsl_lazyInjectData";

    @Override
    public void injectData(Map<String, Object> data) {
        Launch.blackboard.put(LAZY_INJECT_DATA, (Runnable) () -> super.injectData(data));
    }
}
