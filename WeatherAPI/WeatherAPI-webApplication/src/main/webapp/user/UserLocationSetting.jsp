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
                        var responseText = "<table>";
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
                            responseText += "<button onclick=\"chooseCity('"
                                    + location['lat'] + "','"
                                    + location['longit'] + "','"
                                    + location['state'] + "','"
                                    + location['city'] + "','"
                                    + location['zipcode'] + "')\">Select</button>";
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

        function chooseCity(latitude, longitude, state, city, zipcode) {
            var addFavoriteLocationForm = document.getElementById("addFavoriteLocationForm");
            addFavoriteLocationForm.latitude.value = latitude;
            addFavoriteLocationForm.longitude.value = longitude;
            addFavoriteLocationForm.state.value = state;
            addFavoriteLocationForm.city.value = city;
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

        function addDefaultLocation(latitude, longitude, city) {
            var addDefaultLocationForm = document.getElementById("addDefaultLocationForm");
            addDefaultLocationForm.latitude.value = latitude;
            addDefaultLocationForm.longitude.value = longitude;
            addDefaultLocationForm.city.value = city;
            addDefaultLocationForm.submit();
        }
    </script>
    <c:set var="error" value='${requestScope["error"]}'/>
    <c:set var="message" value='${requestScope["message"]}'/>
    <body>
        <span style="font-size:25px;cursor:pointer;color:grey" onclick="openNav()">&#9776;</span>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href='<%=request.getContextPath()%>/user/UserHomepage.jsp'>Home</a>
            <a href="<%=request.getContextPath()%>/user/UserAccount.jsp">Account</a>
            <a href="<%=request.getContextPath()%>/user/UserSettings.jsp">Settings</a>
            <a href="<%=request.getContextPath()%>/user/LogoutServlet">Logout</a>
        </div>

        <div class = "centeredOuter"> <div class = "centeredInner">
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
                <label for="city">Enter City</label>
                <input type="text" id="city" name="city">
                <button id="fetch_location">Search</button>
                <div style = "text-align: center; margin-top: 3em;">
                    <table id="locations">

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
                </form>

                <div style = "text-align: center; margin-top: 3em;">
                    <c:if test='${requestScope["defaultLocation"] != null}' >
                        <c:set var="defaultLocation" value='${requestScope["defaultLocation"]}'/>
                        <h2>Default Location</h2>
                        <table>
                            <tr>
                                <td>City</td>
                                <td>Delete</td>
                            </tr>
                            <tr>
                                <td><c:out value="${defaultLocation.city}" /></td>
                                <td><a href='javascript:deleteDefaultLocation()'>Delete</a></td>
                            </tr>
                        </table>
                    </c:if>
                    <c:if test='${requestScope["defaultLocation"] == null}' >
                        <h2>No Default Location Selected</h2>
                    </c:if>
                    <br><br><br>
                    <c:if test='${requestScope["favoriteLocation"] != null}'>
                        <h2>Favorite Locations</h2>
                        <table>
                            <tr>
                                <td>City</td>
                                <td>Make this Default</td>
                                <td>Delete</td>
                            </tr>

                            <c:forEach var="location" items='${requestScope["favoriteLocation"]}'>
                                <tr>
                                    <td><c:out value="${location.city}"/></td>
                                    <td><a href='javascript:addDefaultLocation("${location.lat}","${location.longit}","${location.city}")'>Make this Default</a></td>
                                    <td><a href='javascript:deleteFavoriteLocation("${location.locationId}")'>Delete</a></td>
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