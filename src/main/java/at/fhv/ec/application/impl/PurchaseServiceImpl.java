package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.PurchaseService;
import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.playlist.PlaylistId;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
    @Inject
    Logger logger;

    @Inject
    HibernatePlaylistRepository hibernatePlaylistRepository;

    @Inject
    HibernateSongRepository hibernateSongRepository;


    @Override
    public void receivePurchase(DigitalProductPurchasedDTO event) {
        logger.info("Received new event " + event);
        // Check if playlist for user exists
        Optional<Playlist> playlistOpt = hibernatePlaylistRepository.findByUsername(event.getUsername());
        Playlist playlist;

        if(playlistOpt.isEmpty()) {
            // Create playlist if not
            logger.info("Couldn't find playlist for " + event.getUsername() + ".\nCreating new playlist");
            playlist = Playlist.create(
                    new PlaylistId(UUID.randomUUID()),
                    event.getUsername()
            );

            hibernatePlaylistRepository.persist(playlist);
        } else {
            logger.info("Found playlist for " + event.getUsername());
            playlist = playlistOpt.get();
        }

        event.getPurchasedSongs().forEach(songDTO -> {
            Optional<Song> songOpt = hibernateSongRepository.findByTitleAndAlbum(songDTO.getTitle(), event.getAlbumName());

            Song song;
            // Check if song already exists
            if(songOpt.isEmpty()) {
                // If not create song
                song = Song.create(
                        new SongId(UUID.randomUUID()),
                        event.getAlbumName(),
                        songDTO.getTitle(),
                        songDTO.getDuration(),
                        event.getArtists()
                );

                hibernateSongRepository.persist(song);
            } else {
                song = songOpt.get();
            }

            // Add song to playlist
            try {
                playlist.addSongToPlaylist(song.getSongId());
            } catch (UnsupportedOperationException e) {
                logger.info("Error at adding song to playlist\n" + e.getMessage());
            }
        });
    }
}
