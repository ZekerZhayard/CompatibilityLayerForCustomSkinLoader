package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat.LazyTweaker;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat.ModDiscoverer;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat.OpenModsCorePluginWrapper;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;

// Make sure this mod will exist in crash reports.
@IFMLLoadingPlugin.Name("@MODID@")
@IFMLLoadingPlugin.TransformerExclusions({
    "io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm."
})
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
    @SuppressWarnings("unchecked")
    public void injectData(Map<String, Object> data) {
        if ((boolean) data.get("runtimeDeobfuscationEnabled")) {
            ModDiscoverer.scanMods((File) data.get("mcLocation"));
        }

        try {
            // OpenModsLib references minecraft classes at pre initialization phase, that is too bad.
            // We should check it and postpone the timing of it being called.
            Field implLookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            implLookupField.setAccessible(true);
            MethodHandles.Lookup implLookup = (MethodHandles.Lookup) implLookupField.get(null);

            Class<?> fmlPluginWrapperClass = Class.forName("cpw.mods.fml.relauncher.CoreModManager$FMLPluginWrapper");
            Class<?> openModsCorePluginClass;
            try {
                openModsCorePluginClass = Class.forName("openmods.core.OpenModsCorePlugin");
            } catch (ClassNotFoundException cnfe) {
                // OpenModsLib does not exist.
                return;
            }
            MethodHandle coreModInstanceGetter = implLookup.findGetter(fmlPluginWrapperClass, "coreModInstance", IFMLLoadingPlugin.class);
            for (ITweaker tweaker : (List<ITweaker>) Launch.blackboard.get("Tweaks")) {
                if (fmlPluginWrapperClass.isInstance(tweaker) && openModsCorePluginClass.isInstance(coreModInstanceGetter.invokeWithArguments(tweaker))) {
                    implLookup.findSetter(fmlPluginWrapperClass, "coreModInstance", IFMLLoadingPlugin.class).invokeWithArguments(tweaker, new OpenModsCorePluginWrapper());
                    ((List<String>) Launch.blackboard.get("TweakClasses")).add(LazyTweaker.class.getName());
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
