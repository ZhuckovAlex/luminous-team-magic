package ru.luminous_team.luminous_team_magic.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import ru.luminous_team.luminous_team_magic.gui.TableContainer;

public class ProcessingTableRecipe implements Recipe<TableContainer> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final Ingredient fuel;
    private final ItemStack result;

    public ProcessingTableRecipe(ResourceLocation id, Ingredient ingredient, Ingredient fuel, ItemStack result) {
        this.id = id;
        this.ingredient = ingredient;
        this.fuel = fuel;
        this.result = result;
    }

    @Override
    public boolean matches(TableContainer container, Level level) {
        // Проверка ингредиента в первом слоте
        boolean hasIngredient = ingredient.test(container.get().get(0).getItem());
        // Проверка топлива во втором слоте
        boolean hasFuel = fuel.test(container.get().get(2).getItem());
        return hasIngredient && hasFuel;
    }

    @Override
    public ItemStack assemble(TableContainer container) {
        // Уменьшаем количество ингредиента в первом слоте
        container.get().get(0).getItem().shrink(1);

        // Уменьшаем количество топлива во втором слоте
        container.get().get(2).getItem().shrink(1);

        // Обновляем контейнер, чтобы изменения отобразились
        container.setChanged();

        // Возвращаем результат
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ProcessingTableRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "processing_table_recipe";
    }

    public static class Serializer implements RecipeSerializer<ProcessingTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation("luminous_team_magic", "processing_table_recipe");

        @Override
        public ProcessingTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            Ingredient fuel = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "fuel"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new ProcessingTableRecipe(recipeId, ingredient, fuel, result);
        }

        @Override
        public ProcessingTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient fuel = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new ProcessingTableRecipe(recipeId, ingredient, fuel, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ProcessingTableRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            recipe.fuel.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
