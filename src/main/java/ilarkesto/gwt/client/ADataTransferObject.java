/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.gwt.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class is for transporting data from the scrum server to the GWT client.
 */
public abstract class ADataTransferObject implements Serializable, IsSerializable {

	public String entityIdBase;
	public Boolean developmentMode;
	private ArrayList<ErrorWrapper> errors;
	public Integer conversationNumber;

	private String userId;
	private Set<String> deletedEntities;
	private Map<String, Map<String, String>> entities;

	public void clear() {
		entities = null;
		deletedEntities = null;
	}

	public void addError(ErrorWrapper error) {
		if (errors == null) errors = new ArrayList<ErrorWrapper>(1);
		errors.add(error);
	}

	public ArrayList<ErrorWrapper> getErrors() {
		return errors;
	}

	public void setUserId(String user) {
		this.userId = user;
	}

	public String getUserId() {
		return userId;
	}

	public boolean isUserSet() {
		return userId != null;
	}

	public boolean containsDeletedEntity(String id) {
		return deletedEntities != null && deletedEntities.contains(id);
	}

	public final boolean containsEntities() {
		return entities != null && !entities.isEmpty();
	}

	public final boolean containsEntity(String entityId) {
		if (entities == null) return false;
		return entities.containsKey(entityId);
	}

	public final void addEntity(Map<String, String> data) {
		String id = data.get("id");
		if (deletedEntities != null && deletedEntities.contains(id)) return;
		if (entities == null) entities = new HashMap<String, Map<String, String>>();
		entities.put(id, data);
	}

	public final Collection<Map<String, String>> getEntities() {
		if (entities == null) return Collections.emptyList();
		return entities.values();
	}

	public final boolean containsDeletedEntities() {
		return deletedEntities != null && !deletedEntities.isEmpty();
	}

	public final void addDeletedEntity(String entityId) {
		if (deletedEntities == null) deletedEntities = new HashSet<String>();
		deletedEntities.add(entityId);
		if (entities != null) entities.remove(entityId);
	}

	public final Set<String> getDeletedEntities() {
		if (deletedEntities == null) return Collections.emptySet();
		return deletedEntities;
	}

}
