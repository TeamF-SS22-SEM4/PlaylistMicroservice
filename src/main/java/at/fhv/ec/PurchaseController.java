package at.fhv.ec;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/purchase")
public class PurchaseController {

    // TODO: Change to appropriate consumes etc.
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response receivePurchase(@RequestBody String something) {
        // TODO: Implement logic after receiving new purchase of digital product
        System.out.println("Received new purchase");

        return Response.ok().build();
    }
}
