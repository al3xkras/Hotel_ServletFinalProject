package ua.alexkras.hotel.model;

public class Pageable {

    public static final int DEFAULT_ENTRIES_IN_PAGE = 10;

    private int pageNumber = 1;
    private int entriesStart;
    private final int entriesInPage;
    private final int totalPages;

    public Pageable( int entriesInPage, int totalItems) {
        this.entriesInPage = entriesInPage;
        this.totalPages = (int)Math.ceil((double)totalItems/entriesInPage);
        seekToPage(1);
    }

    public Pageable(int totalItems){
        this.entriesInPage = DEFAULT_ENTRIES_IN_PAGE;
        this.totalPages = (int)Math.ceil((double)totalItems/entriesInPage);
        seekToPage(1);
    }

    public boolean hasNext(){
        return pageNumber<totalPages;
    }

    public boolean hasPrevious(){
        return pageNumber!=1;
    }

    public void seekToPage(int pageNumber){
        pageNumber = Math.max(1,Math.min(pageNumber, totalPages));
        this.pageNumber=pageNumber;
        this.entriesStart = (pageNumber-1)*entriesInPage+1;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getEntriesStart() {
        return entriesStart;
    }

    public int getEntriesInPage() {
        return entriesInPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "pageNumber=" + pageNumber +
                ", entriesStart=" + entriesStart +
                ", entriesInPage=" + entriesInPage +
                ", totalPages=" + totalPages +
                '}';
    }
}
