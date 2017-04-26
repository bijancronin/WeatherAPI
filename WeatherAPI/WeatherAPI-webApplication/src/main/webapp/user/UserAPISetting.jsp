<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><meta id="meta" name="viewport" content="width=device-width; initial-scale=1.0" />
</head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/libs/slider.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/libs/sidenav.css">

    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="https://raw2.github.com/medialize/URI.js/master/src/URI.js"></script>

    <body>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href='<%=request.getContextPath()%>/user/UserHomepage.jsp'>Home</a>
            <a href="<%=request.getContextPath()%>/user/UserAccount.jsp">Account</a>
            <a href="<%=request.getContextPath()%>/user/UserSettings.jsp">Settings</a>
            <a href="<%=request.getContextPath()%>/user/LogoutServlet">Logout</a>
        </div>

        <span style="font-size:25px;cursor:pointer;color:grey" onclick="openNav()">&#9776;</span>
        <h2><p align = "center">Weather API Settings</p></h2>
        <br><br><br><br><br>
        <div class = "centeredOuter"> <div class = "centeredInner">
                <form action="<%=request.getContextPath()%>/user/UserAPISettingsServlet" method="GET">
                    <!-- Rounded switch -->
                    <div style="">
                        <h3>Apixu</h3>
                        <label class="switch">
                            <input type="checkbox" name="apixu" value="apixu" <c:out value='${requestScope["apixu"]}'/>>
                            <div class="slider round"></div>
                        </label>
                    </div>
                    <div style="">
                        <h3>Dark Sky</h3>
                        <label class="switch">
                            <input type="checkbox" name="darksky" value="darksky" <c:out value='${requestScope["darksky"]}'/>>
                            <div class="slider round"></div>
                        </label>
                    </div>
                    <div style="">
                        <h3>Foreca</h3>
                        <label class="switch">
                            <input type="checkbox" name="foreca" value="foreca" <c:out value='${requestScope["foreca"]}'/>>
                            <div class="slider round"></div>
                        </label>
                    </div>
                    <div style="">
                        <h3>Open Weather Map</h3>
                        <label class="switch">
                            <input type="checkbox" name="openweathermap" value="openweathermap" <c:out value='${requestScope["openweathermap"]}'/>>
                            <div class="slider round"></div>
                        </label>
                    </div>
                    <div style="">
                        <h3>Wunder Ground</h3>
                        <label class="switch">
                            <input type="checkbox" name="wunder" value="wunder" <c:out value='${requestScope["wunder"]}'/>>
                            <div class="slider round"></div>
                        </label>
                    </div>
                    <div style="">
                        <h3>Yahoo</h3>
                        <label class="switch">
                            <input type="checkbox" name="yahoo" value="yahoo" <c:out value='${requestScope["yahoo"]}'/>>
                            <div class="slider round"></div>
                        </label>
                    </div>
                    <button class = "settings">Update</button>
                </form>
            </div></div>
        <br><br>
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