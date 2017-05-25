
package org.engine.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.engine.core.Rule.CompiledRule;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Engine {

	private final Map output = new HashMap();

	public void execute(Map input) {
		
		

		RuleReader<Map<String, String>> reader = new DefaultRuleReader();
		reader.setTarget(output);

		List<Rule> rules = reader.read();
		for (Rule rule : rules) {
			CompiledRule compiledRule = new CompiledRule(rule,input);
			compiledRule.eval();
		}

		OutcomeGenerator generator = new DefaultOutcomeGenerator(output);
		this.outcomeGenerator = generator;

	}

	private RuleReader ruleReader;
	private OutcomeGenerator outcomeGenerator;

	public interface RuleReader<Map> {
		List<Rule> read();

		void setTarget(Map t);
	}

	public interface OutcomeGenerator {
		Map produceOutcome();
	}

}
