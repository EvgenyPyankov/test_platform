package com.lilsmile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Smile on 14.11.15.
 */
public class StaticThings {

    private static Logger logger;
    private static ArrayList<String> tokens;

    static {
        tokens = new ArrayList<String>();

        logger = Logger.getLogger("TestPlatform");
        try {
            logger.addHandler(new FileHandler("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean addToken(String token)
    {
       if (!checkToken(token))
       {
           tokens.add(token);
           return true;
       }
        return false;
    }

    public static boolean checkToken(String token) //true - contains, false - not
    {
        return tokens.contains(token);
    }

    public static boolean deleteToken(String token)
    {
        if (checkToken(token))
        {
            tokens.remove(token);
            return true;
        }
        return false;
    }


    public static void writeInfo(String message)
    {
        logger.info(message);
    }

    public static String loginFromToken(String token)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i<token.length(); i+=2)
        {
            sb.append(token.charAt(i));
        }
        return sb.toString();
    }

}
