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

--------------------------------------------------------------------------------------------------------
Adding Apixu API jar in local maven repo
--------------------------------------------------------------------------------------------------------
Since jar for Apixu API is not available in online maven repository, need to add the jar for this
manually in local maven repo. Below are the steps required to achieve this:
1. Download the jar from https://drive.google.com/open?id=0ByJmPKfs34pQME5XMnhhcUQ3Qlk
2. Run following command
      mvn install:install-file -Dfile=weatherlibraryjava.jar -DgroupId=com.apixu.api -DartifactId=apixu -Dversion=1.0 -Dpackaging=jar
NOTE : Maven command used above needs to be issued from the location where you saved the downloaded jar. Else give full path to jar in '-Dfile' attribute in the command.

--------------------------------------------------------------------------------------------------------
Downloading and Building code
--------------------------------------------------------------------------------------------------------
1. Download code on your local machine.
2. Run 'mvn clean install' from the location you saved the code in.
3. Once build is successfull. Copy .war file from /WeatherAPI/WeatherAPI-webApplication/target/WeatherAPI.war to tomcat's webapp folder.
4. Run the tomcat server and hit http://localhost:8080/WeatherAPI/ to access the web application.

--------------------------------------------------------------------------------------------------------
FAQs
--------------------------------------------------------------------------------------------------------
1. While building code, maven complains "Not able to find Apixu jar".
A. Follow instructions above under "Adding Apixu API jar in local maven repo" heading.
