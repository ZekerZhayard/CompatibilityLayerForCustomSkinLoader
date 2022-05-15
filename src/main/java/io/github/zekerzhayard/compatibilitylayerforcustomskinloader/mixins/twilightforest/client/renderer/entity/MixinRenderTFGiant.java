package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.twilightforest.client.renderer.entity;

import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinAbstractClientPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.client.renderer.entity.RenderTFGiant;

@Mixin(RenderTFGiant.class)
public abstract class MixinRenderTFGiant extends RenderBiped {
    private ModelPlayer slimModelPlayer = new ModelPlayer(0.0F, true);

    private MixinRenderTFGiant(ModelBiped modelBipedIn, float shadowSizeIn) {
        super(modelBipedIn, shadowSizeIn);
    }

    @Redirect(
        method = "Ltwilightforest/client/renderer/entity/RenderTFGiant;<init>()V",
        at = @At(
            value = "NEW",
            target = "()Lnet/minecraft/client/model/ModelBiped;"
        ),
        remap = false
    )
    private static ModelBiped redirect$_init_$0() {
        return new ModelPlayer(0.0F, false);
    }

    @Inject(
        method = "Ltwilightforest/client/renderer/entity/RenderTFGiant;getEntityTexture(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/ResourceLocation;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;getMinecraft()Lnet/minecraft/client/Minecraft;",
            ordinal = 1
        )
    )
    private void inject$getEntityTexture$0(CallbackInfoReturnable<ResourceLocation> cir) {
        if ("slim".equals(((IMixinAbstractClientPlayer) Minecraft.getMinecraft().thePlayer).getSkinType())) {
            this.mainModel = this.modelBipedMain = this.slimModelPlayer;
        }
    }
}
