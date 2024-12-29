package ru.luminous_team.luminous_team_magic.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;

public class LTMRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LuminousTeamMagic.MODID);

  public static final RegistryObject<RecipeSerializer<ProcessingTableRecipe>> FORGE_SCROLL_NETHER_SERIALIZER =
            SERIALIZERS.register("forge_scroll_nether", () -> ProcessingTableRecipe.Serializer.INSTANCE);

}
