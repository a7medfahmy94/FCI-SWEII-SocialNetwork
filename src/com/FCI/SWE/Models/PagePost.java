package com.FCI.SWE.Models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.FCI.SWE.Models.OfyService.ofy;

/**
 * 
 * @author Esraa
 *
 */
@Entity
public class PagePost extends PostsModel {
	@Id    private Long id;
	@Index private Long timelineId;

	public PagePost() {}
	
	public PagePost(String t) {
		super(t);
	}
	public PagePost(String t,Long timelineId){
		super(t);
		this.timelineId = timelineId;
	}
	public static PagePost getPost(Long id){
		return ofy().load().type(PagePost.class).id(id).now();
	}
	public void setTimelineId(Long id){
		timelineId = id;
	}
	public Long getId(){
		return id;
	}
	public Long getTimelineId(){
		return timelineId;
	}
	public void save(){		
		ofy().save().entity(this).now();
	}
}
