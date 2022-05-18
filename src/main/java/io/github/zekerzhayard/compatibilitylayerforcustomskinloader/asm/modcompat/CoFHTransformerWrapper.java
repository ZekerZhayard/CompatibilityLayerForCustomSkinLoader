package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat;

import java.io.IOException;

import cofh.asm.CoFHAccessTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class CoFHTransformerWrapper implements IClassTransformer {
    private final IClassTransformer transformer = new CoFHAccessTransformer();

    public CoFHTransformerWrapper() throws IOException {

    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return this.transform(name, transformedName, basicClass, 3);
    }

    // https://github.com/CoFH/CoFHCore-1.12-Legacy/blob/1.7.10/src/main/java/cofh/asm/CoFHAccessTransformer.java#L585-L596
    private byte[] transform(String name, String transformedName, byte[] basicClass, int depth) {
        if (depth == 0) {
            return this.transformer.transform(name, transformedName, basicClass);
        }
        return this.transform(name, transformedName, basicClass, depth - 1);
    }
}
