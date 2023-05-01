import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        int port = 8989;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

                    printWriter.println("Введите слово для поиска:");

                    String word = bufferedReader.readLine();

                    printWriter.println(engine.search(word));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



