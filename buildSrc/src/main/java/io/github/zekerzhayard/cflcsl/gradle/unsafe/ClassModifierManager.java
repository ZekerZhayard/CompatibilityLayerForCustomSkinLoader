package io.github.zekerzhayard.cflcsl.gradle.unsafe;

import java.lang.invoke.MethodHandle;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;

import io.github.zekerzhayard.cflcsl.gradle.unsafe.modifiers.IClassModifier;

@SuppressWarnings("sunapi")
public class ClassModifierManager {
    static final HashMap<String, IClassModifier> classModifiers = new HashMap<>();

    public static void init() throws Throwable {
        Object ucp = Constants.IMPL_LOOKUP.findGetter(URLClassLoader.class, "ucp", sun.misc.URLClassPath.class).invokeWithArguments(ClassModifierManager.class.getClassLoader());
        ArrayList loaders = (ArrayList) Constants.IMPL_LOOKUP.findGetter(sun.misc.URLClassPath.class, "loaders", ArrayList.class).invokeWithArguments(ucp);

        Class<?> jarLoaderClass = Class.forName("sun.misc.URLClassPath$JarLoader");
        MethodHandle jarMH = Constants.IMPL_LOOKUP.findGetter(jarLoaderClass, "jar", JarFile.class);
        for (Object loader : loaders) {
            if (jarLoaderClass.isInstance(loader)) {
                JarFile jar = (JarFile) jarMH.invokeWithArguments(loader);
                if (jar != null && Paths.get(jar.getName()).getFileName().toString().contains("ForgeGradle-")) {
                    FakeJarFile fakeJarFile = (FakeJarFile) Constants.UNSAFE.allocateInstance(FakeJarFile.class);
                    fakeJarFile.clone(jar, JarFile.class);
                    Constants.IMPL_LOOKUP.findSetter(jarLoaderClass, "jar", JarFile.class).invokeWithArguments(loader, fakeJarFile);
                }
            }
        }
    }

    public static void registerModifier(IClassModifier modifier) {
        classModifiers.put(modifier.getClassName(), modifier);
    }
}
