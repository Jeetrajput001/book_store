package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bookrepository  extends JpaRepository<Book,Integer> {
    List<Book> findByNameContainingIgnoreCase(String name);

}
