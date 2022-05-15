package net.minecraft.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelPlayer extends ModelBiped
{
    public ModelRenderer /* bipedLeftArmwear */ field_178734_a;
    public ModelRenderer /* bipedRightArmwear */ field_178732_b;
    public ModelRenderer /* bipedLeftLegwear */ field_178733_c;
    public ModelRenderer /* bipedRightLegwear */ field_178731_d;
    public ModelRenderer /* bipedBodyWear */ field_178730_v;
    private final boolean /* smallArms */ field_178735_y;

    public ModelPlayer(float modelSize, boolean smallArmsIn)
    {
        super(modelSize, 0.0F, 64, 64);
        this.field_178735_y = smallArmsIn;
        this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.setTextureSize(64, 32);
        this.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);

        if (smallArmsIn)
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            this.field_178734_a = new ModelRenderer(this, 48, 48);
            this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            this.field_178732_b = new ModelRenderer(this, 40, 32);
            this.field_178732_b.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
        }
        else
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.field_178734_a = new ModelRenderer(this, 48, 48);
            this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            this.field_178732_b = new ModelRenderer(this, 40, 32);
            this.field_178732_b.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        }

        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.field_178733_c = new ModelRenderer(this, 0, 48);
        this.field_178733_c.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.field_178731_d = new ModelRenderer(this, 0, 32);
        this.field_178731_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.field_178730_v = new ModelRenderer(this, 16, 32);
        this.field_178730_v.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);

        this.bipedLeftArm.addChild(this.field_178734_a);
        this.bipedRightArm.addChild(this.field_178732_b);
        this.bipedLeftLeg.addChild(this.field_178733_c);
        this.bipedRightLeg.addChild(this.field_178731_d);
        this.bipedBody.addChild(this.field_178730_v);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (this.field_178735_y)
        {
            this.bipedRightArm.rotationPointX += 1.0F;
        }
    }
}
