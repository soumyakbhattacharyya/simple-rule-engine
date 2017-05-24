package org.engine.core;

import lombok.Builder;
import lombok.Getter;

/**
 * core rule class
 */

@Builder
@Getter
public class Rule {

	private final String name;
	private final String expression;
	private final Action outcome;
	private final int priority;
	private final String description;
	private final String namespace;
	
	
}
