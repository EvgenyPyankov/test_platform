package com.lilsmile;

import org.json.simple.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Random;

/**
 * Created by Smile on 08.11.15.
 */
@Path("/auth")
public class AuthorizeAndRegister implements Constants {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String logIn(String body)
    {
        JSONObject jsonObject = new JSONObject();
        Random random = new Random();
        int token = random.nextInt(59123);
        jsonObject.put(TOKEN, token);

        return jsonObject.toString();
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(String body)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(RESULT,OK);
        return jsonObject.toString();
    }


}
