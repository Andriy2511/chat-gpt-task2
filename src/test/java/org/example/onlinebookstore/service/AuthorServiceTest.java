package org.example.onlinebookstore.service;

import org.example.onlinebookstore.model.Author;
import org.example.onlinebookstore.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = List.of(new Author(1L, "Author 1", null));
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();
        assertEquals(1, result.size());
        assertEquals("Author 1", result.get(0).getName());
    }

    @Test
    public void testGetAuthorById() {
        Author author = new Author(1L, "Author 1", null);
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(1L);
        assertTrue(result.isPresent());
        assertEquals("Author 1", result.get().getName());
    }

    @Test
    public void testSaveAuthor() {
        Author author = new Author(1L, "Author 1", null);
        Mockito.when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.saveAuthor(author);
        assertEquals("Author 1", result.getName());
    }

    @Test
    public void testDeleteAuthor() {
        Long id = 1L;
        authorService.deleteAuthor(id);
        Mockito.verify(authorRepository, times(1)).deleteById(id);
    }
}
