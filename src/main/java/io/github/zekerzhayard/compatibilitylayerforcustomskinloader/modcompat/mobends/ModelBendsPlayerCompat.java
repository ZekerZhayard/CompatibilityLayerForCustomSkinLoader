package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.modcompat.mobends;

import net.gobbob.mobends.client.model.ModelRendererBends;
import net.gobbob.mobends.client.model.ModelRendererBends_SeperatedChild;
import net.gobbob.mobends.client.model.entity.ModelBendsPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;

public class ModelBendsPlayerCompat extends ModelBendsPlayer {
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedLeftForeArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedRightForeArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedLeftForeLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedRightForeLegwear;
    public ModelRenderer bipedBodyWear;

    public ModelPlayer originalModel;
    public boolean isSmallArms;

    public ModelBendsPlayerCompat(float modelSize, boolean smallArmsIn) {
        super(modelSize, 0.0F, 64, 64);
        this.originalModel = new ModelPlayer(modelSize, smallArmsIn);
        this.isSmallArms = smallArmsIn;

        int armWidth = 4;
        float armOriginXOffset = 0.0F;
        if (smallArmsIn) {
            armWidth = 3;
            armOriginXOffset = 1.0F;
            this.bipedBody = new ModelRendererBends(this, 16, 16).setShowChildIfHidden(true);
            this.bipedBody.addBox(-4.0F, -12.0F, -2.0F, 8, 12, 4, modelSize);
            this.bipedBody.setRotationPoint(0.0F, 12.0F, 0.0F);

            this.bipedRightArm = new ModelRendererBends_SeperatedChild(this, 40, 16).setMother((ModelRendererBends) this.bipedBody);
            this.bipedRightArm.addBox(-3.0F + armOriginXOffset, -2.0F, -2.0F, armWidth, 6, 4, modelSize);
            this.bipedRightArm.setRotationPoint(-5.0F, -10.0F, 0.0F);

            this.bipedLeftArm = new ModelRendererBends_SeperatedChild(this, 32, 48).setMother((ModelRendererBends)this.bipedBody);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, armWidth, 6, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, -10.0F, 0.0F);

            this.bipedRightForeArm = new ModelRendererBends(this, 40, 22);
            this.bipedRightForeArm.addBox(0.0F + armOriginXOffset, 0.0F, -4.0F, armWidth, 6, 4, modelSize);
            this.bipedRightForeArm.setRotationPoint(-3.0F, 4.0F, 2.0F);
            ((ModelRendererBends) this.bipedRightForeArm).getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0F, -6.0F);

            this.bipedLeftForeArm = new ModelRendererBends(this, 32, 54);
            this.bipedLeftForeArm.addBox(0.0F, 0.0F, -4.0F, armWidth, 6, 4, modelSize);
            this.bipedLeftForeArm.setRotationPoint(-1.0F, 4.0F, 2.0F);
            ((ModelRendererBends) this.bipedLeftForeArm).getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0F, -6.0F);

            this.bipedBody.addChild(this.bipedHead);
            this.bipedBody.addChild(this.bipedRightArm);
            this.bipedBody.addChild(this.bipedLeftArm);
            this.bipedRightArm.addChild(this.bipedRightForeArm);
            this.bipedLeftArm.addChild(this.bipedLeftForeArm);

            ((ModelRendererBends_SeperatedChild) this.bipedRightArm).setSeperatedPart((ModelRendererBends) this.bipedRightForeArm);
            ((ModelRendererBends_SeperatedChild) this.bipedLeftArm).setSeperatedPart((ModelRendererBends) this.bipedLeftForeArm);
            ((ModelRendererBends) this.bipedRightArm).offsetBox_Add(-0.01F, 0.0F, -0.01F).resizeBox(3.02F, 6.0F, 4.02F).updateVertices();
            ((ModelRendererBends) this.bipedLeftArm).offsetBox_Add(-0.01F, 0.0F, -0.01F).resizeBox(3.02F, 6.0F, 4.02F).updateVertices();
        }

        this.bipedBodyWear = new ModelRendererBends(this, 16, 32).setShowChildIfHidden(true);
        this.bipedBodyWear.addBox(-4.0F, -12.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);

        this.bipedRightArmwear = new ModelRendererBends_SeperatedChild(this, 40, 32).setMother((ModelRendererBends) this.bipedBody);
        this.bipedRightArmwear.addBox(-3.0F + armOriginXOffset, -2.0F, -2.0F, armWidth, 6, 4, modelSize + 0.25F);

        this.bipedLeftArmwear = new ModelRendererBends_SeperatedChild(this, 48, 48).setMother((ModelRendererBends) this.bipedBody);
        this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, armWidth, 6, 4, modelSize + 0.25F);

        this.bipedRightLegwear = new ModelRendererBends(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, modelSize + 0.25F);

        this.bipedLeftLegwear = new ModelRendererBends(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, modelSize + 0.25F);

        this.bipedRightForeArmwear = new ModelRendererBends(this, 40, 38);
        this.bipedRightForeArmwear.addBox(0.0F + armOriginXOffset, 0.0F, -4.0F, armWidth, 6, 4, modelSize + 0.25F);

        this.bipedLeftForeArmwear = new ModelRendererBends(this, 48, 54);
        this.bipedLeftForeArmwear.addBox(0.0F, 0.0F, -4.0F, armWidth, 6, 4, modelSize + 0.25F);

        this.bipedRightForeLegwear = new ModelRendererBends(this, 0, 38);
        this.bipedRightForeLegwear.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 4, modelSize + 0.25F);

        this.bipedLeftForeLegwear = new ModelRendererBends(this, 0, 54);
        this.bipedLeftForeLegwear.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 4, modelSize + 0.25F);

        this.bipedBody.addChild(this.bipedBodyWear);
        this.bipedRightArm.addChild(this.bipedRightArmwear);
        this.bipedLeftArm.addChild(this.bipedLeftArmwear);
        this.bipedRightLeg.addChild(this.bipedRightLegwear);
        this.bipedLeftLeg.addChild(this.bipedLeftLegwear);
        this.bipedRightForeArm.addChild(this.bipedRightForeArmwear);
        this.bipedLeftForeArm.addChild(this.bipedLeftForeArmwear);
        this.bipedRightForeLeg.addChild(this.bipedRightForeLegwear);
        this.bipedLeftForeLeg.addChild(this.bipedLeftForeLegwear);
    }
}
