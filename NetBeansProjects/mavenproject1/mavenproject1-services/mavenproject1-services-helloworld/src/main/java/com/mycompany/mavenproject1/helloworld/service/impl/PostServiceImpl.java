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
package com.mycompany.mavenproject1.helloworld.service.impl;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import com.mycompany.mavenproject1.commons.logging.Logged;
import com.mycompany.mavenproject1.commons.model.helloworld.Post;
import com.mycompany.mavenproject1.commons.service.impl.AbstractEntityService;
import com.mycompany.mavenproject1.helloworld.repository.PostRepository;
import com.mycompany.mavenproject1.helloworld.service.PostService;

/**
 * Post Service
 * 
 * @author <a href="mailto:luismmribeiro@gmail.com">Luis Ribeiro</a>
 */
@Logged
@Transactional
public class PostServiceImpl extends AbstractEntityService<Post, PostRepository> implements PostService {

}
