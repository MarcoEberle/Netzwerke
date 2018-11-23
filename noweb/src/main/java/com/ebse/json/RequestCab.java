package com.ebse.json;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("requestTime")
public class RequestCab {


    @POST
    @Path("add")
    @Produces("text/html; charset=UTF-8")
    public Response addUser(
            @FormParam("TaxiNr") int cabNumber,
            @FormParam("StartAdresse") String startAddress,
            @FormParam("EndAdresse") String endAddress,
            @FormParam("TimeArrive") int timeArrive){


        return Response.status(200)
                .entity("addUser is called, TaxiNr: " + cabNumber
                        + ", Startadresse: " + startAddress +
                        ", Zieladresse: " + endAddress + ", geschätzte Ankunftszeit: " + timeArrive)
                .build();

    }
}
