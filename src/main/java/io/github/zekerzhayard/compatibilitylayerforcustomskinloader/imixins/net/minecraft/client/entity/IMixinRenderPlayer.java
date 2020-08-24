package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.imixins.net.minecraft.client.entity;

import net.minecraft.client.renderer.entity.RenderManager;

public interface IMixinRenderPlayer {
    void setModelOnInit(RenderManager renderManager, boolean useSmallArms);
}
