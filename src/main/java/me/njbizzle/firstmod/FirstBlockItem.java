package me.njbizzle.firstmod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.RegistryObject;
import me.njbizzle.firstmod.FirstBlock;

public class FirstBlockItem extends BlockItem
{
    public FirstBlockItem(RegistryObject<FirstBlock> firstBlock)
    {
        super(firstBlock.get(), new Item.Properties());
    }
}