package org.engine.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.ast.ASTNode;
import org.mvel2.integration.Interceptor;
import org.mvel2.integration.VariableResolverFactory;

public class Main {

	public static void main(String[] args) {

		// input.properties
		Map vars = new HashMap();
		vars.put(convertHyphenToUnderscore("aws-rds-enabled"), "true");

		// input.product

		Rule rule = Rule.builder().name("AwsRdsEnabled")
   								  .expression("@Action " + convertHyphenToUnderscore("aws-rds-enabled") + " == true;").namespace("rcu").priority(0)
								  .outcome(new Action() {
									 @Override
									 public void execute() {
								 		System.out.println("executing some action");
									 }
								  }).build();

		CompiledRule compiledRule = new CompiledRule(rule, vars);
		MVEL.executeExpression(compiledRule.getCompiled(), vars);

	}

	public static String convertHyphenToUnderscore(String input) {
		input = "_" + input.replaceAll("\\-", "_");
		return input;
	}

	private static final class CompiledRule {
		private Rule rule;
		private Map map;
		private Serializable compiled;

		private CompiledRule(final Rule rule, final Map map) {
			this.rule = rule;
			this.map = map;

			// Create a new ParserContext
			ParserContext context = new ParserContext();

			Map<String, Interceptor> myInterceptors = new HashMap<String, Interceptor>();

			// Create a simple interceptor.
			Interceptor myInterceptor = new Interceptor() {
				public int doBefore(ASTNode node, VariableResolverFactory factory) {
					System.out.println("BEFORE!");
					return Interceptor.NORMAL_FLOW;
				}

				public int doAfter(Object value, ASTNode node, VariableResolverFactory factory) {
                    if (String.valueOf(value).equalsIgnoreCase("true")){
                    	rule.getOutcome().execute();
                    }
					return Interceptor.NORMAL_FLOW;
				}
			};

			// Now add the interceptor to the map.
			myInterceptors.put("Action", myInterceptor);

			// Add the interceptors map to the parser context.
			context.setInterceptors(myInterceptors);

			// this.compiled = 
			compiled = MVEL.compileExpression(rule.getExpression(), context);
		}

		private Serializable getCompiled() {
			return compiled;
		}

		private Rule getRule() {
			return rule;
		}

		private boolean eval() {
			return true; 
		}
	}

}
