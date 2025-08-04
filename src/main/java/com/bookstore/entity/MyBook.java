package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="my_books")
@Data
public class MyBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String author;
    private String price;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;




    public MyBook( String name, String author, String price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public MyBook() {
    }

}
