package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.gobbob.mobends.client.renderer.entity;

import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.modcompat.mobends.ModelBendsPlayerCompat;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.modcompat.mobends.RenderBendsPlayerUtil;
import net.gobbob.mobends.client.model.entity.ModelBendsPlayer;
import net.gobbob.mobends.client.renderer.entity.RenderBendsPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderBendsPlayer.class)
public abstract class MixinRenderBendsPlayer extends RenderPlayer {
    @Redirect(
        method = "Lnet/gobbob/mobends/client/renderer/entity/RenderBendsPlayer;<init>()V",
        at = @At(
            value = "NEW",
            target = "(F)Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;",
            ordinal = 0
        ),
        remap = false
    )
    private ModelBendsPlayer redirect$_init_$0(float modelSize) {
        return this.redirect$doRender$0(modelSize);
    }

    @Redirect(
        method = "Lnet/gobbob/mobends/client/renderer/entity/RenderBendsPlayer;doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V",
        at = @At(
            value = "NEW",
            target = "(F)Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;",
            ordinal = 0,
            remap = false
        )
    )
    private ModelBendsPlayer redirect$doRender$0(float modelSize) {
        return (ModelBendsPlayer) RenderBendsPlayerUtil.createModelBendsPlayer(this.mainModel instanceof ModelBendsPlayerCompat && ((ModelBendsPlayerCompat) this.mainModel).isSmallArms);
    }

    @Redirect(
        method = "Lnet/gobbob/mobends/client/renderer/entity/RenderBendsPlayer;renderFirstPersonArm(Lnet/minecraft/entity/player/EntityPlayer;)V",
        at = @At(
            value = "NEW",
            target = "()Lnet/minecraft/client/model/ModelBiped;"
        )
    )
    private ModelBiped redirect$renderFirstPersonArm$0() {
        if (this.mainModel instanceof ModelBendsPlayerCompat) {
            return ((ModelBendsPlayerCompat) this.mainModel).originalModel;
        }
        return new ModelBiped();
    }
}
