package ec.puce.music.web;

import ec.puce.music.domain.Song;
import ec.puce.music.service.SongService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/songs")
public class SongController {

  private final SongService service;

  public SongController(SongService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Song>> list() {
    return ResponseEntity.ok(service.list());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Song> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.get(id));
  }

  @PostMapping
  public ResponseEntity<Song> create(@Valid @RequestBody Song song) {
    Song created = service.create(song);
    return ResponseEntity.created(URI.create("/api/songs/" + created.getId())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Song> update(@PathVariable Long id, @Valid @RequestBody Song song) {
    return ResponseEntity.ok(service.update(id, song));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
