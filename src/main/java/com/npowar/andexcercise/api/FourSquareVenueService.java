package com.npowar.andexcercise.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.npowar.andexcercise.beans.Location;
import com.npowar.andexcercise.beans.Venue;
import com.npowar.andexcercise.beans.VenueGroup;
import com.npowar.andexcercise.beans.VenueGroupItem;

@Service("venueService")
public class FourSquareVenueService implements IVenueService {

	private static final Logger logger = LoggerFactory.getLogger(FourSquareVenueService.class);
	
	private String url=null;//"https://api.foursquare.com/v2/venues/";
	private String clientId=null;
	private String clientSecret = null;
	private static final String END_POINT_SEARCH = "/search?";
	private static final String END_POINT_EXPLORE = "/explore?";
	private static final String version = "20160511";
	
	private RestOperations restTemplate;
	
	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate();					
	}
	
	public void setRestTemplate(RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Value("${api.foursquare.url}")
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Value("#{systemProperties['clientId']}")
	public void setClientId(String clientId) {
		logger.info("Setting clientId:{}", clientId);
		this.clientId = clientId;
	}
	
	@Value("#{systemProperties['clientSecret']}")
	public void setClientSecret(String clientSecret) {
		logger.info("Setting clientSecret:{}", clientSecret);
		this.clientSecret = clientSecret;
	}

	@Override
	public List<Venue> searchVenue(String name) throws Exception {
		String finalUrl = url + END_POINT_SEARCH + "near="+name+"&client_id="+clientId+"&client_secret="+clientSecret+"&v="+version; 
		ResponseEntity<ResponseWrapper> response = restTemplate.getForEntity(finalUrl, ResponseWrapper.class);
		ResponseWrapper wrapper = response.getBody();
		//transform
		List<Venue> venues = transformToVenues(wrapper);
		return venues;
	}

	@Override
	public List<VenueGroup> exploreVenue(String place)
			throws Exception {		
		String finalUrl = url + END_POINT_EXPLORE + "near="+place+"&client_id="+clientId+"&client_secret="+clientSecret+"&v="+version; 
		ResponseEntity<ResponseWrapper> response = restTemplate.getForEntity(finalUrl, ResponseWrapper.class);
		ResponseWrapper wrapper = response.getBody();
		List<VenueGroup> venueDetails = transformToVenueGroups(wrapper);		
		return venueDetails;
	}

	List<VenueGroup> transformToVenueGroups(ResponseWrapper wrapper) {
		List<VenueGroup> venueGroups = new ArrayList<VenueGroup>();
		if (wrapper != null
			&& wrapper.response!=null
			&& wrapper.response.groups!=null ){
			
			for (FSGroup fsGroup : wrapper.response.groups) {
				VenueGroup venueGroup = new VenueGroup();
				venueGroup.setName(fsGroup.name);
				venueGroup.setType(fsGroup.type);
				
				if (fsGroup.items!=null) {
					List<VenueGroupItem> venueGroupItems = new ArrayList<>();
					venueGroup.setItems(venueGroupItems);
					for (FSItem fsItem : fsGroup.items) {
						if (fsItem.venue!=null) {
							VenueGroupItem venueGroupItem = new VenueGroupItem();
							venueGroupItem.setVenue(transformVenue(fsItem.venue));
							venueGroupItems.add(venueGroupItem);
						}
					}					
				}				
				venueGroups.add(venueGroup);
			}
		}		
		
		return venueGroups;
	}

	List<Venue> transformToVenues(ResponseWrapper wrapper) {
		List<Venue> venues = new ArrayList<>();
		if (wrapper !=null 
				&& wrapper.response!=null
				&& wrapper.response.venues!=null) {
			for(FSVenue fsVenue:wrapper.response.venues) {
				if (fsVenue!=null) {
					venues.add(transformVenue(fsVenue));
				}				
			}
		}		
		return venues;
	}

	Venue transformVenue(FSVenue fsVenue) {
		Venue venue = new Venue();
		venue.setId(fsVenue.id);
		venue.setName(fsVenue.name);
		if (fsVenue.location!=null) {
			venue.setLocation(transformLocation(fsVenue.location));
		}
		if (fsVenue.categories!=null) {
			venue.setCategoryName(transformVenueCategories(fsVenue.categories));
		}
		return venue;
	}

	List<String> transformVenueCategories(List<FSCategory> fsCategories) {
		List<String> categories = new ArrayList<>();
		for (FSCategory fsCategory : fsCategories) {
			categories.add(fsCategory.name);
		}
		return categories;
	}

	Location transformLocation(FSLocation fsLocation) {
		Location location = new Location();
		location.setAddress(fsLocation.address);
		location.setCity(fsLocation.city);
		location.setCountry(fsLocation.country);
		location.setState(fsLocation.state);
		return location;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)	
	static class ResponseWrapper {
		@JsonProperty("response")
		Response response;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Response {
		@JsonProperty("venues")
		List<FSVenue> venues;
		
		@JsonProperty("groups")
		List<FSGroup> groups;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class FSVenue {
		@JsonProperty("id")
		String id;
		@JsonProperty("name")
		String name;
		@JsonProperty("location")
		FSLocation location;
		@JsonProperty("categories")
		List<FSCategory> categories;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class FSLocation {
		@JsonProperty("address")
		String address;
		@JsonProperty("city")
		String city;
		@JsonProperty("state")
		String state;
		@JsonProperty("country")
		String country;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class FSCategory {
		@JsonProperty("id")
		String id;
		@JsonProperty("name")
		String name;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class FSGroup {
		@JsonProperty("type")
		String type;
		@JsonProperty("name")
		String name;
		@JsonProperty("items")
		List<FSItem> items;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class FSItem {
		@JsonProperty("venue")
		FSVenue venue;
	}
}
