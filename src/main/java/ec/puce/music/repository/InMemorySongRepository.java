package ec.puce.music.repository;

import ec.puce.music.domain.Song;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemorySongRepository implements SongRepository {

  private final ConcurrentMap<Long, Song> storage = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  public InMemorySongRepository() {
    // Datos iniciales (Taylor Swift) para que la API se vea "viva" al arrancar
    save(new Song(null, "Blank Space", "Taylor Swift", "1989", 2014, 231));
    save(new Song(null, "Love Story", "Taylor Swift", "Fearless", 2008, 235));
    save(new Song(null, "Anti-Hero", "Taylor Swift", "Midnights", 2022, 200));
  }

  @Override
  public List<Song> findAll() {
    List<Song> list = new ArrayList<>(storage.values());
    list.sort(Comparator.comparing(Song::getId));
    return list;
  }

  @Override
  public Optional<Song> findById(Long id) {
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public Song save(Song song) {
    if (song == null) {
      throw new IllegalArgumentException("Song must not be null");
    }

    Song copy = copy(song);

    if (copy.getId() == null) {
      copy.setId(sequence.incrementAndGet());
    }

    storage.put(copy.getId(), copy);
    return copy(copy);
  }

  @Override
  public boolean existsById(Long id) {
    return storage.containsKey(id);
  }

  @Override
  public void deleteById(Long id) {
    storage.remove(id);
  }

  @Override
  public void deleteAll() {
    storage.clear();
  }

  private Song copy(Song song) {
    return new Song(
            song.getId(),
            song.getTitle(),
            song.getArtist(),
            song.getAlbum(),
            song.getYear(),
            song.getDurationSeconds());
  }
}
