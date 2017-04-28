<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta id="meta" name="viewport" content="width=device-width; initial-scale=1.0" />
</head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/libs/sidenav.css">

    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="https://raw2.github.com/medialize/URI.js/master/src/URI.js"></script>
    <script>
        $(document).ready(function () {
            var apiSubscription = '${apiSubscription}';
            if(!$.trim(apiSubscription)) {
                $('#errorMessage').empty();
                $('#errorMessage').append("Invalid Request!")
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
                            $("#userlocation").append("<h2>Forecast for Location</h2>");
                            $("#userlocation").append("City : "+parsedLocation["city"]);
                            $("#userlocation").append("<br>State : "+parsedLocation["state"]);
                            $("#userlocation").append("<br>Country : "+parsedLocation["country"]);
                            $("#userlocation").append("<br>zipcode : "+parsedLocation["zipcode"]);
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
                        $("#"+api).append("Unable to fetch forecast for \"" + api
                                + "\" at this time");
                    } else {
                        try {
                            $("#"+api).empty();
                            var parsedForecast = $.parseJSON(response);
                            $("#"+api).append("<h2>" + api + "</h2>");
                            $("#"+api).append("<label>Today's Weather</label>");
                            $("#"+api).append("<br><label>");
                            $("#"+api).append("Max Temperature : " + parsedForecast["daily"][0]["max_temperature"]);
                            $("#"+api).append("<br>Min Temperature : " + parsedForecast["daily"][0]["min_temperature"]);
                            $("#"+api).append("<br>Summary : " + parsedForecast["daily"][0]["summary"]);
                            $("#"+api).append("</label>");
                        } catch(e) {
                            console.log(e);
                            $("#"+api).append("<h2>" + api + "</h2>");
                            $("#"+api).empty();
                            $("#"+api).append("Unable to fetch forecast for \"" + api
                                    + "\" at this time");
                        }
                    }
                }
            );
        }
    </script>
    <body>
        <span style="font-size:25px;cursor:pointer;color:grey" onclick="openNav()">&#9776;</span>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href='<%=request.getContextPath()%>/user/FetchApiSubscriptionServlet'>Home</a>
            <a href="<%=request.getContextPath()%>/user/UserAccount.jsp">Account</a>
            <a href="<%=request.getContextPath()%>/user/UserSettings.jsp">Settings</a>
            <a href="<%=request.getContextPath()%>/user/LogoutServlet">Logout</a>
        </div>

        <h2><p align = "center" margin-bottom = 4em>Welcome <c:out value='${sessionScope["name"]}'/></p></h2>
        
        <div class = "centeredOuter"> <div class = "centeredInner">
                <div style = "text-align: center; margin-bottom: 10em;">
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
                
                <div id="api_forecasts" style = "text-align: center; margin-top: 3em;">
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
