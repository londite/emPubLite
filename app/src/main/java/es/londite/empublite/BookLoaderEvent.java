package es.londite.empublite;

public class BookLoaderEvent {
    private BookContents contents=null;

    public BookLoaderEvent(BookContents contents) {
        this.contents=contents;
    }

    public BookContents getBook() {
        return (contents);
    }
}