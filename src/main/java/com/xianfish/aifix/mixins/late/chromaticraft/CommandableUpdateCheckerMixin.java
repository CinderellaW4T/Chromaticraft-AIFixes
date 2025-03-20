package com.xianfish.aifix.mixins.late.chromaticraft;

import Reika.DragonAPI.Auxiliary.Trackers.CommandableUpdateChecker;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWayMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;



@Mixin(value = CommandableUpdateChecker.class, remap = false)
public abstract class CommandableUpdateCheckerMixin {

    /**
     * 默认关闭Reika全家桶的版本更新检测。
     * 如果需要恢复检测，请使用 /checker enable MODID
     */

    @Shadow (remap = false)
    private final HashMap<DragonAPIMod, Boolean> overrides = new OneWayMap();

    @Overwrite (remap = false)
    private boolean shouldCheck(DragonAPIMod mod) {
        // 默认返回false，关闭版本更新检测
        return overrides.containsKey(mod) ? overrides.get(mod) : false;
    }
}