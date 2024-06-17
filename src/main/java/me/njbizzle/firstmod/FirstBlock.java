package me.njbizzle.firstmod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class FirstBlock extends Block
{
    public static final BooleanProperty LIGHT_UP = BooleanProperty.create("light_up");

    public FirstBlock()
    {
        super(BlockBehaviour.Properties.of()
            .speedFactor(1.5f)
            .jumpFactor(2f)
            .lightLevel(new ToIntFunction<BlockState>() {
                @Override
                public int applyAsInt(BlockState value) {
                    return value.getValue(LIGHT_UP) ? 10 : 0;
                }
            })
            .sound(SoundType.AMETHYST)
        );

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(LIGHT_UP, false)
        );

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_UP);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, BlockState state, Entity entity) {
        entity.addDeltaMovement(new Vec3(1, 1, 1));
        this.stateDefinition.any().setValue(LIGHT_UP, !state.getValue(LIGHT_UP));
        super.stepOn(level, pos, state, entity);
    }
}