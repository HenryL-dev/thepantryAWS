package org.liftoff.thepantry;


import org.liftoff.thepantry.controllers.AuthenticationController;
import org.liftoff.thepantry.data.UserRepository;
import org.liftoff.thepantry.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository UserRepository;

    @Autowired
    AuthenticationController AuthenticationController;

    //private static final List<String> whitelist = Arrays.asList("/static","/recipe","recipeList", "/results", "/search", "/about", "/features", "/list/index", "/login", "/register", "/logout", "/css", "/static", "/images", "/fragments");
    //private static final List<String> main = Arrays.asList("/home", "/home/");
    private static final String home = "/";
    private static final String restricted = "/admin";

    private static boolean isWhitelisted(String path) {
                //for (String pathRoot : whitelist) {
        if(path.startsWith(home) && !path.startsWith(restricted)) {
            return true;
        }
        return false;
    }





    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {


        if (isWhitelisted(request.getRequestURI()) ) {
            System.out.println("whitelisted");
            return true;

        }

        HttpSession session = request.getSession();
        User user = AuthenticationController.getUserFromSession(session);

        if (user != null) {
            System.out.println(user);
            return true;
        }
        response.sendRedirect("/login");
        System.out.println("not signed in");
        return false;
    }
}

