<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout.css">
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
    <c:set var="error" value='${requestScope["error"]}'/>
    <c:set var="message" value='${requestScope["message"]}'/>
    <body>
        <div class = "centeredOuter"> <div class = "centeredInner">
                <div style = "text-align: center; margin-bottom: 6em;">
                    <h1>MyCast</h1>
                    <h2>Personalized Weather Aggregator</h2>
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
                <form action='<%=request.getContextPath()%>/CreateAccountServlet' method="POST">
                    <label for="name">NAME</label>
                    <br/>
                    <input type="text" name="name" value="">
                    <br/><br/>
                    <label for="name">EMAIL</label>
                    <br/>
                    <input type="text" name="username" value="">
                    <br/><br/>
                    <label for="name">PASSWORD</label>
                    <br/>
                    <input type="password" name="password" value="">
                    <div style = "text-align: center; margin-top: 3em;">
                        <button>Create</button>
                    </div>
                </form> 
                <div style = "text-align: center; margin-top: 3em;">
                    <br>
                    <footer>ALREADY HAVE AN ACCOUNT? <a href = "<%=request.getContextPath()%>/index.jsp">SIGN IN</a></footer>
                </div>
            </div> </div>
    </body>
</html>