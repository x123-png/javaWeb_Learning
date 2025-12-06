package cn.edu.swu.ws.book;

import java.util.List;

public class BookPage {
    int pages;
    int page;
    int size;
    List<Book> books;

    public BookPage() {
    }

    public BookPage(int pages, int page, int size, List<Book> books) {
        this.pages = pages;
        this.page = page;
        this.size = size;
        this.books = books;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookPage{" +
                "pages=" + pages +
                ", page=" + page +
                ", size=" + size +
                ", books=" + books +
                '}';
    }

}
