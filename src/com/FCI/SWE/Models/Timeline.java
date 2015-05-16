package com.FCI.SWE.Models;

import static com.FCI.SWE.Models.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Timeline {
	@Id private Long id;	
	ArrayList<Long> allPosts;
	/*
	 * empty constructor
	 */
	public Timeline(){
		
	}
	
	public static Timeline getTimelineByID(Long id){
		return ofy().load().type(Timeline.class).
				filter("id",id).first().now();	
	}
	
	public void addPost(Long id){
		allPosts.add(id);
	}
	public Long save(){
		ofy().save().entity(this).now();
		return id;
	}
	public Long getID(){
		return id;
	}
	
}