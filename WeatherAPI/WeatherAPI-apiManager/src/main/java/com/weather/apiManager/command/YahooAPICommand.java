package com.weather.apiManager.command;

import java.io.IOException;

import org.jsoup.nodes.Document;
import javax.xml.bind.JAXBException;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;

/*
 * This is the class for retrieving data from the weather.com website.  
 */
public class YahooAPICommand implements WeatherAPICommand {

    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;

    public YahooAPICommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
    
    @Override
    public String execute()  {

        // at first get the woeid for the lat an long

        String wurl = "https://query.yahooapis.com/v1/public/yql?q=select%20woeid%20from%20geo.places%20where%20text%3D%22(" + location.getLat() + "," + location.getLongit() + ")%22%20limit%201&diagnostics=false";		

        Document yahooApiResponse;
        try {
                yahooApiResponse = (Document) Jsoup.connect(wurl).timeout(10 * 1000).get();
                String xmlString = ((Element) yahooApiResponse).html();
                Document doc = (Document) Jsoup.parse(xmlString, "", Parser.xmlParser());

                String woeid = ((Element) doc).select("woeid").first().text().toString();

                YahooWeatherService service = new YahooWeatherService();
                Channel channel = service.getForecast(woeid, DegreeUnit.CELSIUS);

                return channel.toString();

        } catch (IOException | JAXBException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
        }

        return null;

    }
}
