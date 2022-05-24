package at.fhv.ec.communication.rest;

import at.fhv.ec.application.api.PlaylistService;
import at.fhv.ec.application.dto.PlayableSongDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

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
    @APIResponse(responseCode = "200",description = "")
    @APIResponse(responseCode = "404", description = "User not found")
    @APIResponse(responseCode = "403", description = "Not Authenticated") //TODO authorization so these can actually be returned
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @APIResponseSchema(value = PlayableSongDTO[].class, responseCode = "200")
    @Operation(operationId = "getPlaylist", summary = "Get the Playlist of user")
    public Response getPlaylist(@PathParam("username") String username) {
        try {
            List<PlayableSongDTO> songList = playlistService.playlistByUsername(username);
            return Response.ok().entity(songList).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
