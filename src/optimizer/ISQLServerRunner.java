package optimizer;

import jaicore.basic.SQLAdapter;

public interface ISQLServerRunner {
	public SQLAdapter getAdapterForConnection();
	public int launchServer();
	public void shutdownServer(int id);
	public int getPort();
}
