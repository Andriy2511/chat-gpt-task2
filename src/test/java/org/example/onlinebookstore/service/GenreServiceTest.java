package org.example.onlinebookstore.service;

import org.example.onlinebookstore.model.Genre;
import org.example.onlinebookstore.repository.GenreRepository;
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
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    public void testGetAllGenres() {
        List<Genre> genres = List.of(new Genre(1L, "Fiction", null));
        Mockito.when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.getAllGenres();
        assertEquals(1, result.size());
        assertEquals("Fiction", result.get(0).getName());
    }

    @Test
    public void testGetGenreById() {
        Genre genre = new Genre(1L, "Fiction", null);
        Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.getGenreById(1L);
        assertTrue(result.isPresent());
        assertEquals("Fiction", result.get().getName());
    }

    @Test
    public void testSaveGenre() {
        Genre genre = new Genre(1L, "Fiction", null);
        Mockito.when(genreRepository.save(genre)).thenReturn(genre);

        Genre result = genreService.saveGenre(genre);
        assertEquals("Fiction", result.getName());
    }

    @Test
    public void testDeleteGenre() {
        Long id = 1L;
        genreService.deleteGenre(id);
        Mockito.verify(genreRepository, times(1)).deleteById(id);
    }
}
