Weather-API project:

The common elements of the JSON that will be returned for all the APIs are:
{
  "latitude": 47.20296790272209,
  "longitude": -123.41670367098749,
 "hourly": {
    "summary": "Rain throughout the day.",
    "icon": "rain",
    "data": [
      {
        "time": 1453399200,
        "summary": "Rain",
        "icon": "rain",
        "precipitation": 0.1379,
        "precipProbability": 0.85,
        "precipType": "rain",
        "temperature": 48.16,
        "humidity": 0.95,
        "windSpeed": 4.47,
        "windBearing": 166,
        "visibility": 3.56,
        "pressure": 1009.97
      },
…
},
"daily": {
 {
       "date": "18 Feb 2017",
       "day": "Sat",
       "high": "-10",
       "low": "-18",
       "text": "Mostly Cloudy",
       "icon": "clouds"
  },
…
},
"alerts": [
    {
      "title": "Flood Watch for Mason, WA",
      "time": 1453375020,
      "expires": 1453407300
    },
...
}

