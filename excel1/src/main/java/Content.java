import java.util.List;

public class Content {
    public String head;
    public String end;
    public List<String> authors;
    public String title;

    public Content() {
    }

    public String getHead() {
        return this.head;
    }

    public String getEnd() {
        return this.end;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public String getTitle() {
        return this.title;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Content{" +
                "head='" + head + '\'' +
                ", end='" + end + '\'' +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                '}';
    }
}
