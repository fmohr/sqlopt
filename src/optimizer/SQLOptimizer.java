package optimizer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hasco.core.HASCOFD;
import hasco.core.Solution;
import hasco.eventlogger.HASCOSQLEventLogger;
import hasco.model.BooleanParameterDomain;
import hasco.model.Component;
import hasco.model.NumericParameterDomain;
import hasco.model.Parameter;
import hasco.model.ParameterRefinementConfiguration;
import hasco.query.Factory;
import hsqldb.HSQLDBRunner;
import jaicore.basic.IObjectEvaluator;
import jaicore.basic.SQLAdapter;
import jaicore.graphvisualizer.SimpleGraphVisualizationWindow;
import jaicore.planning.algorithms.forwarddecomposition.ForwardDecompositionSolution;
import jaicore.planning.graphgenerators.task.tfd.TFDNode;
import jaicore.planning.graphgenerators.task.tfd.TFDTooltipGenerator;
import jaicore.search.algorithms.standard.core.INodeEvaluator;
import jaicore.search.structure.core.Node;
import mysql.MySQLServerRunner;
import postgresql.PostgreSQLServerRunner;

public class SQLOptimizer {

	private HASCOFD<ISQLServerRunner> hasco;
	private SQLBenchmark sqlBenchmark;

	public SQLOptimizer() {

		/* read all data from reference database */
		try {
			sqlBenchmark = new SQLBenchmark(new SQLAdapter("isys-db.cs.upb.de", "mlplan", "UMJXI4WlNqbS968X", "mlplan_results"));
			sqlBenchmark.createTrajectoryForCopyingDatabase((int)Math.pow(10, 5));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* initialize HASCO with empty set of components */
		Factory<ISQLServerRunner> converter = componentInstance -> {
			String sqlServer = componentInstance.getComponent().getName();
			switch (sqlServer) {
			case "MySQL":
				return new MySQLServerRunner(componentInstance.getParameterValues());
			case "HSQLDB":
				return new HSQLDBRunner(componentInstance.getParameterValues());
			case "PostgreSQL":
				return new PostgreSQLServerRunner(componentInstance.getParameterValues());
			default:
				throw new UnsupportedOperationException("No runner for SQL-Server \"" + sqlServer + "\" has been defined.");
			}
		};
		INodeEvaluator<TFDNode, Double> nodeEvaluator = n -> null;
		String nameOfRequiredInterface = "SQLServer";
		IObjectEvaluator<ISQLServerRunner, Double> benchmark = server -> {

			System.out.println("Launching server");
			int id = server.launchServer();
			try {
				Thread.sleep(3000); // wait a second for the server to spawn prior to connecting
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SQLAdapter adapter = server.getAdapterForConnection();
			adapter.checkConnection();

			/* now send queries */
			System.out.println("Now sending queries ...");
			long start = System.currentTimeMillis();
			sqlBenchmark.executeTrajectory(adapter);
			ResultSet rs = adapter.getResultsOfQuery("SELECT * FROM evaluations");
			double performance = (double) (System.currentTimeMillis() - start);
			List<Integer> rows = new ArrayList<>();
			while (rs.next())
				rows.add(rs.getInt(1));
			server.shutdownServer(id);
			System.out.println("Entries: " + rows.size() + ": " + rows + ".");
			System.out.println("Time was " + performance);
			return performance;
		};
		Map<Component, Map<Parameter, ParameterRefinementConfiguration>> paramConfigs = new HashMap<>();
		hasco = new HASCOFD<>(converter, nodeEvaluator, paramConfigs, nameOfRequiredInterface, benchmark);
		hasco.registerListenerForSolutionEvaluations(new HASCOSQLEventLogger<>(new SQLAdapter("isys-db.cs.upb.de", "mlplan", "UMJXI4WlNqbS968X", "hasco")));

		/* definition of components */
		Component c = null;
		Parameter p;

		Map<Parameter, ParameterRefinementConfiguration> paramConfig = new HashMap<>();
		c = new Component("MySQL");
		c.addProvidedInterface("SQLServer");
//		p = new BooleanParameter("innodb_use_native_aio", true);
//		c.addParameter(p);
		// p = new BooleanParameter("big_tables", true);
		// c.addParameter(p);
		// p = new CategoricalParameter("innodb_page_size", new String[] { "4k", "8k", "16k" }, "8k");
		// c.addParameter(p);
//		p = new NumericParameter("innodb_buffer_pool_size", true, 134217728, 5242880, (int) Math.pow(1024, 3)); // allow memory usage of up to 1GB
//		paramConfig.put(p, new ParameterRefinementConfiguration(2, 10 * 1024 * 1024)); // 10 MB precision for this
//		c.addParameter(p);
//		paramConfigs.put(c, paramConfig);
		hasco.addComponent(c);

//		c = new Component("HSQLDB");
//		c.addProvidedInterface("SQLServer");
//		hasco.addComponent(c);
//
//		c = new Component("PostgreSQL");
//		c.addProvidedInterface("SQLServer");
//		hasco.addComponent(c);
	}

	public static void main(String[] args) {
		SQLOptimizer optimizer = new SQLOptimizer();
		new SimpleGraphVisualizationWindow<Node<TFDNode, Double>>(optimizer.hasco).getPanel().setTooltipGenerator(new TFDTooltipGenerator<>());
		for (Solution<ForwardDecompositionSolution, ISQLServerRunner> runner : optimizer.hasco) {
			System.out.println(runner.getSolution());
		}
	}

}
