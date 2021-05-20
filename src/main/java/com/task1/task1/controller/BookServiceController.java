package com.task1.task1.controller;

import com.task1.task1.dao.UserRepository;
import com.task1.task1.entity.Book;
import com.task1.task1.entity.User;
import com.task1.task1.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class BookServiceController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }
    @GetMapping("/main")
    public String viewMainPage() {
        return "main";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllBooks(Model model) {
        List<Book> allBooks = bookService.getAllBooks();
        model.addAttribute("listBooks", allBooks);
        return "available_books";
    }

    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAvailableFreeBooks(Model model) {
        List<Book> availableBooks= bookService.getAllAvailaBooks();
        model.addAttribute("listBooks", availableBooks);
        return "available_books";
    }

    /**@PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public String borrowBook(@RequestParam(name = "userEmail", required = true) String userEmail,
                             @RequestParam(name = "bookId", required = true) String bookId, Model model) {
        bookService.borrowBook(userEmail, bookId, model);
        return "books";
    }*/


    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public String filterBooks(@RequestParam(name = "author", required = false) String author,
                                  @RequestParam(name = "title", required = false) String title,
                                  @RequestParam(name = "description", required = false) String desc,
                                  @RequestParam(name = "publishedDate", required = false) String publishedDate, Model model) {
        List<Book> filterBooks = bookService.filterBooks(author, title, desc, publishedDate);
        model.addAttribute("filterBooks", filterBooks);
        return "filter";
    }

//    @PostMapping(value = "/book")
//    public String book(Authentication authentication,
//                             @RequestParam(name = "requestedBooks1", required = false) List<String> requestedBooks,
//                             @RequestParam(name = "action", required = false) String action,
//                             Model model) {
//        System.out.println(authentication.getName());
//        System.out.println(action);
//        for (String i : requestedBooks) {
//            System.out.println(i);
//        }
////        if (action == "borrow") {
////            bookService.borrowBook(userEmail, bookId, model);
////        }
//        model.addAttribute("listBooks", bookService.getAllBooks());
//        return "index";
//    }

    @GetMapping("/action")
    public String action(Authentication authentication, Model model,
                         @RequestParam(name = "requestedBooks", required = false) List<String> requestedBooks,
                         @RequestParam(name = "action", required = false) String action) {
        if (action.equals("borrow")) {
            for (String bookId : requestedBooks) {
                bookService.borrowBook(authentication.getName(), bookId);
            }
        } else if (action.equals("return")) {
            for (String bookId : requestedBooks) {
                bookService.returnBook(authentication.getName(), bookId);
            }
        }
        List<Book> allBooks = bookService.getAllBooks();
        model.addAttribute("listBooks", allBooks);
        return "books";
    }

}