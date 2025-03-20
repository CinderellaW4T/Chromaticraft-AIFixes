package com.xianfish.aifix.mixins.late.chromaticraft;

import Reika.ChromatiCraft.Items.Tools.ItemSplineAttack;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockBox;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.BreadthFirstSearch;
import Reika.DragonAPI.Instantiable.Data.BlockStruct.OpenPathFinder.PassRules;
import Reika.DragonAPI.Instantiable.RayTracer;
import Reika.ChromatiCraft.Base.ItemChromaTool;
import Reika.ChromatiCraft.API.Interfaces.ProjectileFiringTool;

import net.minecraft.entity.EntityLivingBase;


import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.EnumSet;
import java.util.LinkedList;

@Mixin(value = ItemSplineAttack.class, remap = false)
public abstract class ItemSplineAttackMixin extends ItemChromaTool implements ProjectileFiringTool {

    public ItemSplineAttackMixin(int index) {
        super(index);
    }

    @Shadow(remap = false)
    private static RayTracer losTrace;

    /**
     * 修复 Bezier水晶 的问题。避免在视线范围内（或者类似况下）无生物使用时造成的空指针问题。
     */
    @Overwrite(remap = false)
    private LinkedList<Coordinate> getPath(EntityLivingBase from, EntityLivingBase to) {
        losTrace.getVisualLOS().setOrigins(from.posX, from.posY + from.height / 2, from.posZ, to.posX, to.posY + to.height / 2, to.posZ);
        if (losTrace.getVisualLOS().isClearLineOfSight(from.worldObj)) {
            LinkedList<Coordinate> li = new LinkedList<>();
            li.add(new Coordinate(from));
            li.add(new Coordinate(to));
            return li;
        }
        BlockBox box = BlockBox.between(from, to).expand(8, 6, 8);
        LinkedList<Coordinate> path = BreadthFirstSearch.getOpenPathBetween(from.worldObj, new Coordinate(from), new Coordinate(to), 16, box, EnumSet.allOf(PassRules.class)).getPath();
        
        if (path == null) {
            path = new LinkedList<>(); // 确保返回的LinkedList不为null
        }
        return path;
    }
}