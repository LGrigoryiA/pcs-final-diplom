import java.io.File;
import java.io.IOException;
import java.util.*;

//public class BooleanSearchEngine implements SearchEngine {
//    //???
//
//    public BooleanSearchEngine(File pdfsDir) throws IOException {
//        // прочтите тут все pdf и сохраните нужные данные,
//        // тк во время поиска сервер не должен уже читать файлы
//    }
//
//    @Override
//    public List<PageEntry> search(String word) {
//        // тут реализуйте поиск по слову
//        return Collections.emptyList();
//    }
//}

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {


    private Map<String, List<PageEntry>> pageEntryMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        String fileName;
        int count;
        String key;
        for (File file : pdfsDir.listFiles()) {
            fileName = file.getName();
            var doc = new PdfDocument(new PdfReader(file));
            int pages = doc.getNumberOfPages();
            for (int i = 1; i <= pages; i++) {

                var text = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);

                }
                for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                    count = entry.getValue();
                    key = entry.getKey();
                    pageEntryMapPut(i, count, key, fileName);
                }

            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову

        List<PageEntry> searchWord = pageEntryMap.get(word.toLowerCase());

        return searchWord;
    }


    public Map<String, List<PageEntry>> pageEntryMapPut(int page, int count, String key, String fileName) {
        PageEntry pageEntry = new PageEntry(fileName, page, count);

        List<PageEntry> pageEntryList;

        if (pageEntryMap.containsKey(key)) {
            pageEntryList = pageEntryMap.get(key);
        } else {
            pageEntryList = new ArrayList<>();
        }

        pageEntryList.add(pageEntry);
        Collections.sort(pageEntryList);
        pageEntryMap.put(key, pageEntryList);
        return pageEntryMap;
    }
}
