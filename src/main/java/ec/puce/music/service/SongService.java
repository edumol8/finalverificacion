package ec.puce.music.service;

import ec.puce.music.domain.Song;
import ec.puce.music.repository.SongRepository;
import ec.puce.music.web.ApiException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SongService {

  private final SongRepository repository;

  public SongService(SongRepository repository) {
    this.repository = repository;
  }

  public List<Song> list() {
    return repository.findAll();
  }

  public Song get(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Song not found: " + id));
  }

  public Song create(Song song) {
    song.setId(null);
    return repository.save(song);
  }

  public Song update(Long id, Song song) {
    if (!repository.existsById(id)) {
      throw new ApiException(HttpStatus.NOT_FOUND, "Song not found: " + id);
    }
    song.setId(id);
    return repository.save(song);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ApiException(HttpStatus.NOT_FOUND, "Song not found: " + id);
    }
    repository.deleteById(id);
  }
}
