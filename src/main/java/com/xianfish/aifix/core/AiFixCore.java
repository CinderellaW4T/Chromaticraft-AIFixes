package com.xianfish.aifix.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.entity.player.EntityPlayer;

/**
 * AiFix的主类
 * 负责mod的初始化和生命周期管理
 * 
 * @author XianFish
 */
@Mod(
    modid = AiFixCore.MODID,
    name = AiFixCore.NAME,
    version = AiFixCore.VERSION,
    dependencies = "after:ChromatiCraft;after:gtnhlib"
)
public class AiFixCore {
    public static final String MODID = "aifix";
    public static final String NAME = "AiFix";
    public static final String VERSION = "1.1"; // 确保这里有正确的版本号
    
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    
    @Mod.Instance(MODID)
    public static AiFixCore instance;
    
    public static AiFixCore getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return LOGGER;
    }
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("AiFix early");
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("AiFix loading");
        // 注册玩家登录事件监听器
        FMLCommonHandler.instance().bus().register(this);
    }
    
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.player;
            player.addChatMessage(new ChatComponentText("§a[AiFix] §f已部署 §7v" + VERSION));
        }
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("AiFix late");
    }
}