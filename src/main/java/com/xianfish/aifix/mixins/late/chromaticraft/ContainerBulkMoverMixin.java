package com.xianfish.aifix.mixins.late.chromaticraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import Reika.ChromatiCraft.Container.ContainerBulkMover;
import Reika.ChromatiCraft.Registry.ChromaItems;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;

@Mixin(ContainerBulkMover.class)
public abstract class ContainerBulkMoverMixin extends Container {

    @Shadow
    private InventoryCrafting inventory;

    /**
     * 修复右键打开 物品搬运器 GUI界面后，若点击其他物品栏槽位而产生的崩溃
     */
    @Overwrite
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer ep) {
        boolean inGUI = slot == 0;
        if (inGUI) {
            ItemStack held = ep.inventory.getItemStack();
            ItemStack is = held != null ? ReikaItemHelper.getSizedItemStack(held, 1) : null;
            inventory.setInventorySlotContents(slot, is);
            return held;
        }
        // 添加边界检查，确保slot-1-27不为负数
        else if (slot > 27 && ChromaItems.BULKMOVER.matchWith(ep.inventory.getStackInSlot(slot-1-27))) {
            return ep.inventory.getItemStack();
        }
        else {
            return super.slotClick(slot, par2, par3, ep);
        }
    }
}