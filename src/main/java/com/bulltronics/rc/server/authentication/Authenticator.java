package com.bulltronics.rc.server.authentication;

import java.util.Date;

public class Authenticator {

    public static boolean isValidToken(String token) {
        Date currentTime = new Date();
        if(token.equals("12345")) {
            return true;
        }

        return false;
    }
}
