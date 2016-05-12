package com.npowar.andexcercise.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import com.npowar.andexcercise.beans.Venue;
import com.npowar.andexcercise.beans.VenueGroup;
import com.npowar.andexcercise.beans.VenueGroupItem;
import com.npowar.andexcercise.utils.JsonUtils;

public class FourSquareVenueServiceTest {

	private FourSquareVenueService fourSquareVenueService;
	private RestOperations restTemplate;
	private String clientId,clientSecret,url;
	
	@Before
	public void setUp() throws Exception {
		fourSquareVenueService = new FourSquareVenueService();		
		restTemplate = Mockito.mock(RestOperations.class);
		fourSquareVenueService.setRestTemplate(restTemplate);
		
		url ="http://dummy.api.foursquare.com";
		clientId = "dummyClientId";
		clientSecret = "dummyClientSecret";
		
		fourSquareVenueService.setUrl(url);
		fourSquareVenueService.setClientId(clientId);
		fourSquareVenueService.setClientSecret(clientSecret);
	}
	
	private void init() {
		fourSquareVenueService.setUrl("https://api.foursquare.com/v2/venues/");
		fourSquareVenueService.setClientId("UCF4YACOXP0X5JAB0GI4PZQY3XZJR3E5FAJ5J4EQZFCR2BT4");
		fourSquareVenueService.setClientSecret("EUTN04ARKLPNH02AHAD5AOYRNVOICZR2ECUJ4VJNT2RWJQWR");	
		fourSquareVenueService.init();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Ignored since these are integration tests
	 * @throws Exception
	 */
	@Ignore	
	@Test
	public void testSearchIntegration() throws Exception {
		init();
		List<Venue>  venues = fourSquareVenueService.searchVenue("Ilford");
		assertNotNull(venues);
		assertTrue(venues.size()>0);		
	}

	/**
	 * Ignored since these are integration tests.
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testExploreIntegration() throws Exception {
		init();
		List<VenueGroup> venueGroups = fourSquareVenueService.exploreVenue("Ilford");
		assertNotNull(venueGroups);
		assertTrue(venueGroups.size()>0);
	}
	
	
	@Test	
	public void testSearch() throws Exception {	
		String name = "Ilford";
		String expUrl = "http://dummy.api.foursquare.com/search?near=Ilford&client_id=dummyClientId&client_secret=dummyClientSecret&v=20160511";
		String expJsonReply = "{\"meta\":{\"code\":200,\"requestId\":\"5733d210498eaaf8e1f0a773\"},\"response\":{\"venues\":[{\"id\":\"4f8c667ae4b005c393ed2a47\",\"name\":\"Nuffield Health Club\",\"contact\":{},\"location\":{\"address\":\"The Exchange Mall\",\"lat\":51.55783367681627,\"lng\":0.07291455777153256,\"cc\":\"GB\",\"city\":\"Ilford\",\"state\":\"Greater London\",\"country\":\"United Kingdom\",\"formattedAddress\":[\"The Exchange Mall\",\"Ilford\",\"Greater London\",\"United Kingdom\"]},\"categories\":[{\"id\":\"4bf58dd8d48988d175941735\",\"name\":\"Gym \\/ Fitness Center\",\"pluralName\":\"Gyms or Fitness Centers\",\"shortName\":\"Gym \\/ Fitness\",\"icon\":{\"prefix\":\"https:\\/\\/ss3.4sqi.net\\/img\\/categories_v2\\/building\\/gym_\",\"suffix\":\".png\"},\"primary\":true}],\"verified\":false,\"stats\":{\"checkinsCount\":109,\"usersCount\":17,\"tipCount\":1},\"allowMenuUrlEdit\":true,\"specials\":{\"count\":0,\"items\":[]},\"hereNow\":{\"count\":0,\"summary\":\"Nobody here\",\"groups\":[]},\"referralId\":\"v-1463013904\",\"venueChains\":[]},{\"id\":\"4b5b3039f964a520c4e928e3\",\"name\":\"Cineworld\",\"contact\":{\"phone\":\"+448712002000\",\"formattedPhone\":\"+44 871 200 2000\"},\"location\":{\"address\":\"The Exchange Mall\",\"crossStreet\":\"Clements Rd\",\"lat\":51.5576,\"lng\":0.0741,\"postalCode\":\"IG1 1BP\",\"cc\":\"GB\",\"city\":\"Ilford\",\"state\":\"Greater London\",\"country\":\"United Kingdom\",\"formattedAddress\":[\"The Exchange Mall (Clements Rd)\",\"Ilford\",\"Greater London\",\"IG1 1BP\",\"United Kingdom\"]},\"categories\":[{\"id\":\"4bf58dd8d48988d17f941735\",\"name\":\"Movie Theater\",\"pluralName\":\"Movie Theaters\",\"shortName\":\"Movie Theater\",\"icon\":{\"prefix\":\"https://ss3.4sqi.net/img\\/categories_v2\\/arts_entertainment\\/movietheater_\",\"suffix\":\".png\"},\"primary\":true}],\"verified\":true,\"stats\":{\"checkinsCount\":1774,\"usersCount\":444,\"tipCount\":10},\"url\":\"http:\\/\\/www.cineworld.com\",\"specials\":{\"count\":0,\"items\":[]},\"events\":{\"count\":14,\"summary\":\"14 movies\"},\"storeId\":\"\",\"hereNow\":{\"count\":0,\"summary\":\"Nobody here\",\"groups\":[]},\"referralId\":\"v-1463013904\",\"venueChains\":[{\"id\":\"556a2bf9a7c8957d73d3cf3a\"}]}],\"confident\":false,\"geocode\":{\"what\":\"\",\"where\":\"ilford\",\"feature\":{\"cc\":\"GB\",\"name\":\"Ilford\",\"displayName\":\"Ilford, Greater London, United Kingdom\",\"matchedName\":\"Ilford, Greater London, United Kingdom\",\"highlightedName\":\"<b>Ilford<\\/b>, Greater London, United Kingdom\",\"woeType\":7,\"id\":\"geonameid:2646277\",\"longId\":\"72057594040574213\",\"geometry\":{\"center\":{\"lat\":51.55765,\"lng\":0.07278},\"bounds\":{\"ne\":{\"lat\":51.5643730164,\"lng\":0.0848509967327},\"sw\":{\"lat\":51.5521392822,\"lng\":0.0606600008905}}}},\"parents\":[]}}}";
		
		FourSquareVenueService.ResponseWrapper wrapper = JsonUtils.convrtToObject(expJsonReply, FourSquareVenueService.ResponseWrapper.class);
		ResponseEntity<FourSquareVenueService.ResponseWrapper> responseEntity = new ResponseEntity<FourSquareVenueService.ResponseWrapper>(wrapper, HttpStatus.ACCEPTED);
		Mockito.when(restTemplate.getForEntity(Mockito.eq(expUrl), Mockito.eq(FourSquareVenueService.ResponseWrapper.class))).thenReturn(responseEntity);
		
		List<Venue> venues = fourSquareVenueService.searchVenue(name);
		Mockito.verify(restTemplate).getForEntity(Mockito.eq(expUrl), Mockito.eq(FourSquareVenueService.ResponseWrapper.class));
		
		//verify transformation
		assertNotNull(venues);
		assertTrue(venues.size()==2);
		//check a venue at 0 index
		Venue actualVenue = venues.get(0);
		
		assertEquals("Name", "Nuffield Health Club", actualVenue.getName());
		assertEquals("Id", "4f8c667ae4b005c393ed2a47" , actualVenue.getId());
		assertEquals("Location.City", "Ilford", actualVenue.getLocation().getCity());
		assertEquals("CategoryName", "Gym / Fitness Center", actualVenue.getCategoryName().get(0));		
	}
	
	@Test	
	public void testExplore() throws Exception {	
		String name = "Ilford";
		String expUrl = "http://dummy.api.foursquare.com/explore?near=Ilford&client_id=dummyClientId&client_secret=dummyClientSecret&v=20160511";
		String expJsonReply = "{\"meta\":{\"code\":200,\"requestId\":\"5733d4a6498e30d32e6734f2\"},\"response\":{\"geocode\":{\"what\":\"\",\"where\":\"ilford\",\"center\":{\"lat\":51.55765,\"lng\":0.07278},\"displayString\":\"Ilford, Greater London, United Kingdom\",\"cc\":\"GB\",\"geometry\":{\"bounds\":{\"ne\":{\"lat\":51.5643730164,\"lng\":0.0848509967327},\"sw\":{\"lat\":51.5521392822,\"lng\":0.0606600008905}}},\"longId\":\"72057594040574213\"},\"warning\":{\"text\":\"There aren't a lot of results near you. Try something more general, reset your filters, or expand the search area.\"},\"headerLocation\":\"Ilford\",\"headerFullLocation\":\"Ilford\",\"headerLocationGranularity\":\"city\",\"totalResults\":50,\"suggestedBounds\":{\"ne\":{\"lat\":51.558874502541094,\"lng\":0.07740756443169844},\"sw\":{\"lat\":51.556174842755645,\"lng\":0.06759625917255105}},\"groups\":[{\"type\":\"Recommended Places\",\"name\":\"recommended\",\"items\":[{\"reasons\":{\"count\":0,\"items\":[{\"summary\":\"This spot is popular\",\"type\":\"general\",\"reasonName\":\"globalInteractionReason\"}]},\"venue\":{\"id\":\"5162f337e4b023b1c8989372\",\"name\":\"easyGym Ilford\",\"contact\":{\"twitter\":\"easygym\"},\"location\":{\"address\":\"Pioneer Point\",\"lat\":51.55744934529674,\"lng\":0.07090382360424949,\"cc\":\"GB\",\"city\":\"Ilford\",\"state\":\"Greater London\",\"country\":\"United Kingdom\",\"formattedAddress\":[\"Pioneer Point\",\"Ilford\",\"Greater London\",\"United Kingdom\"]},\"categories\":[{\"id\":\"4f4528bc4b90abdf24c9de85\",\"name\":\"Athletics & Sports\",\"pluralName\":\"Athletics & Sports\",\"shortName\":\"Athletics & Sports\",\"icon\":{\"prefix\":\"https:\\/\\/ss3.4sqi.net\\/img\\/categories_v2\\/shops\\/sports_outdoors_\",\"suffix\":\".png\"},\"primary\":true}],\"verified\":false,\"stats\":{\"checkinsCount\":292,\"usersCount\":58,\"tipCount\":2},\"rating\":7.4,\"ratingColor\":\"C5DE35\",\"ratingSignals\":8,\"hours\":{\"isOpen\":false,\"isLocalHoliday\":false},\"photos\":{\"count\":0,\"groups\":[]},\"hereNow\":{\"count\":0,\"summary\":\"Nobody here\",\"groups\":[]}},\"tips\":[{\"id\":\"51e5c4db498e1f652f6edfca\",\"createdAt\":1374012635,\"text\":\"Nice size, good price,great new equipment.  Not enough showers though.\",\"type\":\"user\",\"canonicalUrl\":\"https:\\/\\/foursquare.com\\/item\\/51e5c4db498e1f652f6edfca\",\"likes\":{\"count\":1,\"groups\":[],\"summary\":\"1 like\"},\"logView\":true,\"todo\":{\"count\":0},\"user\":{\"id\":\"16563197\",\"firstName\":\"John\",\"lastName\":\"Emerson\",\"gender\":\"male\",\"photo\":{\"prefix\":\"https:\\/\\/irs2.4sqi.net\\/img\\/user\\/\",\"suffix\":\"\\/WXXU4HYUVNFMTD2H.jpg\"}}}],\"referralId\":\"e-0-5162f337e4b023b1c8989372-0\"},{\"reasons\":{\"count\":0,\"items\":[{\"summary\":\"This spot is popular\",\"type\":\"general\",\"reasonName\":\"globalInteractionReason\"}]},\"venue\":{\"id\":\"4b5b3039f964a520c4e928e3\",\"name\":\"Cineworld\",\"contact\":{\"phone\":\"+448712002000\",\"formattedPhone\":\"+44 871 200 2000\"},\"location\":{\"address\":\"The Exchange Mall\",\"crossStreet\":\"Clements Rd\",\"lat\":51.5576,\"lng\":0.0741,\"postalCode\":\"IG1 1BP\",\"cc\":\"GB\",\"city\":\"Ilford\",\"state\":\"Greater London\",\"country\":\"United Kingdom\",\"formattedAddress\":[\"The Exchange Mall (Clements Rd)\",\"Ilford\",\"Greater London\",\"IG1 1BP\",\"United Kingdom\"]},\"categories\":[{\"id\":\"4bf58dd8d48988d17f941735\",\"name\":\"Movie Theater\",\"pluralName\":\"Movie Theaters\",\"shortName\":\"Movie Theater\",\"icon\":{\"prefix\":\"https:\\/\\/ss3.4sqi.net\\/img\\/categories_v2\\/arts_entertainment\\/movietheater_\",\"suffix\":\".png\"},\"primary\":true}],\"verified\":true,\"stats\":{\"checkinsCount\":1774,\"usersCount\":444,\"tipCount\":10},\"url\":\"http:\\/\\/www.cineworld.com\",\"rating\":7.2,\"ratingColor\":\"C5DE35\",\"ratingSignals\":57,\"hours\":{\"isOpen\":false,\"isLocalHoliday\":false},\"events\":{\"count\":14,\"summary\":\"14 movies\"},\"photos\":{\"count\":0,\"groups\":[]},\"storeId\":\"\",\"hereNow\":{\"count\":0,\"summary\":\"Nobody here\",\"groups\":[]}},\"tips\":[{\"id\":\"4b76ddce70c603bb8c2892b4\",\"createdAt\":1266081230,\"text\":\"Shows indian movies here one in a while!\",\"type\":\"user\",\"canonicalUrl\":\"https:\\/\\/foursquare.com\\/item\\/4b76ddce70c603bb8c2892b4\",\"likes\":{\"count\":2,\"groups\":[],\"summary\":\"2 likes\"},\"logView\":true,\"todo\":{\"count\":1},\"user\":{\"id\":\"330681\",\"firstName\":\"Ros\",\"lastName\":\"Abr\",\"gender\":\"male\",\"photo\":{\"prefix\":\"https:\\/\\/irs0.4sqi.net\\/img\\/user\\/\",\"suffix\":\"\\/blank_boy.png\",\"default\":true}}}],\"referralId\":\"e-0-4b5b3039f964a520c4e928e3-1\"}]}]}}";
		
		FourSquareVenueService.ResponseWrapper wrapper = JsonUtils.convrtToObject(expJsonReply, FourSquareVenueService.ResponseWrapper.class);
		ResponseEntity<FourSquareVenueService.ResponseWrapper> responseEntity = new ResponseEntity<FourSquareVenueService.ResponseWrapper>(wrapper, HttpStatus.ACCEPTED);
		Mockito.when(restTemplate.getForEntity(Mockito.eq(expUrl), Mockito.eq(FourSquareVenueService.ResponseWrapper.class))).thenReturn(responseEntity);
		
		List<VenueGroup> venueGroups = fourSquareVenueService.exploreVenue(name);
		Mockito.verify(restTemplate).getForEntity(Mockito.eq(expUrl), Mockito.eq(FourSquareVenueService.ResponseWrapper.class));
		
		//verify transformation
		assertNotNull(venueGroups);
		assertTrue(venueGroups.size()==1);
		//check a venue at 0 index
		VenueGroup actualVenueGroup = venueGroups.get(0);
		
		assertEquals("Name", "recommended", actualVenueGroup.getName());
		assertEquals("Type", "Recommended Places" , actualVenueGroup.getType());
		assertNotNull(actualVenueGroup.getItems());
		assertTrue(actualVenueGroup.getItems().size()==2);
		VenueGroupItem actualVenueGroupItem = actualVenueGroup.getItems().get(0);
		assertNotNull(actualVenueGroupItem.getVenue());
		Venue actualVenue = actualVenueGroupItem.getVenue();
		
		assertEquals("Name", "easyGym Ilford", actualVenue.getName());
		assertEquals("Id", "5162f337e4b023b1c8989372" , actualVenue.getId());
		assertEquals("Location.Address", "Pioneer Point", actualVenue.getLocation().getAddress());
		assertEquals("CategoryName", "Athletics & Sports", actualVenue.getCategoryName().get(0));		
	}
	
	
}

