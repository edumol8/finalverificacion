package ec.puce.music.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ec.puce.music.domain.Song;
import ec.puce.music.repository.SongRepository;
import ec.puce.music.web.ApiException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SongServiceTest {

  @Test
  void getWhenMissingShouldThrow404() {
    SongRepository repo = mock(SongRepository.class);
    when(repo.findById(99L)).thenReturn(Optional.empty());
    SongService service = new SongService(repo);

    ApiException ex = assertThrows(ApiException.class, () -> service.get(99L));
    assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
  }

  @Test
  void createShouldNullIdAndSave() {
    SongRepository repo = mock(SongRepository.class);
    Song input = new Song(777L, "Style", "Taylor Swift", "1989", 2014, 231);
    Song saved = new Song(1L, "Style", "Taylor Swift", "1989", 2014, 231);
    when(repo.save(any(Song.class))).thenReturn(saved);

    SongService service = new SongService(repo);
    Song result = service.create(input);

    assertEquals(1L, result.getId());
    verify(repo).save(argThat(s -> s.getId() == null));
  }

  @Test
  void updateWhenExistsShouldSaveWithId() {
    SongRepository repo = mock(SongRepository.class);
    when(repo.existsById(10L)).thenReturn(true);
    Song input = new Song(null, "Anti-Hero", "Taylor Swift", "Midnights", 2022, 200);
    Song saved = new Song(10L, "Anti-Hero", "Taylor Swift", "Midnights", 2022, 200);
    when(repo.save(any(Song.class))).thenReturn(saved);

    SongService service = new SongService(repo);
    Song result = service.update(10L, input);

    assertEquals(10L, result.getId());
    verify(repo).save(argThat(s -> s.getId().equals(10L)));
  }

  @Test
  void listShouldReturnAll() {
    SongRepository repo = mock(SongRepository.class);
    when(repo.findAll())
            .thenReturn(List.of(new Song(1L, "Love Story", "Taylor Swift", "Fearless", 2008, 235)));

    SongService service = new SongService(repo);
    assertEquals(1, service.list().size());
  }
}
