package com.onlinebookstore.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorAndGetters() {
        Book book = new Book("The Alchemist", "Paulo Coelho", new BigDecimal("499.99"), LocalDate.of(1988, 4, 15));

        assertThat(book.getTitle()).isEqualTo("The Alchemist");
        assertThat(book.getAuthor()).isEqualTo("Paulo Coelho");
        assertThat(book.getPrice()).isEqualByComparingTo("499.99");
        assertThat(book.getPublishedDate()).isEqualTo(LocalDate.of(1988, 4, 15));
    }

    @Test
    void testSetters() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPrice(new BigDecimal("399.99"));
        book.setPublishedDate(LocalDate.of(1949, 6, 8));

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("1984");
        assertThat(book.getAuthor()).isEqualTo("George Orwell");
        assertThat(book.getPrice()).isEqualByComparingTo("399.99");
        assertThat(book.getPublishedDate()).isEqualTo(LocalDate.of(1949, 6, 8));
    }

    @Test
    void testValidation_Successful() {
        Book book = new Book("Clean Code", "Robert C. Martin", new BigDecimal("999.00"), LocalDate.of(2008, 8, 1));

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void testValidation_BlankFieldsAndInvalidPrice() {
        Book book = new Book("", "", new BigDecimal("0"), null);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(3);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .extracting(Object::toString)
                .contains("title", "author", "price");
    }
}
