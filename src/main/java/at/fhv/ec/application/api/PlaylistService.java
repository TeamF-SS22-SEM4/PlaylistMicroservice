package at.fhv.ec.application.api;

import at.fhv.ec.application.dto.PlayableSongDTO;

import java.util.List;
import java.util.NoSuchElementException;

public interface PlaylistService {
    List<PlayableSongDTO> playlistByUsername(String username) throws NoSuchElementException;
}
