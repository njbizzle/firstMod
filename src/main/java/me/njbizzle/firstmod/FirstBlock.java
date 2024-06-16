package me.njbizzle.firstmod;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.ToIntFunction;

public class FirstBlock extends Block
{
    public FirstBlock()
    {
        super(BlockBehaviour.Properties.of()
            .speedFactor(1.5f)
            .jumpFactor(2f)
            .lightLevel(new ToIntFunction<BlockState>() {
                @Override
                public int applyAsInt(BlockState value) {
                    return 5;
                }
            })
            .sound(SoundType.AMETHYST)
            .destroyTime(1)
//            .lootFrom()
        );
    }
}