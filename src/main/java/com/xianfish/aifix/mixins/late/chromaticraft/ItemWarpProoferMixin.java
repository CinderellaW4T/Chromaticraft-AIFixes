package com.xianfish.aifix.mixins.late.chromaticraft;

import Reika.ChromatiCraft.Base.ItemChromaTool;
import Reika.ChromatiCraft.ModInterface.ThaumCraft.ItemWarpProofer;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaThaumHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemWarpProofer.class, remap = false)
public abstract class ItemWarpProoferMixin extends ItemChromaTool {

    private static final String PLAYER_TAG = "owner_player";
    private static final String ACTIVITY_TAG = "last_dewarp";

    public ItemWarpProoferMixin(int index) {
        super(index);
    }
    /**
     * @reason 修复 灵气清除器 物品所有者检查逻辑，修复物品所有者无法使用的问题
     */
    @Overwrite(remap = false)
    public void onUpdate(ItemStack is, World world, Entity e, int par4, boolean par5) {
        if (e instanceof EntityPlayer && is.getItemDamage() == 1) {
            if (is.stackTagCompound == null) {
                is.stackTagCompound = new NBTTagCompound();
            }
            EntityPlayer ep = (EntityPlayer) e;
            if (!is.stackTagCompound.hasKey(PLAYER_TAG)) {
                is.stackTagCompound.setString(PLAYER_TAG, ep.getCommandSenderName());
            }
            if (ep.getCommandSenderName().equals(is.stackTagCompound.getString(PLAYER_TAG))) {
                if (world.getTotalWorldTime()-e.getEntityData().getLong(ACTIVITY_TAG) >= 240) {
                    ReikaThaumHelper.removeWarp(ep, 1);
                    e.getEntityData().setLong(ACTIVITY_TAG, world.getTotalWorldTime());
                }
            }
        }
    }
}