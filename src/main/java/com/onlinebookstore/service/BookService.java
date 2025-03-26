package com.onlinebookstore.service;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.exception.ResourceNotFoundExceptionClass;
import com.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
   //add a single book service
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }


    //Retrive all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //Reteive a single book
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionClass("Book not found with ID: " + id));
    }
    

        public List<Book> searchBooksByTitle(String title) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        }

    //  Update a Book
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id); // Throws exception if book is missing
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setPublishedDate(bookDetails.getPublishedDate());
        return bookRepository.save(book);
    }


    //  Delete a Single Book
    public void deleteBook(Long id) {
        Book book = getBookById(id); // Throws exception if book is missing
        bookRepository.delete(book);
    }
}
