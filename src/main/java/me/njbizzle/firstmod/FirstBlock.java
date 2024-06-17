package me.njbizzle.firstmod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
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
            .destroyTime(1)
        );

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(LIGHT_UP, false)
        );

    }

    @Override
    protected List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder p_287596_) {
        System.out.println("GET DROPS");

        System.out.println(this.getLootTable());

        LootParams lootparams = p_287596_.withParameter(LootContextParams.BLOCK_STATE, p_287732_).create(LootContextParamSets.BLOCK);
        ServerLevel serverlevel = lootparams.getLevel();
        LootTable loottable = serverlevel.getServer().reloadableRegistries().getLootTable(this.getLootTable());

        serverlevel.getServer().reloadableRegistries().get().registries().forEach(loot_ -> System.out.println(loot_.toString()));


        var loot = loottable.getRandomItems(lootparams);
        System.out.println(lootparams.toString());
        loot.forEach(loot_ -> System.out.println(loot_.getDisplayName()));
        return super.getDrops(p_287732_, p_287596_);
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