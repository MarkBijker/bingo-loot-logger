package com.bingolootlogger;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("BingoLootLogger")
public interface BingoLootLoggerConfig extends Config
{
	@ConfigItem(
			keyName = "BingoItemList",
			name = "Bingo Item Rules",
			description = "Separate rules with semicolons. Use Item name: NPC name to restrict the source, or just Item name for any source. Example: Rune battleaxe: Giant Mole; Abyssal whip. Commas are also accepted."
	)
	default String BingoItemList()
	{
		return "";
	}
}
