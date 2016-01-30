package rowcord.controllers;

import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 1/30/16.
 */

public class Controller {

    protected int getUserId(HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId = Integer.parseInt(claims.get("userId").toString());
        return userId;
    }

    protected boolean isSuperAdmin(HttpServletRequest request) {
        int userId = getUserId(request);
        return userId==1;
    }
}
