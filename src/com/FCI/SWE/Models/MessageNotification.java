package com.FCI.SWE.Models;

import java.util.ArrayList;

import org.apache.tools.ant.taskdefs.condition.Not;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.*;

import static com.FCI.SWE.Models.OfyService.ofy;

@Entity
public class MessageNotification extends Notification{
	@Id Long id;
	@Index private String user_email;
	private String sender_email;
	private String text;
	private Boolean isRead;
	
	public MessageNotification(){}
	
	public MessageNotification(String receiver,String sender,String text){
		this.user_email = receiver;
		this.sender_email = sender;
		this.text = text;
		this.isRead = false;
	}

	public void save(){
		ofy().save().entity(this);
	}
	
	
	public static ArrayList<MessageNotification> getListOfNotifications(String email){
		ArrayList<MessageNotification> ret = new ArrayList<MessageNotification>();
    	ret.addAll(ofy().load().type(MessageNotification.class).
    			filter("user_email",email).list());
    	for(MessageNotification m: ret){
    		m.isRead = true;
    		m.save();
    	}
		return ret;
	}
	public String getSender(){
		return this.sender_email;
	}
	public String getText(){
		return this.text;
	}
	
}
