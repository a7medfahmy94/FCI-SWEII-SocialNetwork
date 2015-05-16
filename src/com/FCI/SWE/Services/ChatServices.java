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
	
	@POST
	@Path("/send")
	public String send(@FormParam("sender")String sender,
						@FormParam("receiver")String receiver,
						@FormParam("text")String text){
		JSONObject obj = new JSONObject();
		//if not friends put status failed
		if(!Friends.areFriends(sender, receiver)){
			obj.put(status , failed);
		}
		//if friends send the message and put status ok 
		else{
			Chat.send(sender,receiver,text);
			obj.put(status, ok);
		}
			
		return obj.toString();
	}	

}