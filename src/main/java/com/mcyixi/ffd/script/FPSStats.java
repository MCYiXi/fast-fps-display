package com.mcyixi.ffd.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FPSStats {
    private static final Logger LOGGER = LoggerFactory.getLogger("FFD-FPSStats");

    private long lastUpdateTime = 0;
    private int fps = 0;
    private int frameCount = 0;
    private boolean isPaused = false;

    public FPSStats() {
        LOGGER.info("FPS统计器初始化完成");
    }

    //在每帧结束时调用，用于统计FPS
    public void onEndFrame() {
        if (isPaused) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        // 每秒获取一次FPS
        if (currentTime - lastUpdateTime >= 1000) {
            fps = frameCount;
            frameCount = 0;
            lastUpdateTime = currentTime;
        }
        frameCount++;
    }

    //获取FPS
    public int getCurrentFPS() {
        return fps;
    }

    //重置统计器
    public void reset() {
        fps = 0;
        frameCount = 0;
        lastUpdateTime = System.currentTimeMillis();
    }

    //暂停
    public void pause() {
        isPaused = true;
    }

    //恢复
    public void resume() {
        isPaused = false;
        reset();
    }

    //判断是否暂停
    public boolean isPaused() {
        return isPaused;
    }
}
