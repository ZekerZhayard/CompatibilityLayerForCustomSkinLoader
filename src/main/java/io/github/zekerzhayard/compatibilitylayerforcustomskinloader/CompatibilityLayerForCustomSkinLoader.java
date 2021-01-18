package io.github.zekerzhayard.compatibilitylayerforcustomskinloader;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = CompatibilityLayerForCustomSkinLoader.MODID,
    name = CompatibilityLayerForCustomSkinLoader.MODID,
    version = CompatibilityLayerForCustomSkinLoader.VERSION,
    acceptedMinecraftVersions = "(,1.7.10]",
    acceptableRemoteVersions = "*"
)
public class CompatibilityLayerForCustomSkinLoader {
    public final static String MODID = "@MODID@";
    public final static String VERSION = "@VERSION@";

    private final static Logger LOGGER = LogManager.getLogger(MODID);

    public static boolean hasRenderPlayerAPI = false;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            Class.forName("customskinloader.Logger").getMethod("info", String.class).invoke(Class.forName("customskinloader.CustomSkinLoader").getField("logger").get(null), MODID + " Version: " + VERSION);
        } catch (Throwable t) {
            LOGGER.info("No CustomSkinLoader detected!", t);
        }

        hasRenderPlayerAPI = Loader.isModLoaded("RenderPlayerAPI");
        if (!hasRenderPlayerAPI) {
            LOGGER.info("No RenderPlayerAPI detected!");
        }
    }
}
