package org.example.onlinebookstore.service;

import org.example.onlinebookstore.model.Author;
import org.example.onlinebookstore.model.Book;
import org.example.onlinebookstore.model.Genre;
import org.example.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetAllBooks() {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        List<Book> books = List.of(new Book(1L, "Book 1", 10.0, 5, author, genre));
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
    }

    @Test
    public void testGetBookById() {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        Book book = new Book(1L, "Book 1", 10.0, 5, author, genre);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("Book 1", result.get().getTitle());
    }

    @Test
    public void testSaveBook() {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        Book book = new Book(1L, "Book 1", 10.0, 5, author, genre);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.saveBook(book);
        assertEquals("Book 1", result.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Long id = 1L;
        bookService.deleteBook(id);
        Mockito.verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSearchBooksByTitle() {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        List<Book> books = List.of(new Book(1L, "Book 1", 10.0, 5, author, genre));
        String title = "Book 1";

        Mockito.when(bookRepository.findByTitle(title)).thenReturn(books);

        List<Book> result = bookService.searchBooksByTitle(title);
        assertEquals(1, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
    }

    @Test
    public void testSearchBooksByAuthor() {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        List<Book> books = List.of(new Book(1L, "Book 1", 10.0, 5, author, genre));
        String authorName = "Author Name";

        Mockito.when(bookRepository.findByAuthorName(authorName)).thenReturn(books);

        List<Book> result = bookService.searchBooksByAuthor(authorName);
        assertEquals(1, result.size());
        assertEquals("Author Name", result.get(0).getAuthor().getName());
    }

    @Test
    public void testSearchBooksByGenre() {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        List<Book> books = List.of(new Book(1L, "Book 1", 10.0, 5, author, genre));
        String genreName = "Fiction";

        Mockito.when(bookRepository.findByGenreName(genreName)).thenReturn(books);

        List<Book> result = bookService.searchBooksByGenre(genreName);
        assertEquals(1, result.size());
        assertEquals("Fiction", result.get(0).getGenre().getName());
    }
}
