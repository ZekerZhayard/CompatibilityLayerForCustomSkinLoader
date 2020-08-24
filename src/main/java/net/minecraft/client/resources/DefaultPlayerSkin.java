package net.minecraft.client.resources;

import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class DefaultPlayerSkin
{
    /** The default skin for the Steve model. */
    private static final ResourceLocation /* TEXTURE_STEVE */ field_177337_a = new ResourceLocation("textures/entity/steve.png");
    /** The default skin for the Alex model. */
    private static final ResourceLocation /* TEXTURE_ALEX */ field_177336_b = new ResourceLocation("textures/entity/alex.png");

    /**
     * Returns the default skind for versions prior to 1.8, which is always the Steve texture.
     */
    public static ResourceLocation /* getDefaultSkinLegacy */ func_177335_a()
    {
        return field_177337_a;
    }

    /**
     * Retrieves the default skin for this player. Depending on the model used this will be Alex or Steve.
     */
    public static ResourceLocation /* getDefaultSkin */ func_177334_a(UUID playerUUID)
    {
        return func_177333_c(playerUUID) ? field_177336_b : field_177337_a;
    }

    /**
     * Retrieves the type of skin that a player is using. The Alex model is slim while the Steve model is default.
     */
    public static String /* getSkinType */ func_177332_b(UUID playerUUID)
    {
        return func_177333_c(playerUUID) ? "slim" : "default";
    }

    /**
     * Checks if a players skin model is slim or the default. The Alex model is slime while the Steve model is default.
     */
    private static boolean /* isSlimSkin */ func_177333_c(UUID playerUUID)
    {
        return (playerUUID.hashCode() & 1) == 1;
    }
}
