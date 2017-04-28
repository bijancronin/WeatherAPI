<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta id="meta" name="viewport" content="width=device-width; initial-scale=1.0" />
</head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/libs/sidenav.css">

    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="https://raw2.github.com/medialize/URI.js/master/src/URI.js"></script>
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
    <script>
        function openNav() {
            document.getElementById("mySidenav").style.width = "250px";
        }

        function closeNav() {
            document.getElementById("mySidenav").style.width = "0";
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

        <h2><p align = "center" margin-bottom = 4em>Account</p></h2>
        <h2 style="color:red;">
            <p align = "center" margin-bottom = 4em>
                <c:if test="${error != null}">
                    <c:out value="${error}"/>
                </c:if>
            </p>
        </h2>
        <h2>
            <p align = "center" margin-bottom = 4em>
                <c:if test="${message != null}">
                    <c:out value="${message}"/>
                </c:if>
            </p>
        </h2>
        <br><br><br><br><br>
        <c:set var="username" value='${sessionScope["username"]}'/>
        <c:set var="name" value='${sessionScope["name"]}'/>
        <c:set var="error" value='${requestScope["error"]}'/>
        <c:set var="message" value='${requestScope["message"]}'/>
        <div class = "centeredOuter"> <div class = "centeredInner">
                <form action="<%=request.getContextPath()%>/user/UpdateAccountServlet" method="POST">
                    <label for="name">NAME</label>
                    <br/>
                    <input type="text" name="name" value="${name}">
                    <br/><br/>
                    <label for="name">USERNAME</label>
                    <br/>
                    <input type="text" name="username" value="${username}" readonly="true">
                    <br/><br/>
                    <label for="name">OLD PASSWORD</label>
                    <br/>
                    <input type="password" name="password" value="">
                    <br/><br/>
                    <label for="name">NEW PASSWORD</label>
                    <br/>
                    <input type="password" name="new_password" value="">
                    <div style = "text-align: center; margin-top: 3em;">
                        <button>Update</button>
                        <br><br>
                    </div>
                </form> 
            </div> </div>
    </body>
</html>