package org.example.onlinebookstore.controller;

import org.example.onlinebookstore.model.Author;
import org.example.onlinebookstore.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testGetAllAuthors() throws Exception {
        List<Author> authors = List.of(new Author(1L, "Author 1", null));
        Mockito.when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Author 1"));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Author author = new Author(1L, "Author 1", null);
        Mockito.when(authorService.saveAuthor(Mockito.any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Author 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Author 1"));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Long authorId = 1L;

        mockMvc.perform(delete("/api/authors/" + authorId))
                .andExpect(status().isOk());

        Mockito.verify(authorService, times(1)).deleteAuthor(authorId);
    }
}
