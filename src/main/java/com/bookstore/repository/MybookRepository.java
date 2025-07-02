package com.bookstore.repository;

import com.bookstore.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MybookRepository extends JpaRepository<MyBook,Integer> {
}
