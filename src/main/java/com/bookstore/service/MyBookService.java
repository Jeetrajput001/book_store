package com.bookstore.service;

import com.bookstore.entity.MyBook;
import com.bookstore.repository.MybookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookService {
    @Autowired
    MybookRepository mybookRepository;

    public void save(MyBook b){
        mybookRepository.save(b);
    }
    public List<MyBook> getAll(){
        return mybookRepository.findAll();
    }

    public void deleteById(int id) {
        mybookRepository.deleteById(id);
    }
}
