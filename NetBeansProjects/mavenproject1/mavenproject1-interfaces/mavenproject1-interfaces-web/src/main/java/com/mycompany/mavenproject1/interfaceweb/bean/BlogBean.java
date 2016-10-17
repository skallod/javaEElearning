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
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.mycompany.mavenproject1.commons.model.helloworld.Post;
import com.mycompany.mavenproject1.helloworld.service.PostService;

/**
 * JSF bean that lists the Posts
 * 
 * @author <a href="mailto:luismmribeiro@gmail.com">Luis Ribeiro</a>
 */
@ManagedBean
@RequestScoped
public class BlogBean implements Serializable {
	private static final long serialVersionUID = 7798043747336404301L;
	
	private Long postCount;
	private List<Post> posts;
	
	@Inject
	private PostService postService;
	
	public List<Post> getPosts() {
		if(posts == null) {
			posts = postService.findAll();
		}
		
		return posts;
	}
	
	public Long getPostCount() {
		if(postCount == null) {
			postCount = postService.count();
		}
		
		return postCount;
	}
}
