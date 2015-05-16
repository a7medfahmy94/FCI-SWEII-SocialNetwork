package com.FCI.SWE.Models;

import static com.FCI.SWE.Models.OfyService.ofy;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * This class is responsible for representing the Page model
 * 
 * @author Esraa
 *
 */
@Entity
public class Page {
	@Id
	private Long id;
	@Index
	private String name;
	@Index
	private String type;
	@Index
	private String category;
	@Index
	private String ownerEmail;
	@Index
	private long timelineID;
	@Index
	private int numberOfLikes;
	private ArrayList<String> fansEMails;

	/*
	 * empty constructor
	 */

	public Page() {
		numberOfLikes = 0;
		fansEMails = new ArrayList<String>();
	}
	/*
	 * constructor
	 */
	public Page(String pageName, String pageType, String pageCategory,
			String ownerEmail) {
		name = pageName;
		type = pageType;
		category = pageCategory;
		this.ownerEmail = ownerEmail;
		numberOfLikes = 0;
		fansEMails = new ArrayList<>();
		timelineID = new Timeline().save();
	}

	public static Page SearchPageByID(Long pId) {
		return ofy().load().type(Page.class).id(pId).now();
	}

	public static Page SearchPageByName(String name) {
		return ofy().load().type(Page.class).filter("name", name).first().now();
	}

	public Long save() {
		ofy().save().entity(this);
		return id;
	}

	public Long getTimelineId() {
		return timelineID;
	}

	public Timeline getPageTimeline() {
		return Timeline.getTimelineByID(timelineID);
	}

	public UserEntity getOwner() {
		return UserEntity.getUserByEMail(ownerEmail);
	}

	public void addFan(String fanEMail) {
		numberOfLikes++;
		fansEMails.add(fanEMail);
	}

}
