package com.mcyixi.ffd.script;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FPSRenderer {
    private static final Logger LOGGER = LoggerFactory.getLogger("FFD-FPSRenderer");

    private final FPSStats fpsStats;
    private final FPSDisplayConfig config;

    public FPSRenderer(FPSStats fpsStats, FPSDisplayConfig config) {
        this.fpsStats = fpsStats;
        this.config = config;
        LOGGER.info("FPS渲染器初始化完成");
    }

    //渲染FPS
    public void render(DrawContext context) {
        if (!config.isEnabled()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options.hudHidden) {
            return;
        }

        int currentFPS = fpsStats.getCurrentFPS();
        renderFPS(context, currentFPS, config.getPositionX(), config.getPositionY());
    }

    //渲染FPS的具体实现
    private void renderFPS(DrawContext context, int fps, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        // 根据FPS值设置颜色
        Formatting color = getColorForFPS(fps);

        // 创建FPS文本
        Text fpsText = Text.literal(formatFPS(fps)).formatted(color);

        // 绘制背景
        if (config.isShowBackground()) {
            drawBackground(context, textRenderer, fpsText, x, y);
        }

        // 绘制FPS文本
        context.drawText(textRenderer, fpsText, x, y, 0xFFFFFF, true);
    }

    //根据FPS值获取对应的颜色
    private Formatting getColorForFPS(int fps) {
        if (fps >= 60) {
            return Formatting.GREEN;
        } else if (fps >= 30) {
            return Formatting.YELLOW;
        } else {
            return Formatting.RED;
        }
    }

    //格式化FPS显示文本
    private String formatFPS(int fps) {
        return String.format("FPS: %d", fps);
    }

    //绘制背景
    private void drawBackground(DrawContext context, TextRenderer textRenderer, Text text, int x, int y) {
        int textWidth = textRenderer.getWidth(text);
        int backgroundColor = 0x80000000; // 半透明黑色背景

        // 绘制背景矩形
        int padding = 2;
        context.fill(
                x - padding,
                y - padding,
                x + textWidth + padding,
                y + textRenderer.fontHeight + padding,
                backgroundColor
        );
    }

    //获取渲染器状态
    public boolean isEnabled() {
        return config.isEnabled();
    }

    //设置渲染器启用状态
    public void setEnabled(boolean enabled) {
        config.setEnabled(enabled);
    }

    //切换渲染器状态
    public void toggle() {
        setEnabled(!isEnabled());
    }
}
