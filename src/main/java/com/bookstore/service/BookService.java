package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.Bookrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private Bookrepository bookrepository;

    public void save(Book b){
        bookrepository.save(b);

    }
    public List<Book> getAll(){
       return bookrepository.findAll();
    }
    public Book getBookById(int id) {
        return bookrepository.findById(id).orElse(null);
    }
    public void deleteById(int id){
        bookrepository.deleteById(id);
    }


    public List<Book> searchName(String keyword) {
        return bookrepository.findByNameContainingIgnoreCase(keyword);
    }
}
