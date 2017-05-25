package org.engine.core;

import java.util.ArrayList;
import java.util.List;

import org.engine.core.Engine.RuleReader;

public final class RuleFactory {

	public static RuleFactory THIS = new RuleFactory();
	private List<Rule> rules = new ArrayList<Rule>();

	private RuleFactory() {
	}

	private RuleReader ruleReader;

	public static void registerReader() {
	}

}
