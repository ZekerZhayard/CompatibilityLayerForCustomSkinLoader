package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.entity;

import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinRenderPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity implements IMixinRenderPlayer {
    @Shadow
    public ModelBiped modelBipedMain;

    private MixinRenderPlayer(ModelBase modelBaseIn, float shadowSizeIn) {
        super(modelBaseIn, shadowSizeIn);
    }

    @Inject(
        method = "Lnet/minecraft/client/renderer/entity/RenderPlayer;renderFirstPersonArm(Lnet/minecraft/entity/player/EntityPlayer;)V",
        at = @At("RETURN")
    )
    private void inject$renderFirstPersonArm$0(EntityPlayer entityPlayerIn, CallbackInfo ci) {
        if (this.modelBipedMain instanceof ModelPlayer) {
            this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, entityPlayerIn);
            ((ModelPlayer) this.modelBipedMain).field_178732_b.render(0.0625F);
        }
    }

    @Override
    public void setModelOnInit(RenderManager renderManager, boolean useSmallArms) {
        this.setRenderManager(renderManager);
        this.modelBipedMain = new ModelPlayer(0.0F, useSmallArms);
        this.mainModel = this.modelBipedMain;
    }
}
