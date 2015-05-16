
package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.FriendRequestNotification;
import com.FCI.SWE.Models.MessageNotification;
import com.FCI.SWE.Models.Notification;
import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;

import static com.FCI.SWE.Models.OfyService.ofy;

/**
 * This class contains REST services, also contains action function for web
 * application
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Service {


	private String status = "Status";
	private String ok     = "OK";
	private String failed = "Failed";
	
    /**
     * Registration Rest service, this service will be called to make
     * registration. This function will store user data in data store
     *
     * @param uname provided user name
     * @param email provided user email
     * @param pass provided password
     * @return Status json
     */
    @POST
    @Path("/register")
    public String registrationService(@FormParam("uname") String uname,
            @FormParam("email") String email, @FormParam("password") String pass) {
        UserEntity user = new UserEntity(uname, email, pass);
        user.saveUser();
        JSONObject object = new JSONObject();
        object.put(status, ok);
        return object.toString();
    }

    /**
     * Login Rest Service, this service will be called to make login process
     * also will check user data and returns new user from datastore
     *
     * @param uname provided user name
     * @param pass provided user password
     * @return user in json format
     */
    @POST
    @Path("/login")
    public String loginService(@FormParam("email") String email,
            @FormParam("password") String pass) {
        JSONObject object = new JSONObject();
        UserEntity user = UserEntity.getUserByEMail(email);
        if (user == null || !user.getPass().equals(pass)) {
            object.put(status, failed);

        } else {
            object.put(status, ok);
            object.put("name", user.getName());
            object.put("email", user.getEmail());
            object.put("password", user.getPass());
        }
		return object.toString();
	}
/**
 * send friend request service  
 * @param user_one
 * @param user_two
 * @return ok if users exist failed otherwise
 */
    @POST
    @Path("/sendFriendRequest")
    public String sendFriendRequest(@FormParam("user_one") String user_one,
            @FormParam("user_two") String user_two) {
        JSONObject obj = new JSONObject();
        if (UserEntity.sendFriendRequest(user_one, user_two)) {
        	new FriendRequestNotification(user_two , user_one).save();
            obj.put(status, ok);
        } else {
            obj.put(status, failed);
        }
        return obj.toString();
    }

    /**
     * accept friend request service
     * @param user_one
     * @param user_two
     * @return ok if users exist failed other wise
     */
    @POST
    @Path("/acceptFriendRequest")
    public String acceptFriendRequest(@FormParam("user_one") String user_one,
            @FormParam("user_two") String user_two) {
        JSONObject obj = new JSONObject();
        if (UserEntity.acceptFriendRequest(user_one, user_two)) {
            obj.put(status, ok);
        } else {
            obj.put(status, failed);
        }
        return obj.toString();
    }

    /**
     * get user by email service
     * @param email
     * @return ok if user exist and his name and email , failed otherwise
     */
    @GET
    @Path("/getUserByEMail/{email}")
    public String getUserByEMail(@PathParam("email") String email) {
        JSONObject obj = new JSONObject();
        UserEntity u = UserEntity.getUserByEMail(email);
        if (u == null) {
            obj.put(status, failed);
        } else {
            obj.put(status, ok);
            obj.put("name", u.getName());
            obj.put("email", u.getEmail());
        }
        return obj.toString();
    }

    /**
     * get users by name service
     * @param name
     * @return ok and user name and mail if exists failed otherwise
     */
    @GET
    @Path("/searchUsersByName/{name}")
    public String getUserByName(@PathParam("name") String name) {
        JSONObject obj = new JSONObject();
        UserEntity u = UserEntity.getUserByName(name);
        if (u == null) {
            obj.put(status, failed);
        } else {
            obj.put(status, ok);
            obj.put("name", u.getName());
            obj.put("email", u.getEmail());
        }
        return obj.toString();
    }
    
    
    /**
     * @author Fahmy
     * @Param email
     * @param password
     * @return list of notification
     */
    @GET
    @Path("/notifications/{email}&{password}")
    public String getNotifications(@PathParam("email")String email,
    		@PathParam("password")String password){
    	JSONObject obj = new JSONObject();
    	if(UserEntity.getUser(email, password) == null){
    		obj.put(status, failed);
    	}else{
    		obj.put(status, ok);
    		ArrayList<MessageNotification> m = MessageNotification.getListOfNotifications(email);
    		ArrayList<FriendRequestNotification> f = FriendRequestNotification.getListOfNotifications(email);

    		JSONArray emails = new JSONArray();
//    		String emails[] = new String[m.size()];
    		JSONArray messages = new JSONArray();
//    		String messages[] = new String[m.size()];
    		int i = 0;
    		for(MessageNotification msg: m){
    			emails.put(msg.getSender());
    			messages.put(msg.getText());
    			i++;
    		}
    		i = 0;
    		
    		JSONArray friendRequests = new JSONArray();
//    		String friendRequests[] = new String[f.size()];
    		for(FriendRequestNotification fr: f){
    			friendRequests.put(fr.getSender());
    		}    		
    		obj.put("messages_emails", emails);
    		obj.put("messages_text", messages);
    		obj.put("friend_requests_emails", friendRequests);
    	}
    	return obj.toString();
    }
}
