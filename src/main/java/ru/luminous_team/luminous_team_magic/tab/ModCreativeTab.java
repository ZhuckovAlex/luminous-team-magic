package ru.luminous_team.luminous_team_magic.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import ru.luminous_team.luminous_team_magic.blocks.LTMBlocks;

public class ModCreativeTab {
    public static final CreativeModeTab LTM = new CreativeModeTab("ltm") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(LTMBlocks.PROCESSING_TABLE.get());

        }
    };
}
