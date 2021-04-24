package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.entity;

import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinRenderPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity implements IMixinRenderPlayer {
    @Shadow
    public ModelBiped modelBipedMain;

    private MixinRenderPlayer(ModelBase modelBaseIn, float shadowSizeIn) {
        super(modelBaseIn, shadowSizeIn);
    }

    @Override
    public void setModelOnInit(RenderManager renderManager, boolean useSmallArms) {
        this.setRenderManager(renderManager);
        this.modelBipedMain = new ModelPlayer(0.0F, useSmallArms);
        this.mainModel = this.modelBipedMain;
    }
}
