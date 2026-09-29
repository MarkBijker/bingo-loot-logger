package com.bingolootlogger;

import java.util.ArrayList;
import java.util.List;

final class BingoItemRule
{
	private final String itemName;
	private final String npcName;

	private BingoItemRule(String itemName, String npcName)
	{
		this.itemName = itemName;
		this.npcName = npcName;
	}

	static List<BingoItemRule> parse(String value)
	{
		List<BingoItemRule> rules = new ArrayList<>();
		if (value == null || value.trim().isEmpty())
		{
			return rules;
		}

		for (String entry : value.split("[;,]"))
		{
			String rule = entry.trim();
			if (rule.isEmpty())
			{
				continue;
			}

			int separator = rule.indexOf(':');
			String itemName = (separator < 0 ? rule : rule.substring(0, separator)).trim();
			String npcName = separator < 0 ? null : rule.substring(separator + 1).trim();
			if (itemName.isEmpty() || (npcName != null && npcName.isEmpty()))
			{
				continue;
			}

			rules.add(new BingoItemRule(itemName, npcName));
		}

		return rules;
	}

	boolean matches(String lootItemName, String lootNpcName)
	{
		if (lootItemName == null || itemName == null)
		{
			return false;
		}

		if (!itemName.equalsIgnoreCase(lootItemName.trim()))
		{
			return false;
		}

		if (npcName == null)
		{
			return true;
		}

		if (lootNpcName == null)
		{
			return false;
		}

		return npcName.equalsIgnoreCase(lootNpcName.trim());
	}
}
