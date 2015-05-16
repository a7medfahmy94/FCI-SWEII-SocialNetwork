package com.FCI.SWE.Models;

import static com.FCI.SWE.Models.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Chat {
	@Id Long id;
	@Index String user_one;
	@Index String user_two;
	List<Messages> messages;
	
	public Chat(){}
	/*
	 * @Constructor
	 */
	public Chat(String user1,String user2){
		user_one = user1;
		user_two = user2;
		messages = new ArrayList<Messages>();
	}
	
	public List<Messages> getMessages(){return messages;}
	
	/*
	 * this method added messages to chat list
	 */
	
	public void addMessage(Messages MessageObj){
		messages.add(MessageObj);
	}
	
	/*
	 * this method save message entity to database
	 */
	public void save(){
		ofy().save().entity(this);
	}
	
	/*
	 * this method retrive chatting between 2 users
	 */
	public static Chat find(String user1,String user2){
		Chat obj = ofy().load().type(Chat.class).
					filter("user_one",user1).filter("user_two",user2).first().now();
		if(obj != null)return obj;
		
		obj = ofy().load().type(Chat.class).
				filter("user_two",user1).filter("user_one",user2).first().now();
		if(obj != null)return obj;
		
		return null;
	}
	
	/*
	 * this method send message from one user to another user
	 */
	public static void send(String user1,String user2,String text){
		Chat obj = find(user1,user2);
		if(obj == null)obj = new Chat(user1,user2);
		obj.addMessage(new Messages(user1,user2,text));
		obj.save();
		new MessageNotification(user2,user1,text).save();
	}
}
