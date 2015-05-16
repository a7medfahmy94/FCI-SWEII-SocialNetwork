package com.FCI.SWE.Services;

import com.FCI.SWE.Models.PagePost;
import com.FCI.SWE.Models.Timeline;
import com.FCI.SWE.Models.Page;
import com.FCI.SWE.Models.UserEntity;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

/**
 * This class is responsible for the services of pages
 * its relative url is /rest/pages/
 * 
 * @author Esraa
 *
 */
@Path("/pages")
public class PageServices {
	private String status = "Status";
	private String ok = "OK";
	private String failed = "Failed";

	/**
	 * 
	 * @param email
	 * @param pName
	 * @param pCateogry
	 * @param pType
	 * @return
	 */
	@POST
	@Path("/createPage/")
	public String createPage(@FormParam("email") String email,
			@FormParam("page_name") String pName,
			@FormParam("page_category") String pCateogry,
			@FormParam("page_type") String pType) {

		JSONObject obj = new JSONObject();

		UserEntity u = UserEntity.getUserByEMail(email);
		//if user doesn't exist put status failed
		if (u == null) {
			obj.put(status, failed);
		}
		// if exists create a new page and save it to the DB and put status ok 
		else {
			new Page(pName, pType, pCateogry, email).save();
			obj.put(status, ok);
		}
		return obj.toString();
	}

	/**
	 * 
	 * @param email
	 * @param pId
	 * @return
	 */
	@POST
	@Path("/likePage")
	public String likePage(@FormParam("email") String email,
						   @FormParam("page_id") Long pId) {
		JSONObject obj = new JSONObject();

		UserEntity u = UserEntity.getUserByEMail(email);
		Page p = Page.SearchPageByID(pId);
		// if user don't exist or page don't exist put status failed
		if (u == null || p == null) {
			obj.put(status, failed);
		}
		//if both exists add fan to the page and save the page to DB and put status Ok
		else {
			obj.put(status, ok);
			p.addFan(email);
			p.save();
		}
		return obj.toString();
	}

	@POST
	@Path("/writePost/")
	public String writePost(@FormParam("email") String email,
							@FormParam("page_id") Long pId,
							@FormParam("post") String post) {
		JSONObject obj = new JSONObject();
		
		Page p = Page.SearchPageByID(pId);
		UserEntity u = p.getOwner();
		//if page doesn't exists or user don't exist or the user is not the owner put status Failed
		if (p == null || u == null || false==u.getEmail().equals(email)) {
			obj.put(status, failed);
		}
		// else get page timeline 
		// make an new page post for this page and save it 
		// add the post to the timeline and save it 
		// put status ok
		else {
			Timeline t = p.getPageTimeline();
			PagePost pageP = new PagePost(post,t.getID());
			pageP.save();
			t.addPost(pageP.getId());
			t.save();
			obj.put(status, ok);
		}
		return obj.toString();
	}

}
