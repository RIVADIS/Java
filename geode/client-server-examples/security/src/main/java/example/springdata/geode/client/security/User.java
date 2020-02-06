/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.client.security;

import org.apache.geode.security.ResourcePermission;
import org.cp.elements.lang.Identifiable;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class User implements Comparable<User>, Cloneable, Principal, Serializable, Iterable<Role>, Identifiable<String> {

	private String name;
	private List<Role> roles;

	private String credentials = null;

	public User(String name, List<Role> roles) {
		this.name = name;
		this.roles = roles;
	}

	public User(String name) {
		this(name, new ArrayList<Role>());
	}

	@Override
	public void setId(String id) {
		throw new UnsupportedOperationException("Operation Not Supported");
	}

	@Override
	public String getId() {
		return name;
	}

	@Override
	public Iterator<Role> iterator() {
		return roles.iterator();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Object clone() {
		return newUser(name).withCredentials(credentials).withRoles(roles);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int compareTo(User other) {
		return this.getName().compareTo(other.getName());
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof User) {
			if (other == this) {
				return true;
			}
			return name.equals(((User) other).getName());
		}
		return false;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return (17 * 37 + name.hashCode());
	}

	/**
	 * Determines whether this [User] has been granted (assigned) the given [permission][ResourcePermission].
	 *
	 * @param permission [ResourcePermission] to evalute.
	 * @return a boolean value indicating whether this [User] has been granted (assigned)
	 * the given [ResourcePermission].
	 * @see ResourcePermission
	 */
	public boolean hasPermission(ResourcePermission permission) {
		for (Role role : roles) {
			if (role.hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether this [User] has the specified [Role].
	 *
	 * @param role [Role] to evaluate.
	 * @return a boolean value indicating whether this [User] has the specified [Role].
	 * @see Role
	 */
	public boolean hasRole(Role role) {
		return roles.contains(role);
	}


	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Adds the array of [Roles][Role] granting (resource) permissions to this [User].
	 *
	 * @param roles array of [Roles][Role] granting (resource) permissions to this [User].
	 * @return this [User].
	 * @see Role
	 */
	public User withRoles(Role... roles) {
		this.roles.addAll(Arrays.asList(roles));
		return this;
	}

	public User withRoles(Collection<Role> roles) {
		this.roles.addAll(roles);
		return this;
	}

	/**
	 * Sets this [User&#39;s][User] credentials (e.g. password) to the given value.
	 *
	 * @param credentials [String] containing this [User&#39;s][User] credentials (e.g. password).
	 * @return this [User].
	 * @see User
	 */
	public User withCredentials(String credentials) {
		this.credentials = credentials;
		return this;
	}

	public static User newUser(String name) {
		return new User(name);
	}

	public List<Role> getRoles() {
		return roles;
	}

	public String getCredentials() {
		return credentials;
	}
}
