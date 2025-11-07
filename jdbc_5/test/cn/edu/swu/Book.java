package cn.edu.swu;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String author;
    private double price;
    private String memo;
    private Date publish;

    public Book(int id,String name,String author,double price,String memo,Date publish){
        this.author = author;
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.publish = publish;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getMemo() {
        return memo;
    }

    public double getPrice() {
        return price;
    }

    public Date getPublish() {
        return publish;
    }
}
