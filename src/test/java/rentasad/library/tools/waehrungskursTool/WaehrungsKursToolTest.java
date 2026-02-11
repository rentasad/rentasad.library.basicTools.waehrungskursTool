package rentasad.library.tools.waehrungskursTool;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;

public class WaehrungsKursToolTest
{

	@Test
	public void testGetWaehrungsKursItemFromOpenExchangeRates_ValidInput() throws MalformedURLException
	{
		String jsonString = "{ \"base\": \"USD\", \"timestamp\": 1609459200, \"rates\": { \"EUR\": 0.82, \"GBP\": 0.74 } }";
		WaehrungskursItem result = WaehrungsKursTool.getWaehrungsKursItemFromOpenExchangeRates(jsonString);

		// Validate base currency
		Assertions.assertEquals(WaehrungenEnum.USD, result.getBaseCurrencyEnum());

		// Validate timestamp
		Date expectedDate = new Date(1609459200000L); // 1609459200 * 1000
		Assertions.assertEquals(expectedDate, result.getTimeStamp());

		// Validate rates map
		HashMap<WaehrungenEnum, Double> expectedRates = new HashMap<>();
		expectedRates.put(WaehrungenEnum.EUR, 0.82);
		expectedRates.put(WaehrungenEnum.GBP, 0.74);
		Assertions.assertEquals(expectedRates, result.getRatesMap());
	}

	@Test
	public void testGetWaehrungsKursItemFromOpenExchangeRates_MissingRates() throws MalformedURLException
	{
		String jsonString = "{ \"base\": \"USD\", \"timestamp\": 1609459200, \"rates\": { } }";
		WaehrungskursItem result = WaehrungsKursTool.getWaehrungsKursItemFromOpenExchangeRates(jsonString);

		// Validate base currency
		Assertions.assertEquals(WaehrungenEnum.USD, result.getBaseCurrencyEnum());

		// Validate timestamp
		Date expectedDate = new Date(1609459200000L); // 1609459200 * 1000
		Assertions.assertEquals(expectedDate, result.getTimeStamp());

		// Validate rates map is empty
		HashMap<WaehrungenEnum, Double> expectedRates = new HashMap<>();
		Assertions.assertEquals(expectedRates, result.getRatesMap());
	}

	@Test
	public void testGetWaehrungsKursItemFromOpenExchangeRates_InvalidBaseCurrency() throws MalformedURLException
	{
		String jsonString = "{ \"base\": \"INVALID\", \"timestamp\": 1609459200, \"rates\": { \"EUR\": 0.82, \"GBP\": 0.74 } }";

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			WaehrungsKursTool.getWaehrungsKursItemFromOpenExchangeRates(jsonString);
		});
	}

	@Test
	public void testGetWaehrungsKursItemFromOpenExchangeRates_InvalidJsonFormat() throws MalformedURLException
	{
		String jsonString = "{ \"bas\": \"USD\", \"timestamp\": 1609459200, \"rates\": { \"EUR\": 0.82, \"GBP\": 0.74 } }";


		Assertions.assertThrows(JSONException.class, () -> {
			WaehrungsKursTool.getWaehrungsKursItemFromOpenExchangeRates(jsonString);
		});
	}
}