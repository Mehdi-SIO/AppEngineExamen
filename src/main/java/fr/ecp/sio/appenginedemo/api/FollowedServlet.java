package fr.ecp.sio.appenginedemo.api;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by SIO on 17/12/2015.
 */
public class FollowedServlet extends JsonServlet {


    // A GET request should simply return the user
    @Override
    protected String doGet(HttpServletRequest req) throws ServletException, IOException, ApiException {


        String test = "La page des abonnements";

        //I don't get how the url is redirected by urlwrite. This servlet is never called.


        return test;
    }

}
