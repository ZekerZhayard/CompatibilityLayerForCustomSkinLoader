package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.resources;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SkinManager.SkinAvailableCallback.class)
public interface MixinSkinManager$SkinAvailableCallback {
    @Shadow
    void onSkinAvailable(MinecraftProfileTexture.Type skinPart, ResourceLocation skinLoc);

    default void /* skinAvailable */ func_180521_a(MinecraftProfileTexture.Type typeIn, ResourceLocation location, MinecraftProfileTexture profileTexture) {
        this.onSkinAvailable(typeIn, location);
    }
}
