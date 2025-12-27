package com.mcyixi.ffd.script;

import com.mcyixi.ffd.FastFPSDisplayClient;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class FPSCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(FPSCommand::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher,
                                         CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("fps")
                // 主命令：显示帮助信息
                .executes(context -> {
                    context.getSource().sendFeedback(Text.literal("§6=== FPS显示模组命令帮助 ==="));
                    context.getSource().sendFeedback(Text.literal("§a/fps toggle §7- 切换FPS显示"));
                    context.getSource().sendFeedback(Text.literal("§a/fps status §7- 显示当前状态"));
                    context.getSource().sendFeedback(Text.literal("§a/fps reset §7- 重置FPS统计"));
                    context.getSource().sendFeedback(Text.literal("§a/fps background §7- 背景显示相关命令"));
                    return 1;
                })

                // 切换FPS显示
                .then(literal("toggle")
                        .executes(context -> {
                            var fpsManager = FastFPSDisplayClient.getFpsManager();
                            if (fpsManager != null) {
                                fpsManager.toggleDisplay();
                                context.getSource().sendFeedback(
                                        Text.literal("§aFPS显示: " + (fpsManager.getFpsRenderer().isEnabled() ? "§2启用" : "§c禁用"))
                                );
                            } else {
                                context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                            }
                            return 1;
                        })
                )

                // 显示状态信息
                .then(literal("status")
                        .executes(context -> {
                            var fpsManager = FastFPSDisplayClient.getFpsManager();
                            if (fpsManager != null) {
                                String status = fpsManager.getFpsRenderer().isEnabled() ? "§2启用" : "§c禁用";
                                String bgStatus = fpsManager.isBackgroundEnabled() ? "§2启用" : "§c禁用";
                                int currentFPS = fpsManager.getCurrentFPS();

                                context.getSource().sendFeedback(
                                        Text.literal("§6=== FPS显示状态 ===")
                                );
                                context.getSource().sendFeedback(
                                        Text.literal("§7FPS显示: " + status)
                                );
                                context.getSource().sendFeedback(
                                        Text.literal("§7背景显示: " + bgStatus)
                                );
                                context.getSource().sendFeedback(
                                        Text.literal("§7当前FPS: §e" + currentFPS)
                                );
                            } else {
                                context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                            }
                            return 1;
                        })
                )

                // 重置统计
                .then(literal("reset")
                        .executes(context -> {
                            var fpsManager = FastFPSDisplayClient.getFpsManager();
                            if (fpsManager != null) {
                                fpsManager.resetStats();
                                context.getSource().sendFeedback(
                                        Text.literal("§aFPS统计已重置")
                                );
                            } else {
                                context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                            }
                            return 1;
                        })
                )

                // 背景显示相关命令
                .then(literal("background")
                        .executes(context -> {
                            context.getSource().sendFeedback(Text.literal("§6=== 背景显示命令 ==="));
                            context.getSource().sendFeedback(Text.literal("§a/fps background enable §7- 启用背景显示"));
                            context.getSource().sendFeedback(Text.literal("§a/fps background disable §7- 禁用背景显示"));
                            context.getSource().sendFeedback(Text.literal("§a/fps background toggle §7- 切换背景显示"));
                            context.getSource().sendFeedback(Text.literal("§a/fps background status §7- 显示背景状态"));
                            return 1;
                        })

                        // 启用背景
                        .then(literal("enable")
                                .executes(context -> {
                                    var fpsManager = FastFPSDisplayClient.getFpsManager();
                                    if (fpsManager != null) {
                                        fpsManager.enableBackground();
                                        context.getSource().sendFeedback(
                                                Text.literal("§aFPS背景显示已启用")
                                        );
                                    } else {
                                        context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                                    }
                                    return 1;
                                })
                        )

                        // 禁用背景
                        .then(literal("disable")
                                .executes(context -> {
                                    var fpsManager = FastFPSDisplayClient.getFpsManager();
                                    if (fpsManager != null) {
                                        fpsManager.disableBackground();
                                        context.getSource().sendFeedback(
                                                Text.literal("§cFPS背景显示已禁用")
                                        );
                                    } else {
                                        context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                                    }
                                    return 1;
                                })
                        )

                        // 切换背景
                        .then(literal("toggle")
                                .executes(context -> {
                                    var fpsManager = FastFPSDisplayClient.getFpsManager();
                                    if (fpsManager != null) {
                                        fpsManager.toggleBackground();
                                        context.getSource().sendFeedback(
                                                Text.literal("§aFPS背景显示已" + (fpsManager.isBackgroundEnabled() ? "启用" : "禁用"))
                                        );
                                    } else {
                                        context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                                    }
                                    return 1;
                                })
                        )

                        // 背景状态
                        .then(literal("status")
                                .executes(context -> {
                                    var fpsManager = FastFPSDisplayClient.getFpsManager();
                                    if (fpsManager != null) {
                                        String bgStatus = fpsManager.isBackgroundEnabled() ? "§2启用" : "§c禁用";
                                        context.getSource().sendFeedback(
                                                Text.literal("§7FPS背景显示: " + bgStatus)
                                        );
                                    } else {
                                        context.getSource().sendFeedback(Text.literal("§cFPS管理器未初始化!"));
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }
}
