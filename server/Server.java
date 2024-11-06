import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server
{
	public static void main( String[] args ) throws IOException {
		ArrayList<ArrayList<String>> items = new ArrayList<>();

		ServerSocket ss = new ServerSocket(6666);

		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				3,
				30,
				60,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(2),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy()
		);

		while (true) {
			Socket socket = ss.accept();

			pool.submit(new MyRunnable(socket, items));
		}
	}
}