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
	@Id private static  String email;
    @Index private String name;
    @Index private Long timelineID;
    @Index private static ArrayList<Long>owenedGroups;
    @Index private static ArrayList<Long>joinedGroups;
    
    private String password;
    
    public UserEntity(){
    	
    }
    /**
     * Constructor accepts user data
     *
     * @param name user name
     * @param email user email
     * @param password user provided password
     */
    public UserEntity(String name, String email, String password) {
        this.timelineID = new Timeline().save();
    	this.name = name;
        this.email = email;
        this.password = password;
        this.owenedGroups = new ArrayList();
        this.joinedGroups = new ArrayList();
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
     * this method will return the user's timeline
     * 
     * @return Timeline
     */
    public Timeline getTimeline(){
    	return Timeline.getTimelineByID(timelineID);
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
    public static UserEntity getUser(String email, String pass) {
    	UserEntity e = getUserByEMail(email);
    	if(e.password.equals(pass))return e;
    	return null;        
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
  * this method sends a friend request from user1 to user2
  * @param user1
  * @param user2
  * @return true if send false otherwise
  */
    public static boolean sendFriendRequest(String user1, String user2) {

    	if (getUserByEMail(user1) == null || getUserByEMail(user2) == null) {
            return false;
        }
    	if (Friends.areFriends(user1, user2))return false;
    	if (FriendRequest.find(user1,user2))return false;
    	ofy().save().entity(new FriendRequest(user1,user2)).now();
        return true;
    }
    /**
     * this method accepts a friend request from user1 to user2
     * @param user1
     * @param user2
     * @return true if added to friends false otherwise
     */
    public static boolean acceptFriendRequest(String user1, String user2) {
    	FriendRequest ff = (FriendRequest)ofy().load().type(FriendRequest.class)
    			.filter("user_two",user1).filter("user_one",user2).first().now();
    	
        if (ff == null) {
            return false;
        }
        if(ff != null)
        	ofy().delete().entity(ff);

        FriendRequestNotification.delete(user2,user1);
        
        Friends nf = new Friends(user1,user2);
        ofy().save().entity(nf);

        return true;
    }
    
    public static String getUserEmail(){
    	return UserEntity.email;
    }
    /**
     * @author Rania Sayed
     * 
     * this method allow user to Create New group
     */
    public static boolean createGroup(String groupName,String groupPrivacy){
    	
    	UserEntity.owenedGroups.add(new Group(groupName,groupPrivacy).save());
    	
    	return true;
    	
    }
    
    /**
     * @author Rania Sayed
     * 
     * @param grID
     * 
     * this method allow user to Join Existing group
     */
   public static void joinGroup(Long groupID){
	   
	   UserEntity.joinedGroups.add(groupID);
   }
  

}
