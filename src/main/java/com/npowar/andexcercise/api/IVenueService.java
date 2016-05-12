package com.npowar.andexcercise.api;

import java.util.List;

import com.npowar.andexcercise.beans.Venue;
import com.npowar.andexcercise.beans.VenueGroup;

public interface IVenueService {
	List<Venue> searchVenue(String name) throws Exception;
	List<VenueGroup> exploreVenue(String place) throws Exception;
}
