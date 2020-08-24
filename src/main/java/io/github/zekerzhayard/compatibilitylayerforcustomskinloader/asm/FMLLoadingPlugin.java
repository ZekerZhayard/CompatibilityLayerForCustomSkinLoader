package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

// Make sure this mod will exist in crash reports.
@IFMLLoadingPlugin.Name("@MODID@")
public class FMLLoadingPlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
