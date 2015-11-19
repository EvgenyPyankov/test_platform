package com.lilsmile;

import db.DBContorller;
import db.DBControllerMethods;
import db.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
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
    public String logIn(String body) {
        try {

            JSONObject jsonBody = (JSONObject) JSONValue.parse(body);
            StringBuilder logBuilder = new StringBuilder();
            String login = (String) jsonBody.get(LOGIN);
            logBuilder.append("login:"+login+"\n");
            String password = (String) jsonBody.get(PASSWORD);
            JSONObject response = new JSONObject();
            User currentUser = dbController.getUserByEmail(login);
            if (currentUser == null) {
                currentUser = dbController.getUserByLogin(login);
                if (currentUser == null) {
                    response.put(RESULT, WRONG_LOGIN);
                    logBuilder.append("no user");
                    StaticThings.writeInfo(logBuilder.toString());
                    return response.toString();
                }
            }
            String token = generateToken(currentUser.getLogin());
            logBuilder.append("token: "+token);
            StaticThings.addToken(token);
            response.put(TOKEN, token);

            return response.toString();
        } catch (Exception e) {
            logException(e);
            return null;
        }

    }

    private String generateToken(String login) {
        Random random = new Random(System.currentTimeMillis());
        int root = random.nextInt(Integer.MAX_VALUE);
        String salt = DigestUtils.md5Hex(String.valueOf(root));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < login.length(); i++) {
            sb.append(salt.charAt(i));
            sb.append(login.charAt(i));
        }
        String token = sb.toString();
        StaticThings.writeInfo("token: " + token);//log
        return token;
    }


    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(String body) {
        //StaticThings.writeInfo(body);//log
        JSONObject response = new JSONObject();
        JSONObject jsonBody = (JSONObject) JSONValue.parse(body);
        String email = (String) jsonBody.get(EMAIL);
        String login = (String) jsonBody.get(LOGIN);
        String password = (String) jsonBody.get(PASSWORD);
        try {
            if (dbController.getUserByLogin(login) == null) {
                if (dbController.getUserByEmail(email) == null) {
                    dbController.addUser(new User(login, email, password.hashCode()));
                    response.put(RESULT, OK);
                    StaticThings.writeInfo("user: "+login+" successfully created");
                    //response.put(TOKEN, generateToken(login)); //we said that signup should return OK code, not a token
                } else {
                    StaticThings.writeInfo("tryna signup with existing email");
                    response.put(RESULT, BAD_EMAIL);
                }
            } else {
                StaticThings.writeInfo("tryna signup with existing login");
                response.put(RESULT, BAD_LOGIN);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            return SMTH_IS_WRONG;
        }

        return response.toString();
    }

    @POST
    @Path("/anonymous")
    @Consumes(MediaType.APPLICATION_JSON)
    public String anonymousLogIn(String body) {
        JSONObject request = (JSONObject) JSONValue.parse(body);
        String anonymous = (String) request.get(LOGIN);
        JSONObject response = new JSONObject();
        String token = generateToken(anonymous);
        response.put(TOKEN, token);
        StaticThings.addToken(token);
        StaticThings.writeInfo("anonymous login");
        return response.toString();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public String logout(String body)
    {
        JSONObject jsonObject = (JSONObject) JSONValue.parse(body);
        String token = (String) jsonObject.get(TOKEN);
        StaticThings.deleteToken(token);
        StaticThings.writeInfo("logout user "+loginFromToken(token));
        return OK;
    }



    private void logException(Exception e) {
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : elements) {
            sb.append(element.toString() + "\n");
        }
        StaticThings.writeInfo(sb.toString());//log
    }

    private String loginFromToken(String token)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i<token.length(); i+=2)
        {
            sb.append(token.charAt(i));
        }
        return sb.toString();
    }



}
