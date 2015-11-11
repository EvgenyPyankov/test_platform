package com.lilsmile;

import bd.DBContorller;
import bd.DBControllerMethods;
import bd.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Smile on 08.11.15.
 */
@Path("/auth")
public class AuthorizeAndRegister implements Constants {

    DBControllerMethods dbController = new DBContorller();
    private static Logger log;
    static {
        try {
            log =  Logger.getLogger(AuthorizeAndRegister.class.getName());
            log.addHandler(new FileHandler("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String logIn(String body)
    {
        try{
        log.info(body);
        JSONObject jsonBody = (JSONObject) JSONValue.parse(body);
        String login = (String) jsonBody.get(LOGIN);
        String password = (String) jsonBody.get(PASSWORD);
        JSONObject response = new JSONObject();
        User currentUser = dbController.getUserByEmail(login);
        if (currentUser.equals(null))
        {
            currentUser = dbController.getUserByLogin(login);
            if (currentUser.equals(null))
            {
                response.put(RESULT, WRONG_LOGIN);
                return response.toString();
            }
        }
        response.put(TOKEN, generateToken(currentUser.getLogin()));

        return response.toString();
        }catch (Exception e)
        {
            logException(e);
            return null;
        }

    }

    private String generateToken(String login)
    {
        Random random = new Random(System.currentTimeMillis());
        int root = random.nextInt(Integer.MAX_VALUE);
        String salt = DigestUtils.md5Hex(String.valueOf(root));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<login.length(); i++)
        {
            sb.append(salt.charAt(i));
            sb.append(login.charAt(i));
        }
        String token = sb.toString();
        log.info("token: "+token);
        return token;
    }


    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(String body)
    {
        log.info(body);
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
                response.put(TOKEN, generateToken(login));
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
    
    @POST
    @Path("/anonymous")
    @Consumes(MediaType.APPLICATION_JSON)
    public String anonymousLogIn(String body)
    {
        JSONObject request = (JSONObject) JSONValue.parse(body);
        String anonymous = (String) request.get(LOGIN);
        JSONObject response = new JSONObject();
        response.put(TOKEN, generateToken(anonymous));
        return response.toString();
    }
    
    private void logException(Exception e)
    {
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : elements)
        {
            sb.append(element.toString()+"\n");
        }
        log.warning(sb.toString());
    }


}
