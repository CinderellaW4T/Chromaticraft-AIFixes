package com.xianfish.aifix.mixins.late.chromaticraft;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.WrapWithCondition;

import micdoodle8.mods.galacticraft.core.util.WorldUtil;

// 修复DragonAPI与GalacticraftCore的兼容问题。不确定是否会产生某种副作用。
// 未实现

@Mixin(value = ChunkProviderServer.class, priority = 1500 )
public class ChunkProviderServerOverrideMixin {

    @WrapWithCondition(
        method = "populate(Lnet/minecraft/world/chunk/IChunkProvider;II)V",
        at = @At(
            remap = false,
            target = "Lcpw/mods/fml/common/registry/GameRegistry;generateWorld(IILnet/minecraft/world/World;Lnet/minecraft/world/chunk/IChunkProvider;Lnet/minecraft/world/chunk/IChunkProvider;)V",
            value = "INVOKE"
        ),
        require = 0  // 唯一修改点：将原模组的require=1改为0。你也可以自己编译galicraftcore的相关代码，将require=1改为0。
    )
    private boolean galacticraft$overrideCheck(int chunkX, int chunkZ, World world,
            IChunkProvider chunkProvider, IChunkProvider chunkGenerator) {
        return !WorldUtil.otherModPreventGenerate(chunkX, chunkZ, world, chunkProvider, chunkGenerator);
    }
}