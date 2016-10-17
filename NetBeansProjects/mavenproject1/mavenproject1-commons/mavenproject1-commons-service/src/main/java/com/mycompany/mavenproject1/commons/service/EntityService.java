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
package com.mycompany.mavenproject1.commons.service;

import java.util.List;

import com.mycompany.mavenproject1.commons.model.AbstractEntity;

/**
 * Generic interface of all the Services
 * 
 * @author <a href="mailto:luismmribeiro@gmail.com">Luis Ribeiro</a>
 */
public interface EntityService<T extends AbstractEntity> {

	/**
	 * Deletes an entity from the persistence repository
	 * 
	 * @param t Entity to remove
	 */
	public void delete(T t);

	/**
	 * Saves an entity on the persistence repository
	 * 
	 * @param t Entity to save
	 * @return Entity saved 
	 */
	public T save(T t);

	/**
	 * Obtains all the entities of type "T" from the persistence repository
	 * 
	 * @return List of all the entities of type "T"
	 */
	public List<T> findAll();

	/**
	 * Obtains "max" entities from the persistence repository starting from the "offset" 
	 * 
	 * @param start Offset
	 * @param max Number of entities
	 * @return List of "max" entities of type "T"
	 */
	public List<T> findAll(int start, int max);

	/**
	 * Counts all the entities of type "T" on the persistence repository
	 * 
	 * @return Number of entities of type "T"
	 */
	public Long count();

	/**
	 * Obtains the entity given its primary key
	 * 
	 * @param primaryKey Primary key of the desired entity
	 * @return Entity
	 */
	public T findBy(Long primaryKey);
}