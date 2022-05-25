package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.PlaylistService;
import at.fhv.ec.application.dto.PlayableSongDTO;
import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import at.fhv.ec.infrastructure.HibernateSongRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    @Inject
    HibernatePlaylistRepository playlistRepository;

    @Inject
    HibernateSongRepository songRepository;

    @Override
    public List<PlayableSongDTO> playlistByUsername(String username) throws NoSuchElementException {
        Playlist playlist = playlistRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        return playlist.getSongs().stream()
                        .map(songRepository::findBySongId)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(this::dtoFromSong)
                        .collect(Collectors.toList());
    }

    private PlayableSongDTO dtoFromSong(Song song) {
        return PlayableSongDTO.builder()
                .withSongId(song.getSongId().getUUID())
                .withAlbumName(song.getAlbumName())
                .withTitle(song.getTitle())
                .withDuration(song.getDuration())
                .withFilePath(song.getMp3Path())
                .withArtists(song.getArtists())
                .build();
    }
}
