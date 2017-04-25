package com.weather.apiManager.dl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import java.sql.Timestamp;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class APIResponsesDAOTest {
    
    private APIResponsesDAO apiResponsesDAO;
    private APIResponseBean apiResponseBean;
    private WeatherAPIGeoLocation location;
    
    @Before
    public void setUp() {
        apiResponseBean = new APIResponseBean();
        apiResponseBean.setLatitude(30.04);
        apiResponseBean.setLongitude(30.04);
        apiResponseBean.setCity("Dummy");
        apiResponseBean.setState("Dummy");
        apiResponseBean.setCountry("Dummy");
        apiResponseBean.setApi(APIResponsesDAO.DARK_SKY);
        apiResponseBean.setJson("{}");
        
        // Substracting 3800000 to make request 60 minutes older
        // so that when get test runs it deletes the
        // dummy record.
        apiResponseBean.setRequestTime(new Timestamp(Calendar
                .getInstance().getTimeInMillis() - 3800000));
        apiResponsesDAO = new APIResponsesDAO();
        location = new WeatherAPIGeoLocation();
        location.setLat(30.04);
        location.setLongit(30.04);
    }
    
    @Test
    public void testAddApiResponseSuccess() {
        apiResponseBean.setCity("Dummy");
        boolean actual = apiResponsesDAO.addApiResponse(apiResponseBean);
        boolean expected = true;
        assertEquals(expected, actual);
        
        // Need to do this in order to delete recently added dummy record.
        apiResponsesDAO.getAPIResponse(
                apiResponsesDAO.DARK_SKY, location);
    }
    
    @Test
    public void testAddApiResponseFailure() {
        apiResponseBean.setCity(null);
        boolean actual = apiResponsesDAO.addApiResponse(apiResponseBean);
        boolean expected = false;
        assertEquals(expected, actual);
    }
    
    /**
     * This method tests whether APIResponseDAO deletes
     * requests which is older than 1 hour or not.
     */
    @Test
    public void testGetAPIResponseExpiredRequest() {
        // Need to do this in order add dummy record which will be deleted.
        apiResponseBean.setCity("Dummy");
        apiResponsesDAO.addApiResponse(apiResponseBean);
        
        String actual = apiResponsesDAO.getAPIResponse(
                apiResponsesDAO.DARK_SKY, location);
        assertThat(actual, is(nullValue()));
    }
}
