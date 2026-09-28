package com.bingolootlogger;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface BingoLootLoggerConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}

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
