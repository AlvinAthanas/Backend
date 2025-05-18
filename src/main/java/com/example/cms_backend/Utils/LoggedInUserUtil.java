package com.example.cms_backend.Utils;


import com.example.cms_backend.Security.Jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

public class LoggedInUserUtil {


    public static String loggedInUserEmail(HttpServletRequest request){

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.substring(7);
        return JwtUtil.extractUsername(token);
    }


    public static String loggedInUserToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }


}
