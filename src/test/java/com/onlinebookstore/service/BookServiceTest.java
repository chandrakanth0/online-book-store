package com.onlinebookstore.service;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.exception.ResourceNotFoundExceptionClass;
import com.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Test Book");
        sampleBook.setAuthor("Test Author");
        sampleBook.setPrice(BigDecimal.valueOf(29.99));
        sampleBook.setPublishedDate(LocalDate.of(2024, 1, 1));
    }

    @Test
    void testAddBook() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook);
        Book savedBook = bookService.addBook(sampleBook);
        assertEquals(sampleBook, savedBook);
        verify(bookRepository, times(1)).save(sampleBook);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(sampleBook);
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
        assertEquals(sampleBook, result.get(0));
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        Book result = bookService.getBookById(1L);
        assertEquals(sampleBook, result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptionClass.class, () -> bookService.getBookById(2L));
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdateBook_Success() {
        Book updatedDetails = new Book();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setAuthor("Updated Author");
        updatedDetails.setPrice(BigDecimal.valueOf(49.99));
        updatedDetails.setPublishedDate(LocalDate.of(2025, 1, 1));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book updatedBook = bookService.updateBook(1L, updatedDetails);

        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals(BigDecimal.valueOf(49.99), updatedBook.getPrice());
        assertEquals(LocalDate.of(2025, 1, 1), updatedBook.getPublishedDate());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        doNothing().when(bookRepository).delete(sampleBook);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(sampleBook);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptionClass.class, () -> bookService.deleteBook(2L));
        verify(bookRepository, times(1)).findById(2L);
    }
}
