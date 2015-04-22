package com.FCI.SWE.Models;
import static com.FCI.SWE.Models.OfyService.ofy;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
/*
 * @author Rania Sayed
 */
public class Group {

	@Id private Long id;
	//private ArrayList<userPost> grPosts = new ArrayList();
	
	public void save(){
		ofy().save().entity(this).now();
	}
	public Long getId(){
		
		return id;
	}
	static public Group getGroupByID(Long id){
		return ofy().load().type(Group.class).
				filter("id",id).first().now();	
	}
	
	
	
}
