package com.mcyixi.ffd.script;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FPSManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("FFD-FPSManager");

    private final FPSStats fpsStats;
    private final FPSRenderer fpsRenderer;
    private final FPSDisplayConfig config;

    public FPSManager(FPSDisplayConfig config) {
        this.config = config;
        this.fpsStats = new FPSStats();
        this.fpsRenderer = new FPSRenderer(fpsStats, config);

        LOGGER.info("FPS管理器初始化完成");
    }

    //注册所有事件监听器
    public void registerEvents() {
        // 注册客户端tick事件来统计FPS
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            fpsStats.onEndFrame();
        });

        // 注册HUD渲染事件来显示FPS
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            fpsRenderer.render(drawContext);
        });

        LOGGER.info("FPS事件监听器注册完成");
    }

    //获取FPS统计器
    public FPSStats getFpsStats() {
        return fpsStats;
    }

    //获取FPS渲染器
    public FPSRenderer getFpsRenderer() {
        return fpsRenderer;
    }

    //获取配置
    public FPSDisplayConfig getConfig() {
        return config;
    }

    //重新加载配置
    public void reloadConfig() {
        LOGGER.info("配置重新加载");
        //目前无内容
    }

    //获取当前FPS值
    public int getCurrentFPS() {
        return fpsStats.getCurrentFPS();
    }

    //切换FPS显示状态
    public void toggleDisplay() {
        fpsRenderer.toggle();
        LOGGER.info("FPS显示已{}", fpsRenderer.isEnabled() ? "启用" : "禁用");
    }

    //重置FPS统计
    public void resetStats() {
        fpsStats.reset();
        LOGGER.info("FPS统计已重置");
    }

    //启用背景显示
    public void enableBackground() {
        config.setShowBackground(true);
        LOGGER.info("FPS背景显示已启用");
    }

    //禁用背景显示
    public void disableBackground() {
        config.setShowBackground(false);
        LOGGER.info("FPS背景显示已禁用");
    }

    //切换背景显示状态
    public void toggleBackground() {
        boolean newState = !config.isShowBackground();
        config.setShowBackground(newState);
        LOGGER.info("FPS背景显示已{}", newState ? "启用" : "禁用");
    }

    //获取背景显示状态
    public boolean isBackgroundEnabled() {
        return config.isShowBackground();
    }
}
