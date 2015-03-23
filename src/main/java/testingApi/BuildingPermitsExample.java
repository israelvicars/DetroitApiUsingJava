package testingApi;

import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.ClientResponse;

public class BuildingPermitsExample {

	static void showPermitsInDateRange(String startDate, String endDate) throws SodaError {
		// The API's class for reading data sets from the Detroit API
		Soda2Consumer consumer = Soda2Consumer.newConsumer("https://data.detroitmi.gov/");
		
		// When we find the data set, which rows will we want
		SoqlQuery permitsInRange = new SoqlQueryBuilder()
		.setWhereClause("permit_issued>=\'"+ startDate +"\'")
		.setWhereClause("permit_issued<=\'"+ endDate +"\'")
		.build();
		
		// To show a raw String of the results, 
		// First, initialize an empty ClientResponse generic API object
		ClientResponse response;
		try {
			// Give it a GET request formatted to include (1) which dataset, (2) what output type, (3) which rows 
			response = consumer.query("cthr-59vy", HttpLowLevel.JSON_TYPE, permitsInRange);
			// Put it all in a String
			String fullQueryResults = response.getEntity(String.class);
			// Print out the string
			System.out.println(fullQueryResults);
		} catch (LongRunningQueryException e) {
			// If something goes wrong, say so
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String startDate = "2015-03-01T00:00:00";
		String endDate = "2015-03-23T00:00:00";
		try {
			showPermitsInDateRange(startDate, endDate);
		} catch (SodaError e) {
			e.printStackTrace();
		}
	}
}
