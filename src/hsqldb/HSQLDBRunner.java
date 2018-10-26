package hsqldb;

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

public class HSQLDBRunner implements ISQLServerRunner {
	
	private static LinkedBlockingQueue<Integer> systems = new LinkedBlockingQueue<>();
	static {
		for (int i = 1; i <= 1; i++)
			systems.add(i);
	}
	
	private Process serverProcess;
	private int pid;
	private int port;
	private Map<String, String> parameters;
	
	public HSQLDBRunner(Map<String, String> parameters) {
		super();
		this.parameters = parameters;
	}
	
	public SQLAdapter getAdapterForConnection() {
		return new SQLAdapter("hsqldb:hsql", "localhost", "SA", "", "testdb", new Properties());
	}

	@Override
	public int launchServer() {
		File workingFolder = new File("E:\\sql-dbms");
		File templateFolder = new File(workingFolder + File.separator + "hsqldb-2.4.0\\hsqldb");
		
		int id = 0;
		try {
			id = systems.take();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Now running the server with params " + parameters);
		port = 33000 + id;
		
		List<String> commands = new ArrayList<>();
//		String binary = templateFolder.getAbsolutePath() + "\\bin\\runServer.bat";
		commands.add("java");
		commands.add("-cp");
		commands.add(templateFolder.getAbsolutePath() + File.separator + "lib" + File.separator + "hsqldb.jar");
		commands.add("org.hsqldb.server.Server");
//		commands.add("--datadir");
//		commands.add(templateFolder + File.separator + "data_" + id + File.separator);
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
				try {
					ProcessUtil.killProcess(pid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
		return id;
	}

	@Override
	public void shutdownServer(int id) {
		System.out.println("Shutting down the house ...");
		serverProcess.destroy();
		systems.add(id);
	}

	public int getPort() {
		return port;
	}
}
