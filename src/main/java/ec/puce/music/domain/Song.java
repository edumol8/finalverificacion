package ec.puce.music.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class Song {

  private Long id;

  @NotBlank
  @Size(max = 120)
  private String title;

  @NotBlank
  @Size(max = 120)
  private String artist;

  @NotBlank
  @Size(max = 80)
  private String album;

  @Min(1900)
  @Max(2100)
  private int year;

  @Min(1)
  @Max(3600)
  private int durationSeconds;

  public Song() { }

  public Song(Long id, String title, String artist, String album, int year, int durationSeconds) {
    this.id = id;
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.year = year;
    this.durationSeconds = durationSeconds;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getDurationSeconds() {
    return durationSeconds;
  }

  public void setDurationSeconds(int durationSeconds) {
    this.durationSeconds = durationSeconds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Song)) {
      return false;
    }
    Song song = (Song) o;
    return year == song.year
        && durationSeconds == song.durationSeconds
        && Objects.equals(id, song.id)
        && Objects.equals(title, song.title)
        && Objects.equals(artist, song.artist)
        && Objects.equals(album, song.album);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, artist, album, year, durationSeconds);
  }
}
