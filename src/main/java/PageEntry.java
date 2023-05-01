
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return Integer.compare(o.count, count);

    }


    @Override
    public String toString() {
        String string = toJsonPageEntry(this);
        return string;
    }

    public String toJsonPageEntry(PageEntry pageEntry) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(pageEntry);
    }
}