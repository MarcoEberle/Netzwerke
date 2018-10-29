
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

public class HTTPS {
    private final static int PORT = 8082;
    private static final List<String> list = Arrays.asList("MMIX", "Java", "Computer", "RISC", "CISC", "Debugger", "Informatik", "Student", "Studentin", "Studierende",
            "Windows", "Linux", "Software", "InformatikerInnen", "Informatiker", "Informatik");

    public static void main(String[] args) {

        String host = args[0]; 
        String smiley = "src=\"https://upload.wikimedia.org/wikipedia/commons/8/8d/Smiley_head_happy.svg\"";
        String path;

        //Starting a new server socket
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            while (true) {

                //Listens for a connection to be made to this socket and accepts it
                try (Socket socket = server.accept();
                     BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    //Reading and cutting the path
                    path = fromClient.readLine();
                    path = path.substring(4, path.indexOf("HTTP"));

                    System.out.println(path);

                    if (host.contains("https://")) {
                        System.out.println("HTTPS PATH");
                        URL myUrl = new URL(host + path);
                        HttpsURLConnection httpsURL = (HttpsURLConnection) myUrl.openConnection();

                        try (BufferedReader fromServer = new BufferedReader(new InputStreamReader(httpsURL.getInputStream()))) {
                            System.out.println("Socket started!");

                            //Writing to client our new Header
                            String readLine = fromServer.readLine();

                            toClient.write("HTTP/1.1 200 OK\r\n");
                            toClient.write("\r\n");

                            //Manipulating our images to a smiley
                            while (readLine != null) {
                                System.out.println(readLine);
                                if (readLine.contains("img src")) {
                                    readLine = readLine.replaceFirst("src=\".*\" ", smiley);

                                }
                                readLine = modifyYeah(readLine);
                                toClient.write(readLine);
                                readLine = fromServer.readLine();

                            }
                            toClient.flush();

                        }
                    } else {
                        System.out.println("HTTP PATH");
                        URL myUrl = new URL(host + path);
                        HttpURLConnection httpURL = (HttpURLConnection) myUrl.openConnection();
                        //Starting a new client socket at port 80
                        try (BufferedReader fromServer = new BufferedReader(new InputStreamReader(httpURL.getInputStream()))) {
                            System.out.println("Socket started!");

                            //Writing to client our new Header
                            String readLine = fromServer.readLine();
                            toClient.write("HTTP/1.0 200 OK\r\n");
                            toClient.write("\r\n");

                            //Manipulating our images to a smiley
                            while (readLine != null) {
                                if (readLine.contains("img src")) {
                                    readLine = readLine.replaceFirst("src=\".*\" ", smiley);

                                }
                                readLine = modifyYeah(readLine);
                                toClient.write(readLine);
                                readLine = fromServer.readLine();

                            }
                            toClient.flush();

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Manipulate the string and add to all listed words (Yeah!)
    public static String modifyYeah(String readLine) {
        for (int i = 0; i < list.size(); i++) {
            if (readLine.contains(list.get(i))) {

                readLine = readLine.replace("" + list.get(i), list.get(i) + " (yeah!)");
                return readLine;
            }

        }
        return readLine;
    }
}
