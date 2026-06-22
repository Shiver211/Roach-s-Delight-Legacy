package com.shiver.roachdelight.common;

import com.shiver.roachdelight.RoachDelight;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public final class ModCreativeTab {
    public static final CreativeTabs TAB = new CreativeTabs(RoachDelight.MOD_ID) {
        @Override
        @MethodsReturnNonnullByDefault
        public ItemStack createIcon() {
            return new ItemStack(ModItems.BOTTLED_ROACH);
        }
    };

    private ModCreativeTab() {
    }
}
