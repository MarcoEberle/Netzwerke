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
        String smiley = "src=\"https://www.br.de/puls/themen/netz/pepe-der-frosch-mem-100~_v-img__16__9__l_-1dc0e8f74459dd04c91a0d45af4972b9069f1135.jpg?version=4ca31\""; // https://upload.wikimedia.org/wikipedia/commons/8/8d/Smiley_head_happy.svg
        String path;

        //Starting a new server socket
        try (ServerSocket server = new ServerSocket(PORT)) {  //Server socket waits for request on PORT
            System.out.println("Server started!");
            while (true) {

                //Listens for a connection to be made to this socket and accepts it
                try (Socket socket = server.accept(); //Socket implements client socket > Socket is an endpoint for communication
                     BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())); //InputStreamReader decodes byte(InputStream) to character
                     BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"))) { //BufferedReader/Writer buffers characters to lines

                    //Reading and cutting the path TCP closed handler
                    path = fromClient.readLine(); //Reads line from BufferedReader
                    if (path == null) {
                        continue;
                    }
                    path = path.substring(4, path.indexOf("HTTP")); // Cuts out the path
                    System.out.println(path);

                    URL myUrl = new URL(host + path); //URL is a pointer to a resource on the internet (https://..)
                    HttpURLConnection httpsURL = (HttpURLConnection) myUrl.openConnection();

                    if (host.contains("https")) {
                        httpsURL = (HttpsURLConnection) myUrl.openConnection(); // Returns a URL connection instance that represents a connection referred by URL (myURL)
                    }

                    System.out.println("Content-type: " + httpsURL.getContentType());
                    System.out.println("Content-encoding: " + httpsURL.getContentEncoding());


                    try (BufferedReader fromServer = new BufferedReader(new InputStreamReader(httpsURL.getInputStream(), "ISO-8859-1"))) { //"ISO-8859-1"
                        System.out.println("Socket started!");

                        //Writing to client our new Header
                        String readLine = fromServer.readLine();
                        toClient.write("HTTP/1.1 200 OK\r\n");
                        toClient.write("\r\n");

                        //Manipulating our images to a smiley
                        while (readLine != null) {
                            System.out.println(readLine);
                            if (readLine.contains("src")) {
                                readLine = readLine.replaceFirst("src=\".*\" ", smiley);
                            }
                            readLine = modifyYeah(readLine);
                            toClient.write(readLine);
                            readLine = fromServer.readLine();
                        }
                        toClient.flush();  //Forces the BufferedWriter to write all buffered input to its destination
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Manipulates the string and adds to all listed words "(Yeah!)"
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
