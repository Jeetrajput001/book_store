package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBook;
import com.bookstore.entity.User;
import com.bookstore.repository.MybookRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MyBookService myBookService;
    @Autowired
    private MybookRepository mybookRepository;
    @Autowired
    UserRepository userRepository;

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
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html from templates
    }

    @GetMapping("/my_books")
    public String myBooks(Model model, Principal principal) {
        String username=principal.getName();
        User user=userRepository.findByUsername(username);

        List<MyBook> myBooks = mybookRepository.findByUser(user);
        model.addAttribute("books", myBooks);
        return "myBooks";
    }

    @GetMapping("/addToMyBook/{id}")
    public String addToMyBook(@PathVariable("id") int id,Principal principal,RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id);
        String username= principal.getName();
        User user=userRepository.findByUsername(username);
        // ✅ Check if book already exists in user's MyBook
        boolean exists = myBookService.existsByBookIdAndUser(id, user);
        if (exists) {
            redirectAttributes.addFlashAttribute("message", "📚 Book is already in your list!");
            return "redirect:/my_books";
        }
        MyBook myBook = new MyBook(book.getName(),book.getAuthor(),book.getPrice());

        myBook.setUser(user);
        myBook.setBookId(id);
        myBookService.save(myBook);
        redirectAttributes.addFlashAttribute("message", "✅ Book added to your list!");
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
        Book book = bookService.getBookById(id);
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
