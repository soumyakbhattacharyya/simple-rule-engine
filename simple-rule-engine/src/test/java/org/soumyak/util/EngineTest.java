
package org.soumyak.util;

import java.util.HashMap;
import java.util.Map;

import org.engine.core.DefaultRuleReader;
import org.engine.core.Engine;
import org.engine.core.Rule;
import org.junit.Test;

public class EngineTest {

	@Test
	public void testRcuPropertyGeneration() throws Exception {

		// get input
		// input.properties
		Map input = new HashMap();
		input.put(Rule.convertHyphenToUnderscore("aws-rds-enabled"), "true");

		// read rules
		Engine engine = Engine.builder().ruleReader(new DefaultRuleReader()).build();
		
		// run engine
		engine.execute(input);
		
		// validate outcome
		Map map = engine.getOutcomeGenerator().produceOutcome();

	}

	// private void computeRCU(Properties properties, CoreType core) {
	// String productId = "rcu";
	// ProductType product = ProductLookup.getProductNameById(core, productId);
	// boolean awsRdsEnabled = false;
	// if (product != null) {
	// ParamList paramList = product.getParamList();
	// if (paramList != null) {
	// for (Param p : paramList.getParamArray()) {
	// if (p.getId() != null && p.getId().equals("aws-rds-enabled")) {
	// if (p.getStringValue() != null &&
	// p.getStringValue().equalsIgnoreCase("true")) {
	// awsRdsEnabled = true;
	// }
	// }
	// }
	// if (awsRdsEnabled) {
	// updateProductDefaultIfNotExist(productId, properties, "db-sys-username",
	// "admin", paramList);
	// updateProductDefaultIfNotExist(productId, properties, "db-sys-role",
	// "default", paramList);
	// updateProductDefaultIfNotExist(productId, properties, "honor-omf",
	// "true", paramList);
	// }
	// updateProductDefaultIfNotExist(productId, properties, "db-url",
	// "jdbc:oracle:thin:@${core.product[rcu].param[db-host]}:${core.product[rcu].param[db-port]}/${core.product[rcu].param[db-service-name]}",
	// paramList);
	//
	// String globalDsTypeProp =
	// "core.product[weblogic].param[datasource-type]";
	// String dsType = properties.getProperty(globalDsTypeProp);
	// if ("gridlink".equalsIgnoreCase(dsType) &&
	// areDatabaseSourceValuesSet(properties)) {
	// updateProductDefaultIfNotExist(productId, properties, "db-runtime-url",
	// "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=${core.product[rcu].param[db-host]})(PORT=${core.product[rcu].param[db-port]})))(CONNECT_DATA=(SERVICE_NAME=${core.product[rcu].param[db-service-name]})))",
	// paramList);
	// } else {
	// updateProductDefaultIfNotExist(productId, properties, "db-runtime-url",
	// "${core.product[rcu].param[db-url]}", paramList);
	// }
	// }
	// }
	// }

}
