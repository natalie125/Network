import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client
{
	public static void main( String[] args ) throws IOException {
		Socket socket = new Socket("127.0.0.1", 6666);

		OutputStream os = socket.getOutputStream();

		if (args[0].equals("show")) {

			os.write(args[0].getBytes());

			socket.shutdownOutput();

			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			int b;
			while ((b = isr.read()) != -1) {
				System.out.print((char) b);
			}

		}else if (args[0].equals("item")) {
			os.write((args[0] + " " + args[1]).getBytes());

			socket.shutdownOutput();

			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			int b;
			while ((b = isr.read()) != -1) {
				System.out.print((char) b);
			}
		}else if (args[0].equals("bid")) {
			os.write((args[0] + " " + args[1] + " " + args[2]).getBytes());

			socket.shutdownOutput();

			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			int b;
			while ((b = isr.read()) != -1) {
				System.out.print((char) b);
			}
		}else{
			System.out.println("Please input the correct command line argument!");
		}

		socket.close();
	}
}