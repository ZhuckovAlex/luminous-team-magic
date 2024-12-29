package ru.luminous_team.luminous_team_magic.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.impl.ClassLoaderUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TableRecipes {
    public static final Map<String, ItemStack[]> recipes = new HashMap<>();

    public static void load() {

        String folderPath = "/data/luminous_team_magic/recipes";

        // List files in the folder
        try (var files = Files.walk(Paths.get(LuminousTeamMagic.class.getResource(folderPath).toURI()))) {
            files.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            loadRecipe(folderPath + "/" + file.getFileName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadRecipe(String filePath) throws IOException {
        InputStream inputStream = ClassLoaderUtil.getClassLoader().getResourceAsStream(filePath);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder builder = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            builder.append(line);
        }
        JsonObject object = JsonParser.parseString(builder.toString()).getAsJsonObject();
        if (!object.has("ingredient") || !object.has("fuel") || !object.has("result") || !object.has("name")) {
            return;
        }

        JsonObject ingredient = object.getAsJsonObject("ingredient");
        JsonObject fuel = object.getAsJsonObject("fuel");
        JsonObject result = object.getAsJsonObject("result");
        String name = object.get("name").getAsString();

        if (!ingredient.has("item") || !fuel.has("item") || !result.has("item")) {
            return;
        }

        String itemIngredient = ingredient.get("item").getAsString();
        String itemFuel = fuel.get("item").getAsString();
        String itemResult = result.get("item").getAsString();

        String[] resourceItemIngredient = itemIngredient.split(":");
        String[] resourceItemFuel = itemFuel.split(":");
        String[] resourceItemResult = itemResult.split(":");

        Item itemIngredientCraft = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resourceItemIngredient[0], resourceItemIngredient[1]));
        Item itemFuelCraft = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resourceItemFuel[0], resourceItemFuel[1]));
        Item itemResultCraft = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resourceItemResult[0], resourceItemResult[1]));

        recipes.put(name, new ItemStack[]{new ItemStack(itemIngredientCraft), new ItemStack(itemFuelCraft), new ItemStack(itemResultCraft)});
    }
}
