
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package ru.luminous_team.luminous_team_magic.gui;


import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;

public class LTMMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, LuminousTeamMagic.MODID);
	public static final RegistryObject<MenuType<TableContainer>> TABLE_CONTAINER = REGISTRY.register("table_container", () -> IForgeMenuType.create(TableContainer::new));
}
