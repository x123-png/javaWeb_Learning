package cn.edu.swu.ws.book;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String author;
    private double price;
    private String memo;
    private String picture;
    private Date publish;

    public Book(){

    }

    public Book(int id, String name, String author, double price,
                String memo, String picture, Date publish) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.memo = memo;
        this.picture = picture;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", memo='" + memo + '\'' +
                ", picture='" + picture + '\'' +
                ", publish=" + publish +
                '}';
    }
}
