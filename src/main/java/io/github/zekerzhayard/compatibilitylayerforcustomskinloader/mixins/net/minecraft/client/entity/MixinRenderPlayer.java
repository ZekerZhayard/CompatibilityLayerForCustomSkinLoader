package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.entity;

import cpw.mods.fml.common.Loader;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinRenderPlayer;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.modcompat.mobends.RenderBendsPlayerUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity implements IMixinRenderPlayer {
    @Shadow
    public ModelBiped modelBipedMain;

    private MixinRenderPlayer(ModelBase modelBaseIn, float shadowSizeIn) {
        super(modelBaseIn, shadowSizeIn);
    }

    @Redirect(
        method = "Lnet/minecraft/client/renderer/entity/RenderPlayer;<init>()V",
        at = @At(
            value = "NEW",
            target = "(F)Lnet/minecraft/client/model/ModelBiped;",
            ordinal = 0
        )
    )
    private static ModelBiped redirect$_init_$0(float modelSize) {
        return Loader.isModLoaded("mobends") ? RenderBendsPlayerUtil.createModelBendsPlayer(false) : new ModelPlayer(modelSize, false);
    }

    @Override
    public void setModelOnInit(RenderManager renderManager, boolean useSmallArms) {
        this.setRenderManager(renderManager);
        this.modelBipedMain = Loader.isModLoaded("mobends") ? RenderBendsPlayerUtil.createModelBendsPlayer(useSmallArms) : new ModelPlayer(0.0F, useSmallArms);
        this.mainModel = this.modelBipedMain;
    }
}
