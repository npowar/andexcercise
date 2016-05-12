package com.npowar.andexcercise.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npowar.andexcercise.api.IVenueService;
import com.npowar.andexcercise.beans.Venue;
import com.npowar.andexcercise.beans.VenueGroup;


@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	private IVenueService venueService;
	
	@Autowired
	public void setVenueService(IVenueService venueService) {
		this.venueService = venueService;
	}

	@RequestMapping(value = "/search/{key}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Venue> searchVenue(@PathVariable String key) {
		logger.info("Searching for {}", key);
		try {
			List<Venue> venues = venueService.searchVenue(key);
			return venues;
		} catch (Exception e) {
			logger.error("Failed while searching venues with {}",key, e);			
		}		
		return new ArrayList<Venue>();
	}
	
	@RequestMapping(value = "/explore/{id}", method = RequestMethod.GET,  produces = "application/json")
	@ResponseBody
	public List<VenueGroup> exploreVenue(@PathVariable String id) {
		logger.info("Exploring venue {}", id);
		try {
			List<VenueGroup> venueDetails = venueService.exploreVenue(id);
			return venueDetails;
		}catch(Exception e) {
			logger.error("Failed while getting details for venue {}", id, e);
		}
		return new ArrayList<VenueGroup>();
	}
}
