package rentasad.library.tools.waehrungskursTool;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

/**
 * 
 * Gustini GmbH (2015)
 * Creation: 15.09.2015
 * Rentasad Library
 * rentasad.lib.tools.waehrungskursTool
 * 
 * @author Matthias Staud
 *
 *
 *         Description:
 *
 */
public class WaehrungsKursTool
{
    private static final int HTTP_TIMEOUT_SECONDS = 30;
    private static final String API_BASE_URL = "https://openexchangerates.org/api/latest.json";
    private static final String API_KEY = System.getenv().getOrDefault("OPEN_EXCHANGE_RATES_API_KEY", "88170157fc564715821f0f7e54f6faf8");
    public static final String JSON_URL_STRING = API_BASE_URL + "?app_id=" + API_KEY;

    private WaehrungsKursTool()
    {
        super();
    }

    /**
     * Reads the content of the specified URL and returns it as a string.
     *
     * @param urlString the URL to read from
     * @return the content read from the URL as a string
     * @throws IOException if an I/O exception occurs
     * @throws InterruptedException if the HTTP request is interrupted
     */
    public static String readUrl(String urlString) throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(Duration.ofSeconds(HTTP_TIMEOUT_SECONDS))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(HTTP_TIMEOUT_SECONDS))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
        {
            throw new IOException("HTTP request failed with status code: " + response.statusCode());
        }

        return response.body();
    }

    public static String getJsonStringFromOpenExchangeRates() throws IOException, InterruptedException
    {
        return readUrl(JSON_URL_STRING);
    }

    /**
     * Extracts a WaehrungskursItem from a JSON string fetched from Open Exchange Rates.
     *
     * @param jsonObjectString JSON string containing currency rates data.
     * @return A WaehrungskursItem containing the currency rates and base currency information.
     * @throws IllegalArgumentException if the JSON format is invalid or base currency is unknown
     */
    public static WaehrungskursItem getWaehrungsKursItemFromOpenExchangeRates(String jsonObjectString)
    {
        try
        {
            JSONObject headObject = new JSONObject(jsonObjectString);
            String base = headObject.getString("base");
            WaehrungenEnum basEnum = WaehrungenEnum.valueOf(base);
            long timeStamp = headObject.getLong("timestamp") * 1000;
            Calendar timeCalendar = new GregorianCalendar();
            timeCalendar.setTimeInMillis(timeStamp);
            JSONObject ratesObject = headObject.getJSONObject("rates");
            WaehrungskursItem kursItem = new WaehrungskursItem(timeCalendar.getTime(), basEnum);

            // Use Stream API to populate rates map, ignoring currencies not present in response
            Arrays.stream(WaehrungenEnum.values())
                    .filter(waehrungItem -> ratesObject.has(waehrungItem.name()))
                    .forEach(waehrungItem ->
                            kursItem.getRatesMap().put(waehrungItem, ratesObject.getDouble(waehrungItem.name())));

            return kursItem;
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Invalid currency base or malformed JSON: " + e.getMessage(), e);
        }
    }
}
