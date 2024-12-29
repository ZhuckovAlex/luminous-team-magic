package ru.luminous_team.luminous_team_magic.blocks.blocks_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;
import ru.luminous_team.luminous_team_magic.recipes.TableRecipes;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class TableBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	public int progress;
	public boolean isCrafted;
	public String nameCraft;
	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	public TableBlockEntity(BlockPos position, BlockState state) {
		super(ModBlockEntities.PROCESSING_TABLE.get(), position, state);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if (!this.tryLoadLootTable(compound))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.stacks);
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public Component getDefaultName() {
		return Component.literal("table");
	}

	@Override
	protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
		return null;
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}



	@Override
	public Component getDisplayName() {
		return Component.literal("Table");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return true;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
			return handlers[facing.ordinal()].cast();
		return super.getCapability(capability, facing);
	}

	private static String getRecipe(ItemStack ingredient, ItemStack fuel) {
		AtomicReference<String> name = new AtomicReference<>("");
		TableRecipes.recipes.forEach((s, itemStacks) -> {
			if (itemStacks[0].is(ingredient.getItem()) && itemStacks[1].is(fuel.getItem())){
				name.set(s);
            }
		});
		return name.get();
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TableBlockEntity pEntity) {
		ItemStack first = pEntity.stacks.get(0);
		ItemStack second = pEntity.stacks.get(2);
		String recipe = getRecipe(first, second);
		if (!recipe.isEmpty() && (pEntity.nameCraft == null || pEntity.nameCraft.isEmpty())){
			pEntity.progress = 1;
			pEntity.isCrafted = true;
			pEntity.nameCraft = recipe;
		}
		if (pEntity.isCrafted(pEntity.nameCraft, first, second, pEntity)){
			pEntity.progress += 1;
			if (pEntity.progress == 160){
				pEntity.progress = 1;
				pEntity.isCrafted = false;
				ItemStack[] itemsForCraft = TableRecipes.recipes.get(pEntity.nameCraft);
				pEntity.setItem(1, itemsForCraft[2]);
				pEntity.setItem(0, new ItemStack(Items.AIR, 1));
				pEntity.setItem(2, new ItemStack(Items.AIR, 1));
			}
		} else if (pEntity.isCrafted) {
			pEntity.isCrafted = false;
			pEntity.nameCraft = "";
		}
	}

	public boolean isCrafted(String nameCraft, ItemStack ingredient, ItemStack fuel, TableBlockEntity pEntity){
		ItemStack[] itemsForCraft = TableRecipes.recipes.get(nameCraft);
		return pEntity.isCrafted && !nameCraft.isEmpty() && itemsForCraft[0].is(ingredient.getItem()) && itemsForCraft[1].is(fuel.getItem());
	}


	@Override
	public void setRemoved() {
		super.setRemoved();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}
}
