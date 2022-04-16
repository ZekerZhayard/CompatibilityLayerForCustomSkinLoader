package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.modcompat.mobends;

import net.gobbob.mobends.client.renderer.entity.RenderBendsPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class RenderBendsPlayerUtil {
    public static RenderPlayer createRenderBendsPlayer() {
        return new RenderBendsPlayer();
    }

    public static ModelBiped createModelBendsPlayer(boolean useSmallArms) {
        return new ModelBendsPlayerCompat(0.0F, useSmallArms);
    }
}
