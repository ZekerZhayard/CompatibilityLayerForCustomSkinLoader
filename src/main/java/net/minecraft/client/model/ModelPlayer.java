package net.minecraft.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

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
            this.field_178734_a.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.field_178732_b = new ModelRenderer(this, 40, 32);
            this.field_178732_b.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            this.field_178732_b.setRotationPoint(-5.0F, 2.5F, 10.0F);
        }
        else
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.field_178734_a = new ModelRenderer(this, 48, 48);
            this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            this.field_178734_a.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.field_178732_b = new ModelRenderer(this, 40, 32);
            this.field_178732_b.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            this.field_178732_b.setRotationPoint(-5.0F, 2.0F, 10.0F);
        }

        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.field_178733_c = new ModelRenderer(this, 0, 48);
        this.field_178733_c.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.field_178733_c.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.field_178731_d = new ModelRenderer(this, 0, 32);
        this.field_178731_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.field_178731_d.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.field_178730_v = new ModelRenderer(this, 16, 32);
        this.field_178730_v.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
        this.field_178730_v.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GL11.glPushMatrix();

        if (this.isChild)
        {
            float f = 2.0F;
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glTranslatef(0.0F, 24.0F * scale, 0.0F);
            this.field_178733_c.render(scale);
            this.field_178731_d.render(scale);
            this.field_178734_a.render(scale);
            this.field_178732_b.render(scale);
            this.field_178730_v.render(scale);
        }
        else
        {
            if (entityIn.isSneaking())
            {
                GL11.glTranslatef(0.0F, 0.2F, 0.0F);
            }

            this.field_178733_c.render(scale);
            this.field_178731_d.render(scale);
            this.field_178734_a.render(scale);
            this.field_178732_b.render(scale);
            this.field_178730_v.render(scale);
        }

        if (this.field_178735_y) {
            this.bipedRightArm.rotationPointX += 1.0F;
        }

        GL11.glPopMatrix();
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        copyModelAngles(this.bipedLeftLeg, this.field_178733_c);
        copyModelAngles(this.bipedRightLeg, this.field_178731_d);
        copyModelAngles(this.bipedLeftArm, this.field_178734_a);
        copyModelAngles(this.bipedRightArm, this.field_178732_b);
        copyModelAngles(this.bipedBody, this.field_178730_v);
    }

    // ModelBase
    /**
     * Copies the angles from one object to another. This is used when objects should stay aligned with each other, like
     * the hair over a players head.
     */
    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest)
    {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }
}
