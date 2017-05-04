<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta id="meta" name="viewport" content="width=device-width; initial-scale=1.0" />

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/libs/sidenav.css">
    <style>
        table, td, th {    
            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            text-align: left;
        }

        table {
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-collapse: collapse;
            border:1px solid;
            border-color:lightgrey;
            width: 100%;
        }

        th, td {
            padding: 10px;
        }

        tr {

            color: grey;
        }

        th {

            background-color: #CBEFE5;
            color: dimgrey;
            font-size: 18px;
            font-weight: normal;}
    </style>
    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="https://raw2.github.com/medialize/URI.js/master/src/URI.js"></script>
    <script>
        $(document).ready(function () {
            var apiSubscription = '${apiSubscription}';
            if(!$.trim(apiSubscription)) {
                var url = "<%=request.getContextPath()%>/user/FetchApiSubscriptionServlet";
                $(location).attr('href',url);
                return;
            } else {
                var parsedApiSubscription = $.parseJSON(apiSubscription);
                $("#api_forecasts")
                            .append("<div id='userlocation'><label>Fetching Location "
                            + "...</label></div><br><br>");
                    fetchUserLocation();
                $(parsedApiSubscription).each(function(index, api) {
                    $("#api_forecasts")
                            .append("<div id='" + api + "'><label>Fetching " + api 
                            + "'s Forecast...</label><br><img src='<%=request.getContextPath()%>/user/img/progress-bar.gif' width='50' height='50'/></div><br><br>");
                    fetchAPIForecast(api);
                });
            }
        });
        
        function fetchUserLocation() {
            $.get('<%=request.getContextPath()%>/rest/api/getlocation/<%=request.getSession().getAttribute("username")%>'
                , function(response) {
                    if(!$.trim(response)) {
                        $("#userlocation").empty();
                        $("#userlocation").append("Unable to fetch user location");
                    } else {
                        try {
                            var parsedLocation = $.parseJSON(response);
                            $("#userlocation").empty();
                            $("#userlocation").append("<h3>Showing Forecast for : " + parsedLocation["city"] + ", " 
                                    + parsedLocation["state"] + ", " + parsedLocation["zipcode"] + "</h3>");
                            $("#userlocation").append("<br><a href='<%=request.getContextPath()%>/user/GetDefaultAndFavoriteLocationsServlet' >CHANGE LOCATION</a>");
                        } catch(e) {
                            console.log(e);
                            $("#userlocation").empty();
                            $("#userlocation").append("Unable to fetch user location");
                        }
                    }
                }
            );
        }
        
        function fetchAPIForecast(api) {
            $.get('<%=request.getContextPath()%>/rest/api/'+api+'/<%=request.getSession().getAttribute("username")%>'
                , function(response) {
                    if(!$.trim(response)) {
                        $("#"+api).empty();
                        $("#"+api).append("<table width='100%'>"
                        + "<tr>"
                        + "<th>Forecast From</th>"
                        + "<th>Currently</th>"
                        + "<th>Condition</th>"
                        + "<th>High/Low</th>"
                        + "<th>Precipitation</th>"
                        + "<th>Wind</th>"
                        + "<th>Humidity</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>" + api + "</td>"
                        + "<td>--&#8457;</td>"
                        + "<td>-</td>"
                        + "<td>--&#8457; / --&#8457;</td>"
                        + "<td>--%</td>"
                        + "<td>--mph</td>"
                        + "<td>--%</td>"
                        + "</tr>"
                        + "</table>");
                    } else {
                        try {
                            $("#"+api).empty();
                            var parsedForecast = $.parseJSON(response);
                            var current = getCurrentForecast(parsedForecast["hourly"], api);
                            var currentTemperature = current["temperature"];
                            var high = '';
                            var low = '';
                            if(api === 'weatherbit') {
                                currentTemperature = convertCelsiusToFahrenheit(currentTemperature);
                                high = currentTemperature;
                                low = currentTemperature;
                            } else {
                                high = parsedForecast["daily"][0]["max_temperature"];
                                low = parsedForecast["daily"][0]["min_temperature"];
                            }
                            if(api === 'foreca') {
                                currentTemperature = convertCelsiusToFahrenheit(currentTemperature);
                                high = convertCelsiusToFahrenheit(high);
                                low = convertCelsiusToFahrenheit(low);
                            }
                            $("#"+api).append("<table width='100%'>"
                            + "<tr>"
                            + "<th>Forecast From</th>"
                            + "<th>Currently</th>"
                            + "<th>Condition</th>"
                            + "<th>High/Low</th>"
                            + "<th>Precipitation</th>"
                            + "<th>Wind</th>"
                            + "<th>Humidity</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td>" + api + "</td>"
                            + "<td>" + currentTemperature + "&#8457;</td>"
                            + "<td>" + current["summary"] + "</td>"
                            + "<td>" + high 
                            + "&#8457; /" + low 
                            + "&#8457;</td>"
                            + "<td>" + current["precipitation"] + "%</td>"
                            + "<td>" + current["wind_speed"] + "mph</td>"
                            + "<td>" + current["humidity"] + "%</td>"
                            + "</tr>"
                            + "</table>");
                        } catch(e) {
                            console.log(e);
                            $("#"+api).empty();
                            $("#"+api).append("<table width='100%'>"
                            + "<tr>"
                            + "<th>Forecast From</th>"
                            + "<th>Currently</th>"
                            + "<th>Condition</th>"
                            + "<th>High/Low</th>"
                            + "<th>Precipitation</th>"
                            + "<th>Wind</th>"
                            + "<th>Humidity</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td>" + api + "</td>"
                            + "<td>--&#8457;</td>"
                            + "<td>-</td>"
                            + "<td>--&#8457; / --&#8457;</td>"
                            + "<td>--%</td>"
                            + "<td>--mph</td>"
                            + "<td>--%</td>"
                            + "</tr>"
                            + "</table>");
                        }
                    }
                }
            );
        }
        
        function getCurrentForecast(hourly, api) {
            if(api === 'wunder') {
                return hourly["data"][0];
            } else if(api === 'weatherbit') {
                return hourly["data"][0];
            } else {
                var d = new Date();
                var currentTimeInMillis = Date.parse(d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate() + " " + d.getHours() + ":00");
                var currentIndex = 0;
                $(hourly["data"]).each(function(index, forecast) {
                    var timeInMillis = Date.parse(forecast["time"]);
                    if(currentTimeInMillis === timeInMillis) {
                        currentIndex = index;
                    }
                });
                console.log(currentIndex);
                return hourly["data"][currentIndex];
            }
        }
        
        function convertCelsiusToFahrenheit(temperatureC) {
            return Math.round(temperatureC * 1.8 + 32);
        }
        
    </script>
    </head>
    <body>
        <span style="font-size:25px;cursor:pointer;color:grey" onclick="openNav()">&#9776;</span>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href='<%=request.getContextPath()%>/user/FetchApiSubscriptionServlet'>Home</a>
            <a href="<%=request.getContextPath()%>/user/UserAccount.jsp">Account</a>
            <a href="<%=request.getContextPath()%>/user/UserSettings.jsp">Settings</a>
            <a href="<%=request.getContextPath()%>/user/LogoutServlet">Logout</a>
        </div>

        <h2><p align = "center">Welcome <c:out value='${sessionScope["name"]}'/></p></h2>
        
        <div class = "centeredOuter"> <div class = "centeredInner">
                <div style = "text-align: center; margin-bottom: 2em;">
                    <h1>Forecast</h1>
                    <h2 id="errorMessage" style="color:red;">
                        <c:if test="${error != null}">
                            <c:out value="${error}"/>
                        </c:if>
                    </h2>
                    <h2>
                        <c:if test="${message != null}">
                            <c:out value="${message}"/>
                        </c:if>
                    </h2>
                </div>
                
                <div id="api_forecasts" style = "width: 100%; text-align: center;">
                </div>
                
            </div> </div>
        
        <div align = "center"></div>
        <script>
            function openNav() {
                document.getElementById("mySidenav").style.width = "250px";
            }

            function closeNav() {
                document.getElementById("mySidenav").style.width = "0";
            }

        </script>

        <script type="text/javascript">
            function get(name) {
                if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
                    return decodeURIComponent(name[1]);
            }
            var param = get("myVar");
            if (param == "1")
            {
                $("body").html("bye");
            }


        </script>
    </body>
</html>
