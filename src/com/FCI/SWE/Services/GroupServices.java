package com.FCI.SWE.Services;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.Chat;
import com.FCI.SWE.Models.Friends;
import com.FCI.SWE.Models.Group;
import com.FCI.SWE.Models.UserEntity;
import com.google.api.server.spi.auth.common.User;

@Path("/group")
public class GroupServices {

	private String status = "Status";
	private String ok     = "OK";
	
	@POST
	@Path("/create")
	public String create(@FormParam("groupName")String groupName,@FormParam("groupPrivacy")String groupPrivacy){
		JSONObject obj = new JSONObject();
		
		UserEntity.createGroup(groupName,groupPrivacy);
		obj.put(status, ok);
		
			
		return obj.toString();
	}	
	@POST
	@Path("/join")
	public String join(@FormParam("groupID")Long groupID){
		JSONObject obj = new JSONObject();
		
	    UserEntity.joinGroup(groupID);
		obj.put(status, ok);
		
		return obj.toString();
	}
	
	
}
