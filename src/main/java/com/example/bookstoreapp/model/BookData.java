package com.example.bookstoreapp.model;
import com.example.bookstoreapp.dto.BookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor

@Table(name = "book_data")
public class BookData {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_description")
    private String bookDescription;

    @Column(name = "book_image")
    private String bookImage;

    @Column(name = "book_price")
    private int bookPrice;

    @Column(name = "book_quantity")
    private int bookQuantity;

    public BookData(BookDTO bookDTO) {
        this.bookName = bookDTO.bookName;
        this.bookAuthor = bookDTO.bookAuthor;
        this.bookDescription = bookDTO.bookDescription;
        this.bookImage = bookDTO.bookImage;
        this.bookPrice = bookDTO.bookPrice;
        this.bookQuantity = bookDTO.bookQuantity;
    }
}
