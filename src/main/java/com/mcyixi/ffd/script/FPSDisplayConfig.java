package com.mcyixi.ffd.script;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FPSDisplayConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
            FabricLoader.getInstance().getConfigDir().toFile(),
            "fast-fps-display.json"
    );

    // 配置项
    public boolean enabled = true;
    public int positionX = 5;
    public int positionY = 5;
    public boolean showBackground = false;
    public int updateInterval = 1000; // 毫秒

    // 保存配置
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            com.mcyixi.ffd.FastFPSDisplay.LOGGER.error("无法保存配置", e);
        }
    }

    // 加载配置
    public static FPSDisplayConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, FPSDisplayConfig.class);
            } catch (IOException e) {
                com.mcyixi.ffd.FastFPSDisplay.LOGGER.error("无法加载配置", e);
            }
        }

        // 如果配置文件不存在，创建默认配置并保存
        FPSDisplayConfig config = new FPSDisplayConfig();
        config.save();
        return config;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        save();
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
        save();
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
        save();
    }

    public void setShowBackground(boolean showBackground) {
        this.showBackground = showBackground;
        save();
    }

    public boolean isShowBackground() {
        return showBackground;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
        save();
    }
}
