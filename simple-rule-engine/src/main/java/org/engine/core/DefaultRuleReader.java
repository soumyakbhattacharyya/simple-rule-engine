package org.engine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.engine.core.Engine.RuleReader;


public class DefaultRuleReader implements RuleReader<Map<String,String>> {
	
	private Map<String,String> t;

	@Override
	public List<Rule> read() {	

		Rule rule = Rule.builder().name("AwsRdsEnabled")
				.expression("@INTERCEPT " + Rule.convertHyphenToUnderscore("aws-rds-enabled") + " == true;")
				.namespace("rcu").priority(0).action(new Action() {
					@Override
					public void execute() {
						System.out.println("populating outcome");
						getTarget().put("db-sys-username", "admin");
						getTarget().put("db-sys-role", "default");
						getTarget().put("honor-omf", "true");
					}
				}).build();
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(rule);
		return rules;
	}

	@Override
	public void setTarget(Map<String,String> t) {
		this.t = t;		
	}
	
	public Map<String, String> getTarget(){ return t;}

}
