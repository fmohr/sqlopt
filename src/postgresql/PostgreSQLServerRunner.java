package postgresql;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import jaicore.basic.SQLAdapter;
import jaicore.processes.ProcessUtil;
import optimizer.ISQLServerRunner;
import scala.annotation.meta.param;

public class PostgreSQLServerRunner implements ISQLServerRunner {
	
	private static LinkedBlockingQueue<Integer> systems = new LinkedBlockingQueue<>();
	static {
		for (int i = 1; i <= 1; i++)
			systems.add(i);
	}
	
	private Process serverProcess;
	private int pid;
	private int port;
	private Map<String, String> parameters;
	
	public PostgreSQLServerRunner(Map<String, String> parameters) {
		super();
		this.parameters = parameters;
	}
	
	public SQLAdapter getAdapterForConnection() {
		return new SQLAdapter("postgresql", "localhost", "benchmarker", "benchmark", "benchmark", new Properties());
	}

	@Override
	public int launchServer() {
		File workingFolder = new File("E:\\sql-dbms");
		File templateFolder = new File(workingFolder + File.separator + "pgsql");
		
		int idTmp = 0;
		try {
			idTmp = systems.take();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		final int id = idTmp;
		System.out.println("Now running the server with params " + parameters);
		port = 33000 + id;
		
		List<String> commands = new ArrayList<>();
		String binary = templateFolder.getAbsolutePath() + "\\bin\\postgres";
		commands.add(binary);
		commands.add("-D");
		commands.add(templateFolder + File.separator + "data");
//		commands.add("--port");
//		commands.add("" + port);
//		for (String param : parameters.keySet()) {
//			commands.add("--" + param + "=" + parameters.get(param));
//		}
		System.out.println(commands);
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.redirectOutput(Redirect.INHERIT);
		pb.redirectError(Redirect.INHERIT);
		
		
		
		pb.directory(templateFolder);
		try {
			serverProcess = pb.start();
			pid = ProcessUtil.getPID(serverProcess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Closing Connection");
				shutdownServer(id);
			}
		}));
		return id;
	}

	@Override
	public void shutdownServer(int id) {
		System.out.println("Shutting down the house ...");
		try {
			ProcessUtil.killProcess(pid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		systems.add(id);
	}

	public int getPort() {
		return port;
	}
}
