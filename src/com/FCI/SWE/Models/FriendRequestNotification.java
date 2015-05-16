package com.FCI.SWE.Models;


import java.util.ArrayList;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.*;

import static com.FCI.SWE.Models.OfyService.ofy;

@Entity
public class FriendRequestNotification extends Notification{
	@Index private String receiver;
	@Index private String sender;
	@Id private Long friendRequestNotificationID;
	
	@Ignore static String RECEIVER = "receiver";
	@Ignore static String SENDER = "sender";

	public FriendRequestNotification(){}
	public FriendRequestNotification(String receiver,String sender){
		this.receiver = receiver;
		this.sender = sender;
	}

	public void save(){
		ofy().save().entity(this);
	}

	public static ArrayList<FriendRequestNotification> getListOfNotifications(String receiver){
		ArrayList<FriendRequestNotification> listOfNotification = new ArrayList<FriendRequestNotification>();
		listOfNotification.addAll(ofy().load().type(FriendRequestNotification.class).
    			filter(RECEIVER,receiver).list());
		return listOfNotification;
	}
	public String getSender(){
		return this.sender;
	}
	/*
	 * this method delete FriendRequestNotification
	 */
	public static void delete(String sender,String receiver){
		FriendRequestNotification f = 
		ofy().load().type(FriendRequestNotification.class).
				filter(RECEIVER,receiver).filter(SENDER, sender).
				first().now();
		if(f != null){
			ofy().delete().entity(f);
		}
	}
}
