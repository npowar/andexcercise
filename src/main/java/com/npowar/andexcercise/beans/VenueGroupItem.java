package com.npowar.andexcercise.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VenueGroupItem {
	private Venue venue;

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VenueGroupItem [venue=");
		builder.append(venue);
		builder.append("]");
		return builder.toString();
	}
	
	
}
