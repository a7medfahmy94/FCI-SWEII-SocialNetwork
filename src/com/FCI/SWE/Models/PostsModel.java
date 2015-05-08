package com.FCI.SWE.Models;

/**
 * This class is the abstract Post
 * it only has the text of the post and leaves
 * other enhancements to the extension classes
 */
public abstract class PostsModel {

	protected String text;
	
	/**
	 * empty constructor
	 */
	public PostsModel() {
	}

	/**
	 * constructor takes text for a post
	 * 
	 * @param t
	 */
	public PostsModel(String t) {

		text = t;
	}
}