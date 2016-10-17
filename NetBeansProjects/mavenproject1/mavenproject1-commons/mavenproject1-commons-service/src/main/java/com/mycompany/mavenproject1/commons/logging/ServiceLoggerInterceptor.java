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
package com.mycompany.mavenproject1.commons.logging;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interceptor class that loggs the calls to CDI bean's methods. It logs the class name, the method name, the parameters
 * and also the returned object.
 * 
 * @author <a href="mailto:luismmribeiro@gmail.com">Luis Ribeiro</a>
 */
@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ServiceLoggerInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLoggerInterceptor.class);

	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		LOGGER.debug("Entering method: " + invocationContext.getTarget().getClass().getName() + "." + invocationContext.getMethod().getName());
		Object[] parameterObjects = invocationContext.getParameters();
		for (int i = 0; i < parameterObjects.length; i++) {
			LOGGER.debug("Parameter " + i + " : " + parameterObjects[i].toString());
		}
		
		Object obj = invocationContext.proceed();
		
		LOGGER.debug("Leaving method: " + invocationContext.getTarget().getClass().getName() + "." + invocationContext.getMethod().getName());
		LOGGER.debug("Returned object: " + obj);
		
		return obj;
	}
}
