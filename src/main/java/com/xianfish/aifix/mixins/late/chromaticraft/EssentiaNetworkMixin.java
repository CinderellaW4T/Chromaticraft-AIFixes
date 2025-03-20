package com.xianfish.aifix.mixins.late.chromaticraft;

import Reika.ChromatiCraft.ModInterface.ThaumCraft.EssentiaNetwork;
import thaumcraft.api.aspects.IEssentiaTransport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EssentiaNetwork.class, remap = false)
public abstract class EssentiaNetworkMixin {

// 修复 源质中继节点 在放置在罐子周围（包括许多要使用源质的物品）
// 然后罐子被破坏时，造成的空指针崩溃。问题同毗邻核心。

    @Inject(method = "isJar", at = @At("HEAD"), cancellable = true)
    private static void injectIsJar(IEssentiaTransport te, CallbackInfoReturnable<Boolean> cir) {
        if (te == null) {
            cir.setReturnValue(false);
        }
    }
}