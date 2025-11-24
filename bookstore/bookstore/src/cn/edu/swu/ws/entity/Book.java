package cn.edu.swu.ws.entity;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String author;
    private double price;
    private String memo;
    private Date publish;

    public Book(){

    }

    public Book(int id, String name, String author, double price, String memo, Date publish) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.memo = memo;
        this.publish = publish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getPublish() {
        return publish;
    }

    public void setPublish(Date publish) {
        this.publish = publish;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", memo='" + memo + '\'' +
                ", publish=" + publish +
                '}';
    }
}
