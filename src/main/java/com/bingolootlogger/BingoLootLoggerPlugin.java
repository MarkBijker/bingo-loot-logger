package com.bingolootlogger;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ServerNpcLoot;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
		Collection<ItemStack> itemStack = getItemStack(event);
		List<BingoItemRule> bingoItemRules = BingoItemRule.parse(config.BingoItemList());
		String npcName = event.getComposition().getName();

		List<ItemStack> bingoItems = itemStack.stream()
				.filter(item -> bingoItemRules.stream()
						.anyMatch(rule -> rule.matches(getItemName(item.getId()), npcName)))
				.collect(Collectors.toList());
		if (!bingoItems.isEmpty()) sendBingoLootMessage(event, bingoItems);
	}

	private void sendBingoLootMessage(ServerNpcLoot event, List<ItemStack> bingoItems) {
		String npcName = event.getComposition().getName();

		// TODO: Potentially add "M" or "K" abbreviations to the ItemPrice
		for (ItemStack bingoItem : bingoItems) {
			int bingoItemId = bingoItem.getId();
			client.addChatMessage(
					ChatMessageType.GAMEMESSAGE,
					"",
					"You received: <col=ff0000>" + getItemName(bingoItemId) +
							"</col> from: <col=ff0000>" + npcName +
							"</col> worth: <col=ff0000>" + getItemPrice(bingoItemId) +
							"</col> gp",
					null
			);
		}
	}
	private Collection<ItemStack> getItemStack(ServerNpcLoot event) {
		return event.getItems();
	}

	private int getItemPrice(int itemId) {
		return itemManager.getItemPrice(itemId);
	}

	public String getItemName(int itemId) {
		return client.getItemDefinition(itemId).getName();
	}

}
