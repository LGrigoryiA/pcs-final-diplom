import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class
Main {
    public static void main(String[] args) throws Exception {


        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("бизнес"));

        var doc = new PdfDocument(new PdfReader("pdfs/Технология распознования лиц.pdf"));
        var text = PdfTextExtractor.getTextFromPage(doc.getPage(1));
        var words = text.split("\\P{IsAlphabetic}+");
        //System.out.println(Arrays.toString(words));
        Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
        for (var word : words) { // перебираем слова
            if (word.isEmpty()) {
                continue;
            }
            word = word.toLowerCase();
            freqs.put(word, freqs.getOrDefault(word, 0) + 1);

        }
        System.out.println(doc);
        System.out.println(freqs);
        System.out.println(doc.getNumberOfPages());
        System.out.println(freqs.get("лиц"));



/*
import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import java.net.Socket;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.util.HashMap;
        import java.util.Scanner;

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
        try (
                Socket socket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            Scanner in = new Scanner(System.in);
            System.out.println(bufferedReader.readLine());
            String word = in.nextLine();
            printWriter.println(word);
            System.out.println(jsonToString(bufferedReader.readLine()));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String jsonToString(String string) {

        if (string.equals("null"))
            return "такого слова нет";

        String s = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            Object jsonObject = mapper.readValue(string, Object.class);
            new TypeReference<HashMap<String, Object>>() {
            };
            s = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return s;*/
    }
}




