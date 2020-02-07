import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket sock = null;
        String address = "127.0.0.1";
        int port = 1024;
        try {
            sock = new Socket(InetAddress.getByName(address), port);
            InputStream sin = sock.getInputStream();
            OutputStream sout = sock.getOutputStream();
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            Scanner input = new Scanner(System.in);
            String i = input.nextLine();

            out.writeUTF(i);
            out.flush();
            i = in.readUTF();
            System.out.println(i);

            sock.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
