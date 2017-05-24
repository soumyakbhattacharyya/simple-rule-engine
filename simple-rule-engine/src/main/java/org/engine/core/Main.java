package org.engine.core;

import java.util.HashMap;
import java.util.Map;

import org.engine.core.Rule.CompiledRule;

public class Main {

	public static void main(String[] args) {
		
		final Map output = new HashMap();

		// input.properties
		Map input = new HashMap();
		input.put(Rule.convertHyphenToUnderscore("aws-rds-enabled"), "true");

		// input.product

		Rule rule = Rule.builder().name("AwsRdsEnabled")
								  .expression("@INTERCEPT " + Rule.convertHyphenToUnderscore("aws-rds-enabled") + " == true;").namespace("rcu")
								  .priority(0)
								  .action(new Action() {
									@Override
									public void execute() {
										System.out.println("populating outcome");
										output.put("db-sys-username", "admin");
										output.put("db-sys-role", "default");
										output.put("honor-omf", "true");
									  }
								    }
								   )
								  .build();

		CompiledRule compiledRule = new CompiledRule(rule, input);
        compiledRule.eval();
	}

	

	

}
