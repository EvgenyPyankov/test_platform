package com.lilsmile;

import bd.DBContorller;
import bd.DBControllerMethods;
import bd.User;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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

    DBControllerMethods dbController = new DBContorller();


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String logIn(String body)
    {
        JSONObject jsonBody = (JSONObject) JSONValue.parse(body);
        String login = (String) jsonBody.get(LOGIN);
        String password = (String) jsonBody.get(PASSWORD);
        JSONObject response = new JSONObject();
        if (dbController.getUserByEmail(login).equals(null) || dbController.getUserByLogin(login).equals(null))
        {
            response.put(RESULT, WRONG_LOGIN);
        } else
        {
            //todo: add passwd check
            char[] arr = login.toCharArray();
            response.put(TOKEN, generateToken(arr));
        }
        return response.toString();
    }

    private String generateToken(char[] arr)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<arr.length; i++)
        {
            sb.append((char)(arr[i]+i*2));
        }
        return sb.toString();
    }


    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(String body)
    {
        JSONObject response = new JSONObject();
        JSONObject jsonBody = (JSONObject) JSONValue.parse(body);
        String email = (String) jsonBody.get(EMAIL);
        String login = (String) jsonBody.get(LOGIN);
        String password = (String) jsonBody.get(PASSWORD);
        if (dbController.getUserByLogin(login).equals(null))
        {
            if (dbController.getUserByEmail(email).equals(null))
            {
                dbController.addUser(new User(login, email, password.hashCode()));
                response.put(TOKEN, generateToken(login.toCharArray()));
            } else
            {
                response.put(RESULT, BAD_EMAIL);
            }
        } else
        {
            response.put(RESULT, BAD_LOGIN);
        }

        return response.toString();
    }


}
