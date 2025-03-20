package com.xianfish.aifix;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {
    @Override
    public String getMixinConfig() {
        return "mixins.aifix.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        List<String> mixins = new ArrayList<>();
        
        if (loadedMods.contains("ChromatiCraft")) {
            if (!loadedMods.contains("chromatifixes")) {
                mixins.add("chromaticraft.PylonGeneratorMixin");
                mixins.add("chromaticraft.ItemWarpProoferMixin");
            }
            mixins.add("chromaticraft.EssentiaNetworkMixin");
            mixins.add("chromaticraft.TileEntityItemInserterMixin");
            mixins.add("chromaticraft.ContainerBulkMoverMixin");
            mixins.add("chromaticraft.ItemSplineAttackMixin");
            mixins.add("chromaticraft.CommandableUpdateCheckerMixin");
        }

        // if (loadedMods.contains("GalacticraftCore")) {
        //     mixins.add("chromaticraft.ChunkProviderServerOverrideMixin");
        // }

        return mixins;
    }
}