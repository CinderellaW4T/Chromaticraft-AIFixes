package com.xianfish.aifix.mixins.late.chromaticraft;

import Reika.ChromatiCraft.TileEntity.AOE.TileEntityItemInserter;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.ChromatiCraft.Base.TileEntity.InventoriedChromaticBase;
import Reika.ChromatiCraft.API.Interfaces.LinkerCallback;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TileEntityItemInserter.class, remap = false)
public abstract class TileEntityItemInserterMixin extends InventoriedChromaticBase implements LinkerCallback {

    @Shadow
    private boolean[][] connections;

    /**
     * @reason 修复 物品放置器 在将物品放入紫色物品槽时产生的崩溃
     * @author
     */
    @Overwrite(remap = false)
    protected void onPreSlotSet(int slot, ItemStack is) {
        if (slot >= 0 && slot < TileEntityItemInserter.TARGETS) { // 添加边界检查
            if (!((TileEntityItemInserter)(Object)this).omniMode && !ReikaItemHelper.matchStacks(is, this.inv[slot])) {
                for (int i = 0; i < 6; i++) {
                    connections[slot][i] = false;
                }
            }
        }
    }
}