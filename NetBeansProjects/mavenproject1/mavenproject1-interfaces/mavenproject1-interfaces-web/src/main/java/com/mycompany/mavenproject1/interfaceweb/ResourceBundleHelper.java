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
package com.mycompany.mavenproject1.interfaceweb;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Class that makes easier the use of Resource Bundles, including the parameterized values
 * 
 * @author <a href="mailto:luismmribeiro@gmail.com">Luis Ribeiro</a>
 */
public class ResourceBundleHelper {
	public static final String RESOURCE_BUNDLE_NAME = "msg";

	public static String getDefaultResourceBundleString(String resourceBundleKey) {
		return getResourceBundleString(RESOURCE_BUNDLE_NAME, resourceBundleKey);
	}

	public static String getDefaultResourceBundleString(String resourceBundleKey, Object ... arguments) {
		return getResourceBundleString(RESOURCE_BUNDLE_NAME, resourceBundleKey, arguments);
	}

	
	public static String getResourceBundleString(String resourceBundleName, String resourceBundleKey, Object ... arguments) {
		String value = getResourceBundleString(resourceBundleName, resourceBundleKey);
		
		if(value == null) {
			return "";
		}
		
		return MessageFormat.format(value, arguments);
	}

	public static String getResourceBundleString(String resourceBundleName, String resourceBundleKey) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, resourceBundleName);
		return bundle.getString(resourceBundleKey);
	}

}
