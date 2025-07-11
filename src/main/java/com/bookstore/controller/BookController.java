package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBook;
import com.bookstore.repository.MybookRepository;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MyBookService myBookService;
    @Autowired
     private MybookRepository mybookRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister() {
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public String availableBook(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "availableBook";
    }

    @GetMapping("/my_books")
    public String myBooks(Model model) {
        List<MyBook> myBooks = myBookService.getAll();
        model.addAttribute("books", myBooks);
        return "myBooks";
    }

    @GetMapping("/addToMyBook/{id}")
    public String addToMyBook(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        MyBook myBook = new MyBook(book.getId(), book.getName(), book.getAuthor(), book.getPrice());
        myBookService.save(myBook);
        return "redirect:/my_books";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteMyBook(@PathVariable("id") int id) {
        myBookService.deleteById(id);
        return "redirect:/my_books";
    }
    @GetMapping("/deleteBookFromAB/{id}")
    public String deleteFromABook(@PathVariable("id") int id) {
        bookService.deleteByIdFromAB(id);
        myBookService.deleteById(id);
        return "redirect:/available_books";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        bookService.save(b);
        return "redirect:/available_books";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam("keyword") String keyword, Model model) {
        List<Book> result = bookService.searchName(keyword);
        model.addAttribute("books", result);
        return "availableBook";
    }
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book book =bookService.getBookById(id);
        model.addAttribute("book", book);
        return "editBook";
    }


    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookService.save(book);// save() will update if id exists
        List<MyBook> relatedBooks = myBookService.getById(book.getId());

        for (MyBook myBook : relatedBooks) {
                myBook.setName(book.getName());
                myBook.setAuthor(book.getAuthor());
                myBook.setPrice(book.getPrice());
                myBookService.save(myBook);
        }
        return "redirect:/available_books";
    }



}
