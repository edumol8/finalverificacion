package ec.puce.music.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ec.puce.music.domain.Song;
import ec.puce.music.service.SongService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SongController.class)
class SongControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SongService service;

  @Test
  void listShouldReturn200AndArray() throws Exception {
    when(service.list())
            .thenReturn(
                    List.of(new Song(1L, "Blank Space", "Taylor Swift", "1989", 2014, 231)));

    mockMvc.perform(get("/api/songs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].title", is("Blank Space")));
  }

  @Test
  void getWhenNotFoundShouldReturn404() throws Exception {
    when(service.get(99L))
            .thenThrow(
                    new ApiException(HttpStatus.NOT_FOUND, "Song not found: 99"));

    mockMvc.perform(get("/api/songs/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", containsString("Song not found")));
  }

  @Test
  void createWhenInvalidShouldReturn400() throws Exception {
    String badJson =
            "{\"title\":\"\","
                    + "\"artist\":\"Taylor Swift\","
                    + "\"album\":\"1989\","
                    + "\"year\":1800,"
                    + "\"durationSeconds\":0}";

    mockMvc.perform(post("/api/songs")
                    .contentType("application/json")
                    .content(badJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Validation failed")))
            .andExpect(jsonPath("$.fields.title", not(emptyOrNullString())));
  }

  @Test
  void deleteShouldReturn204() throws Exception {
    doNothing().when(service).delete(1L);

    mockMvc.perform(delete("/api/songs/1"))
            .andExpect(status().isNoContent());
  }
}
