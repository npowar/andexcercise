package com.npowar.andexcercise.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VenueGroup {
	private String name;
	private String type;
	private List<VenueGroupItem> items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<VenueGroupItem> getItems() {
		return items;
	}

	public void setItems(List<VenueGroupItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VenueGroup [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", items=");
		builder.append(items);
		builder.append("]");
		return builder.toString();
	}
	
	
}
