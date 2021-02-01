package com.example.luservice.controller;

import com.example.luservice.model.Book;
import com.example.luservice.model.User;
import com.example.luservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", maxAge = 3600)
public class BooksController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("books")
    public ResponseEntity<List<Book>> getAllBooks(@AuthenticationPrincipal User user) {
        List<Book> books = bookRepository.findAll();
        if (user != null) {
            books.removeAll(user.getBooks());
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("auth/books")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }
}
