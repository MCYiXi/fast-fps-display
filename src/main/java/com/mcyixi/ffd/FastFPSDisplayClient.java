package com.mcyixi.ffd;

import com.mcyixi.ffd.script.FPSCommand;
import com.mcyixi.ffd.script.FPSDisplayConfig;
import com.mcyixi.ffd.script.FPSManager;
import net.fabricmc.api.ClientModInitializer;

public class FastFPSDisplayClient implements ClientModInitializer {
    private static FPSManager fpsManager;

    @Override
    public void onInitializeClient() {
        // 加载配置
        FPSDisplayConfig config = FPSDisplayConfig.load();

        // 初始化FPS管理器
        fpsManager = new FPSManager(config);

        // 注册事件监听器
        fpsManager.registerEvents();

        //注册命令
        FPSCommand.register();
    }

    public static FPSManager getFpsManager() {
        return fpsManager;
    }
}
