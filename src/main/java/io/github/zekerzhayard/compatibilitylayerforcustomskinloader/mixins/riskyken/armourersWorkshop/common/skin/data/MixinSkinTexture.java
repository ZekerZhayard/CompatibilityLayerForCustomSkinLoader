package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.riskyken.armourersWorkshop.common.skin.data;

import java.awt.image.BufferedImage;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import riskyken.armourersWorkshop.common.skin.data.SkinTexture;

// WIP
@Mixin(
    value = SkinTexture.class,
    remap = false
)
@Pseudo
public abstract class MixinSkinTexture {
    @Shadow
    private BufferedImage bufferedPlayerImage;
    @Shadow
    private BufferedImage bufferedSkinImage;

    @Inject(
        method = "Lriskyken/armourersWorkshop/common/skin/data/SkinTexture;updateForResourceLocation(Lnet/minecraft/util/ResourceLocation;)V",
        at = @At(
            value = "FIELD",
            shift = At.Shift.AFTER,
            target = "Lriskyken/armourersWorkshop/common/skin/data/SkinTexture;bufferedPlayerImage:Ljava/awt/image/BufferedImage;",
            opcode = Opcodes.PUTFIELD
        )
    )
    private void inject$updateForResourceLocation$0(CallbackInfo ci) {
        this.bufferedSkinImage = new BufferedImage(this.bufferedPlayerImage.getWidth(), this.bufferedPlayerImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }
}
