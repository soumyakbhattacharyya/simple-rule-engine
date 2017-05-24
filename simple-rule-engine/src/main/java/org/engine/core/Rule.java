package org.engine.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.ast.ASTNode;
import org.mvel2.integration.Interceptor;
import org.mvel2.integration.VariableResolverFactory;

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
	private final Action action;
	private final int priority;
	private final String description;
	private final String namespace;

	public static String convertHyphenToUnderscore(String input) {
		input = "_" + input.replaceAll("\\-", "_");
		return input;
	}

	@Getter
	public static final class CompiledRule {
		private Rule rule;
		private Map input;
		private Serializable compiled;

		public CompiledRule(final Rule rule, final Map input) {
			this.rule = rule;
			this.input = input;

			// Create a new ParserContext
			ParserContext context = new ParserContext();

			Map<String, Interceptor> interceptors = new HashMap<String, Interceptor>();

			// Create intercepter
			Interceptor executionInterceptor = new Interceptor() {
				public int doBefore(ASTNode node, VariableResolverFactory factory) {
					return Interceptor.NORMAL_FLOW;
				}

				public int doAfter(Object value, ASTNode node, VariableResolverFactory factory) {
					if (String.valueOf(value).equalsIgnoreCase("true")) {
						rule.getAction().execute();
					}
					return Interceptor.NORMAL_FLOW;
				}
			};

			// Now add the interceptor to the map.
			interceptors.put("INTERCEPT", executionInterceptor);

			// Add the interceptors map to the parser context.
			context.setInterceptors(interceptors);

			// this.compiled =
			compiled = MVEL.compileExpression(rule.getExpression(), context);
		}

		private Serializable getCompiled() {
			return compiled;
		}

		public void eval() {
			MVEL.executeExpression(getCompiled(), getInput());
		}
	}

}
