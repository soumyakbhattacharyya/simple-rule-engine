package org.engine.core;

import java.util.HashMap;
import java.util.Map;

import org.engine.core.Engine.OutcomeGenerator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultOutcomeGenerator implements OutcomeGenerator {
	
	private final Map output;
	

	@Override
	public Map produceOutcome() {
		
		return output;
	}



}
