package com.bingolootlogger;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BingoItemRuleTest
{
	@Test
	public void matchesItemOnlyFromConfiguredNpc()
	{
		List<BingoItemRule> rules = BingoItemRule.parse("Rune battleaxe: Giant Mole");

		assertTrue(rules.get(0).matches("Rune battleaxe", "Giant Mole"));
		assertFalse(rules.get(0).matches("Rune battleaxe", "Chaos Elemental"));
		assertFalse(rules.get(0).matches("Dragon axe", "Giant Mole"));
	}

	@Test
	public void matchesItemWithoutNpcRestriction()
	{
		List<BingoItemRule> rules = BingoItemRule.parse("Abyssal whip");

		assertTrue(rules.get(0).matches("Abyssal whip", "Abyssal demon"));
		assertTrue(rules.get(0).matches("abyssal whip", "Abyssal sire"));
	}

	@Test
	public void parsesMultipleRulesAndIgnoresCaseAndWhitespace()
	{
		List<BingoItemRule> rules = BingoItemRule.parse(
				" Rune battleaxe : giant mole ; Abyssal whip, Dragon axe: Dagannoth Kings ");

		assertEquals(3, rules.size());
		assertTrue(rules.get(0).matches("RUNE BATTLEAXE", "Giant Mole"));
		assertTrue(rules.get(1).matches("Abyssal whip", "Abyssal sire"));
		assertTrue(rules.get(2).matches("Dragon axe", "Dagannoth Kings"));
		assertFalse(rules.get(2).matches("Dragon axe", "Giant Mole"));
	}
}
