package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.gobbob.mobends.client.model.entity;

import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.modcompat.mobends.ModelBendsPlayerCompat;
import net.gobbob.mobends.client.model.entity.ModelBendsPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(
    value = ModelBendsPlayer.class,
    remap = false
)
public abstract class MixinModelBendsPlayer extends ModelBiped {
    // bipedLeftArm
    @ModifyArgs(
        method = "Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;<init>(FFII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/gobbob/mobends/client/model/ModelRendererBends_SeperatedChild;<init>(Lnet/minecraft/client/model/ModelBase;II)V",
            ordinal = 1
        )
    )
    private void modifyArgs$_init_$0(Args args) {
        if ((Object) this instanceof ModelBendsPlayerCompat) {
            args.set(1, 32);
            args.set(2, 48);
        }
    }

    // bipedLeftLeg
    @ModifyArgs(
        method = "Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;<init>(FFII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/gobbob/mobends/client/model/ModelRendererBends;<init>(Lnet/minecraft/client/model/ModelBase;II)V",
            ordinal = 6
        )
    )
    private void modifyArgs$_init_$1(Args args) {
        if ((Object) this instanceof ModelBendsPlayerCompat) {
            args.set(1, 16);
            args.set(2, 48);
        }
    }

    // bipedLeftForeArm
    @ModifyArgs(
        method = "Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;<init>(FFII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/gobbob/mobends/client/model/ModelRendererBends;<init>(Lnet/minecraft/client/model/ModelBase;II)V",
            ordinal = 8
        )
    )
    private void modifyArgs$_init_$2(Args args) {
        if ((Object) this instanceof ModelBendsPlayerCompat) {
            args.set(1, 32);
            args.set(2, 54);
        }
    }

    // bipedLeftForeLeg
    @ModifyArgs(
        method = "Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;<init>(FFII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/gobbob/mobends/client/model/ModelRendererBends;<init>(Lnet/minecraft/client/model/ModelBase;II)V",
            ordinal = 10
        )
    )
    private void modifyArgs$_init_$3(Args args) {
        if ((Object) this instanceof ModelBendsPlayerCompat) {
            args.set(1, 16);
            args.set(2, 54);
        }
    }

    @Redirect(
        method = "Lnet/gobbob/mobends/client/model/entity/ModelBendsPlayer;<init>(FFII)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/model/ModelRenderer;mirror:Z",
            remap = true
        )
    )
    private void redirect$_init_$0(ModelRenderer modelRenderer, boolean b) {
        if ((Object) this instanceof ModelBendsPlayerCompat) {
            modelRenderer.mirror = false;
        } else {
            modelRenderer.mirror = b;
        }
    }
}
