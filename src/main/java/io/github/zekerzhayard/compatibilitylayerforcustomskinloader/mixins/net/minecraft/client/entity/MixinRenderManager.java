package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.mixins.net.minecraft.client.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinAbstractClientPlayer;
import io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity.IMixinRenderPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
    private final Map<String, RenderPlayer> /* skinMap */ field_178636_l = Maps.newHashMap();
    private RenderPlayer /* renderPlayer */ field_178637_m;

    @Inject(
        method = "Lnet/minecraft/client/renderer/entity/RenderManager;<init>()V",
        at = @At("RETURN")
    )
    private void inject$_init_$0(CallbackInfo ci) {
        this.field_178637_m = new RenderPlayer();
        ((IMixinRenderPlayer) this.field_178637_m).setModelOnInit((RenderManager) (Object) this, false);
        this.field_178636_l.put("default", this.field_178637_m);

        RenderPlayer renderPlayer = new RenderPlayer();
        ((IMixinRenderPlayer) renderPlayer).setModelOnInit((RenderManager) (Object) this, true);
        this.field_178636_l.put("slim", renderPlayer);
    }

    @Inject(
        method = "Lnet/minecraft/client/renderer/entity/RenderManager;getEntityRenderObject(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/renderer/entity/Render;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject$getEntityRenderObject$0(Entity entityIn, CallbackInfoReturnable<Render> cir) {
        if (entityIn instanceof AbstractClientPlayer) {
            String skinType = ((IMixinAbstractClientPlayer) entityIn).getSkinType();
            if (skinType != null) {
                RenderPlayer renderPlayer = this.field_178636_l.get(skinType);
                cir.setReturnValue(renderPlayer != null ? renderPlayer : this.field_178637_m);
            }
        }
    }
}
