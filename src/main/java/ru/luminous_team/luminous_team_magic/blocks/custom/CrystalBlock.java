package ru.luminous_team.luminous_team_magic.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class CrystalBlock extends DirectionalBlock {
    public CrystalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos clickedPos = context.getClickedPos();
        BlockPos attachedPos = clickedPos.relative(context.getClickedFace().getOpposite());
        BlockState attachedBlockState = context.getLevel().getBlockState(attachedPos);

        // Проверка: блок можно ставить только на полный блок и не на себя.
        if (attachedBlockState.isFaceSturdy(context.getLevel(), attachedPos, context.getClickedFace())) {
            return this.defaultBlockState().setValue(FACING, context.getClickedFace());
        }
        return null; // Возвращает null, если блок нельзя поставить.
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedBlockState = world.getBlockState(attachedPos);
        return super.canSurvive(state, world, pos);
    }
}