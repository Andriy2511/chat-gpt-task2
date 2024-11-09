package org.example.onlinebookstore.controller;

import org.example.onlinebookstore.model.Genre;
import org.example.onlinebookstore.service.GenreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    public void testGetAllGenres() throws Exception {
        List<Genre> genres = List.of(new Genre(1L, "Fiction", null));
        Mockito.when(genreService.getAllGenres()).thenReturn(genres);

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Fiction"));
    }

    @Test
    public void testGetGenreByGenre() throws Exception {
        Genre genre = new Genre(1L, "Fiction", null);
        Mockito.when(genreService.getGenreById(anyLong())).thenReturn(Optional.of(genre));

        mockMvc.perform(get("/api/genres/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void testCreateGenre() throws Exception {
        Genre genre = new Genre(1L, "Fiction", null);
        Mockito.when(genreService.saveGenre(Mockito.any(Genre.class))).thenReturn(genre);

        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fiction\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fiction"));
    }

    @Test
    public void testDeleteGenre() throws Exception {
        Long genreId = 1L;

        mockMvc.perform(delete("/api/genres/" + genreId))
                .andExpect(status().isOk());

        Mockito.verify(genreService, times(1)).deleteGenre(genreId);
    }
}