package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.osgi.framework.hooks.service.FindHook;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.*;

import static com.FCI.SWE.Models.OfyService.ofy;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */

@Entity
public class UserEntity {
	@Id private String email;
    @Index private String name;
    private String password;

    
    public UserEntity(){}
    /**
     * Constructor accepts user data
     *
     * @param name user name
     * @param email user email
     * @param password user provided password
     */
    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    
    /**
     * this method gets user name
     * @return name
     */
    public String getName() {   
    return name;
    }
    /**
     * this method gets user email
     * @return email
     */
    public String getEmail() {
        return email;
    }
/**
 * this method gets user password
 * @return password
 */
    
    public String getPass() {
        return password;
    }

    /**
     *
     * This static method will form UserEntity class using json format contains
     * user data
     *
     * @param json String in json format contains user data
     * @return Constructed user entity
     */
    public static UserEntity getUser(String json) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(json);
            return new UserEntity(object.get("name").toString(), object.get(
                    "email").toString(), object.get("password").toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * This static method will form UserEntity class using user name and
     * password This method will serach for user in datastore
     *
     * @param name user name
     * @param pass user password
     * @return Constructed user entity
     */
    public static UserEntity getUser(String name, String pass) {
    	return ofy().load().type(UserEntity.class).
        			filter("name",name).filter("password",pass).first().now();
        

    }

    /**
     * this method gets user by mail
     * @param email
     * @return user
     */
    public static UserEntity getUserByEMail(String email) {
        return ofy().load().type(UserEntity.class).
    			id(email).now();
    }
    /**
     * this method gets user by name
     * @param name
     * @return user
     */
    public static UserEntity getUserByName(String name) {
        return ofy().load().type(UserEntity.class).
    			filter("name",name).first().now();

    }

    /**
     * This method will be used to save user object in datastore
     *
     * @return boolean if user is saved correctly or not
     */
    public Boolean saveUser() {
    	ofy().save().entity(this);
    	return true;
    }
 /**
  * this method sends a friend request from u1 to u2
  * @param u1
  * @param u2
  * @return true if send false otherwise
  */
    public static boolean sendFriendRequest(String u1, String u2) {

    	if (getUserByEMail(u1) == null || getUserByEMail(u2) == null) {
            return false;
        }
    	ofy().save().entity(new FriendRequest(u1,u2)).now();
        return true;
    }
    /**
     * this method accepts a friend request from u1 to u2
     * @param u1
     * @param u2
     * @return true if added to friends false otherwise
     */
    public static boolean acceptFriendRequest(String u1, String u2) {
    	FriendRequest f = (FriendRequest)ofy().load().type(FriendRequest.class)
    			.filter("user_one",u1).filter("user_two",u2).first().now();
    	FriendRequest ff = (FriendRequest)ofy().load().type(FriendRequest.class)
    			.filter("user_two",u1).filter("user_one",u2).first().now();
    	
        if (f == null && ff == null) {
            return false;
        }
        if(f != null)
        	ofy().delete().entity(f);
        if(ff != null)
        	ofy().delete().entity(ff);

        Friends nf = new Friends(u1,u2);
        ofy().save().entity(nf);

        return true;
    }

}
