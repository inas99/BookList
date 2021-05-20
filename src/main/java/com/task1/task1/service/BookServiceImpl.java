package com.task1.task1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task1.task1.dao.UserRepository;
import com.task1.task1.entity.Book;
import com.task1.task1.entity.Catalog;
import com.task1.task1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl {

    List<Book> books = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadBooksDetailsFromXML() {
        try {
            File file = new File("books.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Catalog que = (Catalog) jaxbUnmarshaller.unmarshal(file);
            List<Book> list = que.getBooks();
            if (!CollectionUtils.isEmpty(list)) {
                books.addAll(list);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        return !CollectionUtils.isEmpty(books) ? books : new ArrayList<>();
    }

    public List<Book> getAllAvailaBooks() {
        return !CollectionUtils.isEmpty(books)
                ? books.stream().filter(book -> !book.isBorrowed()).collect(Collectors.toList())
                : new ArrayList<>();
    }
/*
    public void borrowBook(String userEmail, String id, Model model) {
        if (!CollectionUtils.isEmpty(books)) {
            User user = userRepository.findByEmail(userEmail);
            for (Book book : books) {
                if (book.getId() == id && !book.isBorrowed() && user != null) {
                    book.setBorrowed(true);
                    book.setBorrowedBy(user.getFirstName());
                    List<Book> books = new ArrayList<>();
                    books.add(book);
                    model.addAttribute("bookList", books);
                    model.addAttribute("bookStatus", "Book Barrowed By " + user.getFirstName());
                    break;
                } else {
                    model.addAttribute("bookStatus", "No Books are available");
                }
            }
        }
    }
    */

    public void borrowBook(String userEmail, String bookIds) {
        if (!CollectionUtils.isEmpty(books)) {
            User user = userRepository.findByEmail(userEmail);
            for (Book book : books) {
                if (bookIds.equals(book.getId()) && !book.isBorrowed() && user != null) {
                    book.setBorrowed(true);
                    book.setBorrowedBy(user.getFirstName());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getRequestedBookIds(String bookIds) {
        List<String> requestedBookIds = null;
        try {
            requestedBookIds = new ObjectMapper().readValue(bookIds, ArrayList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !CollectionUtils.isEmpty(requestedBookIds) ? requestedBookIds : new ArrayList<>();
    }

    public List<Book> filterBooks(String author, String title, String description, String publishedDate) {
        List<Book> filteredBooks = new ArrayList<>();
        if (!CollectionUtils.isEmpty(books)) {
            filteredBooks.addAll(books.stream()
                    .filter(book -> filterByAuthor(book, author) && filterByTitle(book, title)
                            && filterByDesc(book, description) && filterByPublishDate(book, publishedDate))
                    .collect(Collectors.toList()));
        }
        return filteredBooks;
    }

    private boolean filterByAuthor(Book book, String author) {
        return StringUtils.isEmpty(author) ? Boolean.TRUE : book.getAuthor().matches("(?i).*" + author + ".*");
    }

    private boolean filterByTitle(Book book, String title) {
        return StringUtils.isEmpty(title) ? Boolean.TRUE : book.getTitle().matches("(?i).*" + title + ".*");
    }

    private boolean filterByDesc(Book book, String description) {
        System.out.println(book.getDescription());
        System.out.println(description);
        return StringUtils.isEmpty(description) ? Boolean.TRUE : book.getDescription().matches("(?is).*" + description + ".*");
    }

    private boolean filterByPublishDate(Book book, String publishDate) {
        return StringUtils.isEmpty(publishDate) ? Boolean.TRUE : book.getPublish_date().matches("(?i).*" + publishDate + ".*");
    }

    public void returnBook(String userEmail, String bookId) {
        if (!CollectionUtils.isEmpty(books)) {
            User user = userRepository.findByEmail(userEmail);
            for (Book book : books) {
                if (bookId.equals(book.getId()) && book.isBorrowed() && user.getFirstName().equals(book.getBorrowedBy())) {
                    book.setBorrowed(false);
                    book.setBorrowedBy(null);
                }
            }
        }
    }
}
