package com.bingolootlogger;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BingoLootLoggerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BingoLootLoggerPlugin.class);
		RuneLite.main(args);
	}
}