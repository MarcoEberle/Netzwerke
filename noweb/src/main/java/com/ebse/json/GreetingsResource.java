package com.ebse.json;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("greetings")
public class GreetingsResource {

    @GET
    @Produces("text/html; charset=UTF-8")
    public Response getUser() {

        return Response.status(200).entity("ES LÄUUUUUUFTTTT!").build();

    }
}
