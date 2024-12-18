package rentasad.library.tools.waehrungskursTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

//import com.extendedsystems.jdbc.advantage.ADSConnection;

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
    public final String jsonURLString;

    public WaehrungsKursTool() throws MalformedURLException
    {
        super();
        this.jsonURLString = "https://openexchangerates.org/api/latest.json?app_id=88170157fc564715821f0f7e54f6faf8";
    }

    /**
     * Reads the content of the specified URL and returns it as a string.
     *
     * @param urlString the URL to read from
     * @return the content read from the URL as a string
     * @throws IOException if an I/O exception occurs
     */
    public static String readUrl(String urlString) throws IOException
    {
        BufferedReader reader = null;
        try
        {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally
        {
            if (reader != null)
                reader.close();
        }
    }

    public String getjsonStringFromOpenExchangeRates() throws IOException
    {
        return readUrl(this.jsonURLString);
    }

    /**
     * Extracts a WaehrungskursItem from a JSON string fetched from Open Exchange Rates.
     *
     * @param jsonObjectString JSON string containing currency rates data.
     * @return A WaehrungskursItem containing the currency rates and base currency information.
     */
    public WaehrungskursItem getWaehrungsKursItemFromOpenExchangeRates(String jsonObjectString)
    {

        JSONObject headObject = new JSONObject(jsonObjectString);
        String base = headObject.getString("base");
        WaehrungenEnum basEnum = WaehrungenEnum.valueOf(base);
        long timeStamp = headObject.getLong("timestamp") * 1000;
        Calendar timeCalendar = new GregorianCalendar();
        timeCalendar.setTimeInMillis(timeStamp);
        JSONObject ratesObject = headObject.getJSONObject("rates");
        WaehrungskursItem kursItem = new WaehrungskursItem(timeCalendar.getTime(), basEnum);
        for (WaehrungenEnum waehrungItem : WaehrungenEnum.values())
        {
            /**
             * 10.05.2017
             * Falls eine Währung in der Währungsabfrage nicht mehr existiert oder auftaucht, wird sie ignoriert
             * und das Programm stürzt nicht ab
             * 
             */
            if (ratesObject.keySet().contains(waehrungItem.name()))
            {
                kursItem.getRatesMap().put(waehrungItem, ratesObject.getDouble(waehrungItem.name()));
            }

        }
        return kursItem;
    }
    


}
