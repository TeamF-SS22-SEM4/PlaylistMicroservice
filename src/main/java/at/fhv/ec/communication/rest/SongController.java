package at.fhv.ec.communication.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/songs")
public class SongController {

    @GET
    @Path("/{songId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getSong(@PathParam("songId") UUID songId) {
        // TODO: Implement --> Return MP3 file for streaming
        return Response.ok().build();
    }
}
