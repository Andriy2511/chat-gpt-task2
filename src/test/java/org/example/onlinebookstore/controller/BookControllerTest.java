package org.example.onlinebookstore.controller;

import org.example.onlinebookstore.model.Author;
import org.example.onlinebookstore.model.Book;
import org.example.onlinebookstore.model.Genre;
import org.example.onlinebookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        List<Book> books = List.of(new Book(1L, "Book 1", 10.0, 5, author, genre));
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].author.name").value("Author Name"))
                .andExpect(jsonPath("$[0].genre.name").value("Fiction"));
    }

    @Test
    public void testCreateBook() throws Exception {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        Book book = new Book(1L, "Book 1", 10.0, 5, author, genre);
        Mockito.when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book 1\", \"price\":10.0, \"quantity\":5, \"author\":{\"id\":1, \"name\":\"Author Name\"}, \"genre\":{\"id\":1, \"name\":\"Fiction\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author.name").value("Author Name"))
                .andExpect(jsonPath("$.genre.name").value("Fiction"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(delete("/api/books/" + bookId))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).deleteBook(bookId);
    }

    @Test
    public void testSearchBooksByGenre() throws Exception {
        Genre genre = new Genre(1L, "Fiction", null);
        Author author = new Author(1L, "Author Name", null);
        List<Book> books = List.of(new Book(1L, "Book 1", 10.0, 5, author, genre));

        Mockito.when(bookService.searchBooksByGenre("Fiction")).thenReturn(books);

        mockMvc.perform(get("/api/books/search").param("genre", "Fiction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].genre.name").value("Fiction"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Genre oldGenre = new Genre(1L, "Fiction", null);
        Genre newGenre = new Genre(2L, "Non-Fiction", null);
        Author oldAuthor = new Author(1L, "Old Author", null);
        Author newAuthor = new Author(2L, "New Author", null);

        Book existingBook = new Book(1L, "Old Title", 10.0, 5, oldAuthor, oldGenre);
        Book updatedBookDetails = new Book(null, "New Title", 15.0, 10, newAuthor, newGenre);

        Mockito.when(bookService.getBookById(1L)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookService.saveBook(any(Book.class))).thenReturn(
                new Book(1L, "New Title", 15.0, 10, newAuthor, newGenre)
        );

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Title\",\"price\":15.0,\"quantity\":10," +
                                "\"author\":{\"id\":2,\"name\":\"New Author\"}," +
                                "\"genre\":{\"id\":2,\"name\":\"Non-Fiction\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.price").value(15.0))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.author.name").value("New Author"))
                .andExpect(jsonPath("$.genre.name").value("Non-Fiction"));
    }
}
