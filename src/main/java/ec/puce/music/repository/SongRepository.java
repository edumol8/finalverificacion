package ec.puce.music.repository;

import ec.puce.music.domain.Song;
import java.util.List;
import java.util.Optional;

public interface SongRepository {
  List<Song> findAll();

  Optional<Song> findById(Long id);

  Song save(Song song);

  boolean existsById(Long id);

  void deleteById(Long id);

  void deleteAll();
}
