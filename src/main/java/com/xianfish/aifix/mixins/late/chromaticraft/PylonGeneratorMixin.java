package com.xianfish.aifix.mixins.late.chromaticraft;

import com.xianfish.aifix.core.AiFixCore;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import Reika.ChromatiCraft.World.IWG.PylonGenerator;
import Reika.ChromatiCraft.World.IWG.PylonGenerator.PylonEntry;
import Reika.ChromatiCraft.Registry.CrystalElement;
import net.minecraft.world.World;
// import net.minecraft.util.ChatComponentText; // 未使用的库
// import net.minecraft.util.EnumChatFormatting; // 未使用的库
// import net.minecraft.entity.player.EntityPlayer; // 未使用的库

@Mixin(value = PylonGenerator.class, remap = false)
public class PylonGeneratorMixin {
    
// 解决在切换维度/备份存档等类似需要遍历pylon时而产生的报错。

    @Shadow(remap = false)
    private EnumMap<CrystalElement, Collection<PylonEntry>> colorCache;
    
    @Overwrite(remap = false)
    public PylonEntry getNearestPylonSpawn(World world, double x, double y, double z, CrystalElement e) {
        try {
            synchronized(this) {
                Collection<PylonEntry> c = this.colorCache.get(e);
                if (c == null) {
                    // sendErrorMessage("§c[AiFix] Pylon缓存为空，元素类型: " + e);
                    return null;
                }
                
                Collection<PylonEntry> safeCopy = Collections.unmodifiableCollection(c);
                double dist = Double.POSITIVE_INFINITY;
                PylonEntry close = null;
                
                for (PylonEntry loc : safeCopy) {
                    if (loc.location.dimensionID == world.provider.dimensionId) {
                        double d = loc.location.getDistanceTo(x, y, z);
                        if (d < dist) {
                            dist = d;
                            close = loc;
                        }
                    }
                }
                
                if (close == null) {
                    // sendErrorMessage("§e[AiFix] 未找到最近的Pylon，维度: " + world.provider.dimensionId + ", 元素: " + e);
                }
                
                return close;
            }
        } catch (Exception ex) {
            // sendErrorMessage("§4[AiFix] Pylon错误: " + ex.getMessage());
            return null;
        }
    }
    
    // 注释掉sendErrorMessage方法
    // private void sendErrorMessage(String message) {
    //     if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
    //         for (Object obj : FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList) {
    //             if (obj instanceof EntityPlayer) {
    //                 EntityPlayer player = (EntityPlayer) obj;
    //                 player.addChatMessage(new ChatComponentText(message));
    //             }
    //         }
    //     }
    // }
}