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
            font-weight: normal;
        }
        
        input::-webkit-input-placeholder { /* WebKit browsers */
            text-indent: 5px;
        }

        input[type="search"] {

            height: 33px;
            width: 250px;
            margin: 0;
            padding-bottom: 4px;
            font-size: 15px;
            border: 0;
            background-color: white;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            color: grey;
            -webkit-appearance:none;


        }

        input[type="submit"] {
            height: 33px;
            width: 50px;
            margin: 0;
            padding: 0;
            font-size: 12px;
            background-color: rgb(80,210,194);
            border: 0;
            color: white;
            letter-spacing: 1px;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);

        }

    </style>
    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="https://raw2.github.com/medialize/URI.js/master/src/URI.js"></script>
    <script>
        $(document).ready(function () {
            $('#fetch_location').click(function (event) {
                var city = $('#city').val();
                $.post('FetchLocationServlet', {
                    city: city
                }, function (response) {
                    if (!$.trim(response)) {
                        $('#locations').empty();
                        $('#locations').append("<tr><td>No Records Found</td></tr>");
                    } else {
                        var parsedJson = $.parseJSON(response);
                        var responseText = "<table width='100%'>";
                        $(parsedJson).each(function (i, location) {
                            responseText += "<tr>";
                            responseText += "<td>";
                            responseText += location['city'];
                            responseText += ",";
                            responseText += location['state'];
                            responseText += " - ";
                            responseText += location['zipcode'];
                            responseText += "</td>";
                            responseText += "<td>";
                            responseText += "<a href=\"javascript:chooseCity('"
                                    + location['lat'] + "','"
                                    + location['longit'] + "','"
                                    + location['state'] + "','"
                                    + location['city'] + "','"
                                    + location['country'] + "','"
                                    + location['zipcode'] + "')\">ADD TO FAVORITE</a>";
                            responseText += "</td>";
                            responseText += "</tr>";
                        });
                        responseText += "</table>";
                        $('#locations').empty();
                        $('#locations').append(responseText);
                    }

                });
            });
        });

        function chooseCity(latitude, longitude, state, city, country, zipcode) {
            var addFavoriteLocationForm = document.getElementById("addFavoriteLocationForm");
            addFavoriteLocationForm.latitude.value = latitude;
            addFavoriteLocationForm.longitude.value = longitude;
            addFavoriteLocationForm.state.value = state;
            addFavoriteLocationForm.city.value = city;
            addFavoriteLocationForm.country.value = country;
            addFavoriteLocationForm.zipcode.value = zipcode;
            addFavoriteLocationForm.submit();
        }

        function deleteDefaultLocation() {
            var deleteDefaultLocationForm = document.getElementById("deleteDefaultLocationForm");
            deleteDefaultLocationForm.submit();
        }

        function deleteFavoriteLocation(locationId) {
            var deleteFavoriteLocationForm = document.getElementById("deleteFavoriteLocationForm");
            deleteFavoriteLocationForm.locationId.value = locationId;
            deleteFavoriteLocationForm.submit();
        }

        function addDefaultLocation(latitude, longitude, city, state, country, zipcode) {
            var addDefaultLocationForm = document.getElementById("addDefaultLocationForm");
            addDefaultLocationForm.latitude.value = latitude;
            addDefaultLocationForm.longitude.value = longitude;
            addDefaultLocationForm.city.value = city;
            addDefaultLocationForm.state.value = state;
            addDefaultLocationForm.country.value = country;
            addDefaultLocationForm.zipcode.value = zipcode;
            addDefaultLocationForm.submit();
        }
    </script>
    </head>
    <c:set var="error" value='${requestScope["error"]}'/>
    <c:set var="message" value='${requestScope["message"]}'/>
    <body>
        <span style="font-size:25px;cursor:pointer;color:grey" onclick="openNav()">&#9776;</span>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href='<%=request.getContextPath()%>/user/FetchApiSubscriptionServlet'>Home</a>
            <a href="<%=request.getContextPath()%>/user/UserAccount.jsp">Account</a>
            <a href="<%=request.getContextPath()%>/user/UserSettings.jsp">Settings</a>
            <a href="<%=request.getContextPath()%>/user/LogoutServlet">Logout</a>
        </div>

        <div class = "centeredOuter"> <div class = "centeredInner" style="width:80%;">
                <div style = "text-align: center; margin-bottom: 10em;">
                    <h1>Location Settings</h1>
                    <h2 style="color:red;">
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
                <div style = "text-align: center; ">
                    <input style="width:80%;" type="search" id="city" name="city" placeholder="Search city, zip, or place"/>
                    <input id="fetch_location" type="submit" value = "GO"/>
                </div>
                <div style = "text-align: center; margin-top: 3em;">
                    <table id="locations" width="100%">

                    </table>
                </div>
                <form id="addFavoriteLocationForm" name="addFavoriteLocationForm" 
                      action="<%=request.getContextPath()%>/user/AddFavoriteLocationServlet" 
                      method="POST">
                    <input type="hidden" name="latitude"/>
                    <input type="hidden" name="longitude"/>
                    <input type="hidden" name="city"/>
                    <input type="hidden" name="state"/>
                    <input type="hidden" name="zipcode"/>
                    <input type="hidden" name="country"/>
                </form>

                <div style = "text-align: center; margin-top: 3em;">
                    <c:if test='${requestScope["defaultLocation"] != null}' >
                        <c:set var="defaultLocation" value='${requestScope["defaultLocation"]}'/>
                        <h2>Default Location</h2>
                        <table width="100%">
                            <tr>
                                <th>City</th>
                                <th>Delete</th>
                            </tr>
                            <tr>
                                <td><c:out value="${defaultLocation.city}" /></td>
                                <td><a href='javascript:deleteDefaultLocation()'>DELETE</a></td>
                            </tr>
                        </table>
                    </c:if>
                    <c:if test='${requestScope["defaultLocation"] == null}' >
                        <h2>No Default Location Selected</h2>
                    </c:if>
                    <br><br><br>
                    <c:if test='${requestScope["favoriteLocation"] != null}'>
                        <h2>Favorite Locations</h2>
                        <table width="100%">
                            <tr>
                                <th>City</th>
                                <th>Make this Default</th>
                                <th>Delete</th>
                            </tr>

                            <c:forEach var="location" items='${requestScope["favoriteLocation"]}'>
                                <tr>
                                    <td><c:out value="${location.city}"/></td>
                                    <td><a href='javascript:addDefaultLocation("${location.lat}","${location.longit}","${location.city}","${location.state}","${location.country}","${location.zipcode}")'>MAKE DEFAULT</a></td>
                                    <td><a href='javascript:deleteFavoriteLocation("${location.locationId}")'>DELETE</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <c:if test='${requestScope["favoriteLocation"] == null}' >
                        <h2>No Favorites Locations</h2>
                    </c:if>
                    <form id="deleteDefaultLocationForm" name="deleteDefaultLocationForm" 
                          action="<%=request.getContextPath()%>/user/DeleteLocationServlet" 
                          method="POST">
                        <input type="hidden" name="defaultLocation" value="defaultLocation"/>
                    </form>
                    <form id="deleteFavoriteLocationForm" name="deleteFavoriteLocationForm" 
                          action="<%=request.getContextPath()%>/user/DeleteLocationServlet" 
                          method="POST">
                        <input type="hidden" name="favoriteLocation" value="favoriteLocation"/>
                        <input type="hidden" name="locationId" />
                    </form>
                    <form id="addDefaultLocationForm" name="addDefaultLocationForm" 
                          action="<%=request.getContextPath()%>/user/AddDefaultLocationServlet" 
                          method="POST">
                        <input type="hidden" name="latitude"/>
                        <input type="hidden" name="longitude"/>
                        <input type="hidden" name="city"/>
                        <input type="hidden" name="state"/>
                        <input type="hidden" name="country"/>
                        <input type="hidden" name="zipcode"/>
                    </form>
                </div>
            </div> </div>
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