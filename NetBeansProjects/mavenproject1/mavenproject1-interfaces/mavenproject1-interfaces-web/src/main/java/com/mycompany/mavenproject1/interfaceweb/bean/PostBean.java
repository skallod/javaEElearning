/*
 * JEE 7 Archetype, base project for a 3 tiers JEE7 maven application
 *
 * Copyright (c) 2016, Luís Ribeiro or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Luís Ribeiro.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, please send an email to:
 * luismmribeiro@gmail.com
 */
package com.mycompany.mavenproject1.interfaceweb.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.mycompany.mavenproject1.commons.model.helloworld.Post;
import com.mycompany.mavenproject1.helloworld.service.PostService;
import com.mycompany.mavenproject1.interfaceweb.ResourceBundleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSF bean that presents the details of a Post
 * 
 * @author <a href="mailto:luismmribeiro@gmail.com">Luis Ribeiro</a>
 */
@ManagedBean
@RequestScoped
public class PostBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostBean.class);
	private static final String DATE_FORMAT_PATTERN = "yyyy-MMM-dd HH:mm:ss";
	private static final String DATE_AND_AUTHOR_HEADER_KEY = "post_date_and_author_header";

	@Inject
	private PostService postService;

	private int postId;
	private Post post = new Post();

	private boolean edit = false;

	public String save() {
		LOGGER.debug("Saving a post");
		post = postService.save(post);

		return "blog";
	}
	
	public void loadPost() {
		post = postService.findBy(Long.valueOf(postId));
	}

	public String getPostDescription() {
		return ResourceBundleHelper.getDefaultResourceBundleString(DATE_AND_AUTHOR_HEADER_KEY, 
				new SimpleDateFormat(DATE_FORMAT_PATTERN).format(post.getPostDateTime()),
				post.getAuthor());
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
}
