package at.fhv.ec.communication.rest;

import at.fhv.ec.application.api.PlaylistService;
import at.fhv.ec.application.dto.PlayableSongDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/playlists")
public class PlaylistController {

    @Inject
    PlaylistService playlistService;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(@PathParam("username") String username) {
        try {
            List<PlayableSongDTO> songList = playlistService.playlistByUsername(username);
            return Response.ok().entity(songList).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
