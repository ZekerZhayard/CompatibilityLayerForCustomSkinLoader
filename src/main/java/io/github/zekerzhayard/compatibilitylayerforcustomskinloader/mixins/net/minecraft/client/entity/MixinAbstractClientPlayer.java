package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.entity;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinAbstractClientPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer implements IMixinAbstractClientPlayer {
    @Shadow
    private ResourceLocation locationSkin;

    @Shadow
    private ResourceLocation locationCape;

    private String skinType;

    @Override
    public String getSkinType() {
        return this.skinType;
    }

    @SoftOverride
    public void /* skinAvailable */ func_180521_a(MinecraftProfileTexture.Type typeIn, ResourceLocation location, MinecraftProfileTexture profileTexture) {
        switch (typeIn) {
            case SKIN: {
                this.locationSkin = location;
                this.skinType = profileTexture.getMetadata("model");
                if (this.skinType == null) {
                    this.skinType = "default";
                }
                break;
            }
            case CAPE: {
                this.locationCape = location;
                break;
            }
        }
    }
}
