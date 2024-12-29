package ru.luminous_team.luminous_team_magic.blocks.blocks_entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;
import ru.luminous_team.luminous_team_magic.blocks.LTMBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LuminousTeamMagic.MODID);

    public static final RegistryObject<BlockEntityType<TableBlockEntity>> PROCESSING_TABLE =
            BLOCK_ENTITIES.register("processing_table", () ->
                    BlockEntityType.Builder.of(TableBlockEntity::new,
                            LTMBlocks.PROCESSING_TABLE.get()).build(null));
}