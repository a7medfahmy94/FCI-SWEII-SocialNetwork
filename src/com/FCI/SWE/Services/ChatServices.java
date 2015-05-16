package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.Chat;
import com.FCI.SWE.Models.Friends;



@Path("/chat")
public class ChatServices {
	private String status = "Status";
	private String ok     = "OK";
	private String failed = "Failed";
	
	
	/*
	 * send message 
	 * 
	 * @param sender
	 * @param reciver
	 * @param text
	 * @return
	 */
	@POST
	@Path("/send")
	public String send(@FormParam("sender")String sender,
						@FormParam("receiver")String receiver,
						@FormParam("text")String text){
		JSONObject obj = new JSONObject();
		
		// check 2 users is friend
		if(!Friends.areFriends(sender, receiver)){
			obj.put(status , failed);
		}else{
			Chat.send(sender,receiver,text);
			obj.put(status, ok);
		}
			
		return obj.toString();
	}	

}
