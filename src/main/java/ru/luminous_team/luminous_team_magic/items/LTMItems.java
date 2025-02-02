package ru.luminous_team.luminous_team_magic.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;
import ru.luminous_team.luminous_team_magic.tab.ModCreativeTab;

public class LTMItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LuminousTeamMagic.MODID);

    public static final RegistryObject<Item> CRYSTAL_AIR = ITEMS.register("crystal_air",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.LTM)));
    public static final RegistryObject<Item> CRYSTAL_FIRE = ITEMS.register("crystal_fire",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.LTM)));
    public static final RegistryObject<Item> CRYSTAL_STONE = ITEMS.register("crystal_stone",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.LTM)));
    public static final RegistryObject<Item> CRYSTAL_WATER = ITEMS.register("crystal_water",
            () -> new Item(new Item.Properties().tab(ModCreativeTab.LTM)));
}