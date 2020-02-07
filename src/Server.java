import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Server {
    public static void main(String[] args) {
        String path = "csv-with-headers.csv";
        int port = 1024;
        try{

            ServerSocket ss = new ServerSocket(port); //создание сокета
            System.out.println("Waiting for client...");
            Socket socket = ss.accept(); //подключение клиента
            System.out.println("Client connected.");
            InputStream sin = socket.getInputStream(); //создание входного потока
            OutputStream sout = socket.getOutputStream(); //создние выходного потока
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            Reader reader = Files.newBufferedReader(Paths.get(path));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

            HashMap<String,String> dictionary = new HashMap<>();


            String [] nextRecord;
            while ((nextRecord = csvReader.readNext())!=null){
                dictionary.put(nextRecord[0],nextRecord[1]);
            }


            String key= "Clamidiosis";
            String value= "Josamycin";

            String word1 = null;

            try {
                word1 = in.readUTF(); //читает от клиента инфу
            } catch (Exception x) {
                x.printStackTrace();
            }

            //word1 = key;
            String answer= null;

            for(Map.Entry<String,String> item : dictionary.entrySet()){
                if(word1.equals(item.getKey())){
                    answer = item.getValue();
                    System.out.printf("Value: %s \n",item.getValue());
                    break;
                } else if (word1.equals((item.getValue()))) {
                    answer = item.getKey();
                    System.out.printf("Key: %s\n", item.getKey());
                    break;
                }
                else {
                    answer = "Nothing to show. Try again";
                    System.out.println("Nothing to show. Try again");
                }
            }

            System.out.println(word1);
            out.writeUTF(answer); //посылает ответ
            ss.close(); //закрытие сокета клиента
            socket.close(); //закрытие сокета сервера
            out.flush();

            } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
