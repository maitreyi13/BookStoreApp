package com.example.bookstoreapp.repo;

import com.example.bookstoreapp.model.BookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookData, Long> {
    @Query(value = "SELECT * FROM book_data order by book_name ASC", nativeQuery = true)
    List<BookData> sortBookAscendingOrder();

    @Query(value = "SELECT * FROM book_data order by book_name DESC", nativeQuery = true)
    List<BookData> sortBookDescendingOrder();
    List<BookData> findBookByBookAuthor(String bookAuthor);

}