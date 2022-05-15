package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.renderer.entity;

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
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderPlayer.class)
@SuppressWarnings("target")
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

    @ModifyConstant(
        method = "Lnet/minecraft/client/renderer/entity/RenderPlayer;localRenderSpecials(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V", // RenderPlayerAPI
        constant = @Constant(floatValue = 1.0625F),
        remap = false
    )
    private float modifyConstant$localRenderSpecials$0(float scale) {
        return this.modifyConstant$renderEquippedItems$0(scale);
    }

    @ModifyConstant(
        method = "Lnet/minecraft/client/renderer/entity/RenderPlayer;renderEquippedItems(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V",
        constant = @Constant(floatValue = 1.0625F)
    )
    private float modifyConstant$renderEquippedItems$0(float scale) {
        return 1.1875F;
    }

    @Override
    public void setModelOnInit(RenderManager renderManager, boolean useSmallArms) {
        this.setRenderManager(renderManager);
        this.modelBipedMain = Loader.isModLoaded("mobends") ? RenderBendsPlayerUtil.createModelBendsPlayer(useSmallArms) : new ModelPlayer(0.0F, useSmallArms);
        this.mainModel = this.modelBipedMain;
    }
}
