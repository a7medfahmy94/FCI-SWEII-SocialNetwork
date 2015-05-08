package com.FCI.SWE.Services;

import static com.FCI.SWE.Models.OfyService.ofy;

import java.util.ArrayList;

import javax.validation.constraints.Past;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.Friends;
import com.FCI.SWE.Models.PostsModel;
import com.FCI.SWE.Models.Timeline;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPostModel;

@Path("/post")
public class UserPost extends PostsModel {
	
	static private String status = "Status";
	static private String ok = "OK";
	static private String fail = "Failed";

	@POST
	@Path("/userPost/")
	public String userPost(@FormParam("email") String email,
						   @FormParam("text") String text,
						   @FormParam("feeling") String feeling,
						   @FormParam("privacy")String privacy,
						   @FormParam("timelineId")String timelineId){

		UserEntity u = UserEntity.getUserByEMail(email);
		Timeline timeline = Timeline.getTimelineByID(Long.decode(timelineId));
		JSONObject obj = new JSONObject();
		if(u == null || timeline == null){
			obj.put(status,fail);	
		}else{
			UserPostModel p = new UserPostModel(email,text,timeline.getID());
			if(feeling != null)
				p.setFeeling(feeling);
			if(privacy != null)
				p.setPrivacy(Integer.valueOf(privacy));
			
			p.save();
			obj.put(status, ok);
		}
		return  obj.toString();
		
	}	
    
}

