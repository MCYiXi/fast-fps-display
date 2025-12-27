package com.mcyixi.ffd;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastFPSDisplay implements ModInitializer {
	public static final String MOD_ID = "fast-fps-display";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("FFD模组加载成功!");
	}
}