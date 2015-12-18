package fr.ecp.sio.appenginedemo.api;

import fr.ecp.sio.appenginedemo.data.MessagesRepository;
import fr.ecp.sio.appenginedemo.data.UsersRepository;
import fr.ecp.sio.appenginedemo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * A servlet to handle all the requests on a specific user
 * All requests with path matching "/users/*" where * is the id of the user are handled here.
 */
public class UserServlet extends JsonServlet {

    // A GET request should simply return the user
    @Override
    protected User doGet(HttpServletRequest req) throws ServletException, IOException, ApiException {
        // DoneTODO: Extract the id of the user from the last part of the path of the request

        // TODO: Check if this id is syntactically correct

        long id = 0;

        //Allowing "me" keyword
        if (req.getPathInfo().substring(1).equals("me") )
            { User authenticatedUser = getAuthenticatedUser(req);
                id = authenticatedUser.id;}
        else
            //we get the id using getPathInfo and removing the \ with substring(1)
            //As it's a string, a parsing in Long is required
            id = Long.parseLong( req.getPathInfo().substring(1) );

        // Lookup in repository
        User user = UsersRepository.getUser(id);




        // TODO: Not found?
        // TODO: Add some mechanism to hide private info about a user (email) except if he is the caller
        return user;
    }



    // A POST request could be used by a user to edit its own account
    // It could also handle relationship parameters like "followed=true"
    @Override
    protected User doPost(HttpServletRequest req) throws ServletException, IOException, ApiException {
        // TODO: Get the user as below

        //Get the user authenticated with the token
        User authenticatedUser = getAuthenticatedUser(req);

        //id of the user to follow
       Long  idOfFollowed = Long.parseLong(req.getPathInfo().substring(1));




        if (idOfFollowed != authenticatedUser.id)
        // You cannot follow/unfollow yourself
        {
            Boolean followed = Boolean.parseBoolean(req.getParameter("followed"));

            if (followed == true)
            UsersRepository.setUserFollowed(authenticatedUser.id, idOfFollowed, followed);
        }

        // TODO: Apply some changes on the user (after checking for the connected user)
        // TODO: Handle special parameters like "followed=true" to create or destroy relationships
        // TODO: Return the modified user

        return authenticatedUser;



    }

    // A user can DELETE its own account
    @Override
    protected Void doDelete(HttpServletRequest req) throws ServletException, IOException, ApiException {
        // TODO: Security checks
        // TODO: Delete the user, the messages, the relationships
        // A DELETE request shall not have a response body


        //Deletion of a user by his id
        //Getting id in users/{id}
        long id = Long.parseLong( req.getPathInfo().substring(1) );

        if (id == getAuthenticatedUser(req).id)
        {
            // Lookup in repository and delete the user
            UsersRepository.deleteUser(id);
        }

        //It works ! If you try to delete another id it returns null but you still can get
        //the user. If it's the same id. Then a GET returns null.


        return null;
    }

}