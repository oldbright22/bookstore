package com.service.bookstore.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity(name="Books")
public class Book {

    @Id
    @NotNull(message = "Unique ID field that cannot be null.")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    //@Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;

    @NotNull(message = "Book title cannot be empty|null.")
    @Size(min=2, max=255, message="Book title should be of 2 to 255 chars length")
    //@Column(name = "bookName", columnDefinition = "VARCHAR(255)")
    private String bookName;

    @NotNull(message = "Book author cannot be empty|null.")
    @Size(min=2, max=150, message="Book author should be of 2 to 100 chars length")
    //@Column(name = "bookAuthor", columnDefinition = "VARCHAR(150)")
    private String bookAuthor;

    @NotNull(message = "Book price cannot be empty.")
    //@Min(1)
    //@Max(10000)
    //@Digits(fraction = 2, integer = 4)
    @DecimalMax("1000.00") @DecimalMin("1.00")
    //@Column(name = "bookPrice", columnDefinition = "DOUBLE")
    private double bookPrice;

    @Nullable
    //@Min(1)
    //@Max(10000)
    //@Digits(fraction = 2, integer = 4)
    @DecimalMax("500.00") @DecimalMin("0.00")
    //@Column(name = "discountedPrice", columnDefinition = "DOUBLE")
    private double discountedPrice;

    @NotNull (message = "Book published date cannot be empty")
    @Past(message = "date cannot be in the past")
    //@Column(name = "publishedDate", columnDefinition = "DATE")
    private LocalDate publishedDate;

    private BookStatus status;

    private BookRatings rating;
}
