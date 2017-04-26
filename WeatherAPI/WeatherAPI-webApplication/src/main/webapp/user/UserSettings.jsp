<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta id="meta" name="viewport" content="width=device-width; initial-scale=1.0" />
</head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout.css">
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

        <h2><p align = "center">Settings</p></h2>
        <div class = "centeredOuter"> <div class = "centeredInner">
                <div style = "text-align: center; margin-top: 3em;">
                    <form action="<%=request.getContextPath()%>/user/GetUserAPISubscriptionServlet">
                        <button>API Settings</button>
                    </form>
                    <br><br>
                </div>
                <div style = "text-align: center; margin-top: 2em;">
                    <form action="<%=request.getContextPath()%>/user/GetDefaultAndFavoriteLocationsServlet">
                        <button>Locations</button>
                    </form>
                    <br><br>
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