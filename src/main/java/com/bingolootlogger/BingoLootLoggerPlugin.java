package com.bingolootlogger;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ServerNpcLoot;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Bingo Loot Logger"
)
public class BingoLootLoggerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BingoLootLoggerConfig config;

	@Inject
	private ItemManager itemManager;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	BingoLootLoggerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BingoLootLoggerConfig.class);
	}

	@Subscribe
	public void onServerNpcLoot(ServerNpcLoot event)
	{
		sendGameLootMessage(event);

	}

	private void sendGameLootMessage(ServerNpcLoot event) {
		// NPC DATA
		int npcId = event.getComposition().getId();
		var name = event.getComposition().getName();
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "You received loot from " + name + " (ID: " + npcId + ")", null);



		// DROPPED ITEM DATA
		event.getItems().forEach(item -> {
			int itemId = item.getId();
			int quantity = item.getQuantity();
			String itemName = getItemName(itemId);
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
					"lootname: <col=ff0000>" + itemName + "</col>" +
							" value: <col=ff0000>" + itemManager.getItemPrice(itemId) + "</col>" +
							" quantity: " + quantity +
							" ID: " + itemId, null);
		});
	}


	public String getItemName(int itemId) {
		return client.getItemDefinition(itemId).getName();
	}
}
