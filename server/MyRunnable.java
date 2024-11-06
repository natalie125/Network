import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyRunnable implements Runnable{

    Socket socket;
    ArrayList<ArrayList<String>> items;

    public MyRunnable(Socket socket, ArrayList<ArrayList<String>> items){
        this.socket = socket;
        this.items = items;
    }

    @Override
    public void run() {
        try {
            StringBuilder sb = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            int b;
            while ((b = isr.read()) != -1){
                sb.append((char) b);
            }

            String str = "";

            if (sb.toString().equals("show")){
                if (items.isEmpty()) {
                    str = "There are currently no items in this auction.";
                }else {
                    for (ArrayList<String> item : items){
                        for (int i = 0; i < item.size(); i++) {
                            if (i != 2) {
                                str = str + item.get(i) + " : ";
                            }else {
                                str = str + item.get(i);
                            }
                        }
                        str = str + "\n";
                    }
                }
                OutputStream os = socket.getOutputStream();
                os.write(str.getBytes());

            }else if (sb.toString().split(" ")[0].equals("item")) {

                String name = sb.toString().split(" ")[1];

                str = "Success.";

                for (ArrayList<String> item : items) {
                    if(item.get(0).equals(name)) {
                        str = "Failure.";
                        break;
                    }
                }

                if (str.equals("Success.")) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(name);
                    list.add("0.0");
                    list.add("<no bids>");
                    items.add(list);
                }

                OutputStream os = socket.getOutputStream();
                os.write(str.getBytes());

            }else if(sb.toString().split(" ")[0].equals("bid")) {

                String name = sb.toString().split(" ")[1];
                String price = sb.toString().split(" ")[2];

                if (Double.parseDouble(price) <= 0) {
                    str = "Failure.";
                }else {
                    for (ArrayList<String> item : items) {
                        if(item.get(0).equals(name)) {
                            String originPrice = item.get(1);
                            if (Double.parseDouble(price) > Double.parseDouble(originPrice)) {
                                item.set(1, price);
                                InetAddress address = InetAddress.getByName("127.0.0.1");
                                String ip = address.getHostAddress();
                                item.set(2, ip);
                                str = "Accepted.";
                            } else {
                                str = "Rejected.";
                            }
                            break;
                        }
                        str = "Failure.";
                    }
                }

                OutputStream os = socket.getOutputStream();
                os.write(str.getBytes());
            }

            File file = new File("./server/log.txt");
            BufferedWriter out = null;
            if (!file.exists()){
                file.createNewFile();
                out = new BufferedWriter(new FileWriter(file));
            }else{
                out = new BufferedWriter(new FileWriter(file, true));
            }

            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd｜HH:mm:ss");
            Date date = new Date();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            String ip = address.getHostAddress();
            out.write(sdf.format(date) + "｜" + ip + "|" + sb.toString() + "\r\n");
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
