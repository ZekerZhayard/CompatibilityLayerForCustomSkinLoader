package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.platform;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import customskinloader.forge.platform.DefaultFMLPlatform;
import customskinloader.forge.platform.IFMLPlatform;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.ForgePlugin;
import net.minecraft.launchwrapper.ITweaker;
import org.objectweb.asm.commons.Remapper;

public class FMLLegacyPlatform extends DefaultFMLPlatform {
    private final static String FML_PLUGIN_WRAPPER = "cpw.mods.fml.relauncher.CoreModManager$FMLPluginWrapper";

    @Override
    public Result init(Set<IFMLPlatform> otherPlatforms) {
        return otherPlatforms.size() == 1 ? Result.ACCEPT : Result.REJECT;
    }

    @Override
    public String getSide() {
        return FMLLaunchHandler.side().name();
    }

    @Override
    public List<String> getIgnoredMods() {
        return CoreModManager.getLoadedCoremods();
    }

    @Override
    public ITweaker createFMLPluginWrapper(String name, File location, int sortIndex) throws Exception {
        Constructor<?> constructor = Class.forName(FML_PLUGIN_WRAPPER).getDeclaredConstructor(String.class, IFMLLoadingPlugin.class, File.class, int.class, String[].class);
        constructor.setAccessible(true);
        return (ITweaker) constructor.newInstance(name, this.getFMLLoadingPluginClass().newInstance(), location, sortIndex, new String[0]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addLoadPlugins(ITweaker tweaker) throws Exception {
        Field field = CoreModManager.class.getDeclaredField("loadPlugins");
        field.setAccessible(true);
        ((List<ITweaker>) field.get(null)).add(tweaker);
    }

    @Override
    public Remapper getRemapper() {
        return new Supplier<Remapper>() {
            @Override
            public Remapper get() {
                return FMLDeobfuscatingRemapper.INSTANCE;
            }
        }.get();
    }

    @Override
    protected Class<?> getFMLLoadingPluginClass() {
        return ForgePlugin.class;
    }

    @Override
    protected Annotation getNameAnnotation() {
        return this.getFMLLoadingPluginClass().getAnnotation(IFMLLoadingPlugin.Name.class);
    }

    @Override
    protected String getNameValue(Annotation annotationIn) {
        assert annotationIn instanceof IFMLLoadingPlugin.Name;
        return ((IFMLLoadingPlugin.Name) annotationIn).value();
    }

    @Override
    protected Annotation getSortingIndexAnnotation() {
        return this.getFMLLoadingPluginClass().getAnnotation(IFMLLoadingPlugin.SortingIndex.class);
    }

    @Override
    protected int getSortingIndexValue(Annotation annotationIn) {
        assert annotationIn instanceof IFMLLoadingPlugin.SortingIndex;
        return ((IFMLLoadingPlugin.SortingIndex) annotationIn).value();
    }
}
