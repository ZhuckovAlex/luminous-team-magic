package ru.luminous_team.luminous_team_magic.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;
import ru.luminous_team.luminous_team_magic.blocks.blocks_entity.ProcessingTable;
import ru.luminous_team.luminous_team_magic.blocks.custom.CrystalBlock;
import ru.luminous_team.luminous_team_magic.items.LTMItems;
import ru.luminous_team.luminous_team_magic.tab.ModCreativeTab;

import java.util.function.Supplier;

public class LTMBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, LuminousTeamMagic.MODID);


    public static final RegistryObject<Block> PROCESSING_TABLE = registerBlock("processing_table",
            () -> new ProcessingTable(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()),ModCreativeTab.LTM);

    public static final RegistryObject<Block> CRYSTAL = registerBlock("crystal",
            () -> new CrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .sound(SoundType.AMETHYST).strength(1f, 10f).noOcclusion()),ModCreativeTab.LTM);

    public static final RegistryObject<Block> RAW_CRYSTAL_FIRE = registerBlock("raw_crystal_fire",
            () -> new CrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .sound(SoundType.AMETHYST).strength(1f, 10f).noOcclusion()),ModCreativeTab.LTM);
    public static final RegistryObject<Block> RAW_CRYSTAL_WATER = registerBlock("raw_crystal_water",
            () -> new CrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .sound(SoundType.AMETHYST).strength(1f, 10f).noOcclusion()),ModCreativeTab.LTM);
    public static final RegistryObject<Block> RAW_CRYSTAL_AIR = registerBlock("raw_crystal_air",
            () -> new CrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .sound(SoundType.AMETHYST).strength(1f, 10f).noOcclusion()),ModCreativeTab.LTM);
    public static final RegistryObject<Block> RAW_CRYSTAL_STONE = registerBlock("raw_crystal_stone",
            () -> new CrystalBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .sound(SoundType.AMETHYST).strength(1f, 10f).noOcclusion()),ModCreativeTab.LTM);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return LTMItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }


}
