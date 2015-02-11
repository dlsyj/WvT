package nl.scanazon.counterapp.networking;

import nl.scanazon.counterapp.model.ShoppingList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ShoppingListRequest extends SpringAndroidSpiceRequest<ShoppingList> {

	private String listId;
	
	public ShoppingListRequest(String listId) {
	    super(ShoppingList.class);
	    this.listId = listId;
	 }
	
	@Override
	public ShoppingList loadDataFromNetwork() throws Exception{
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("shoppinglist_id", listId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
		
		String url = String.format(RequestHelper.HOST_URL + "/getshoppinglist");
		
		RestTemplate restTemplate = getRestTemplate();
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		return restTemplate.postForObject(url, request, ShoppingList.class);
	}
	
	public String createCacheKey() {
	      return "shoppinglist." + listId;
	}
}
