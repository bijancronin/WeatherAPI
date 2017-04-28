<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><meta id="meta" name="viewport" content="width=device-width; initial-scale=1.0" />
</head>
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
    <body>
        <c:set var="error" value='${requestScope["error"]}'/>
        <c:set var="message" value='${requestScope["message"]}'/>
        <div class = "centeredOuter"> <div class = "centeredInner">
                <div style = "text-align: center; margin-bottom: 10em;">
                    <h1>myCast</h1>
                    <h2>Personalized Weather Aggregator</h2>
                    <c:if test="${error != null}">
                        <h2 style="color:red;">
                            <c:out value="${error}"/>
                        </h2>
                    </c:if>
                    <c:if test="${message != null}">
                        <h2><c:out value="${message}"/></h2>
                    </c:if>

                </div>
                <form action="<%=request.getContextPath()%>/LoginServlet" method="POST">
                    <label for="name">USERNAME</label>
                    <br/>
                    <input type="text" name="username" value="">
                    <br/><br/>
                    <label for="name">PASSWORD</label>
                    <br/>
                    <input type="Password" name="password" value="">
                    <br/>
                    <a href ="" style="text-decoration:none">Forgot Password?</a>
                    <div style = "text-align: center; margin-top: 3em;">
                        <button>Sign In</button>
                    </div>
                </form>	
                <div style = "text-align: center; margin-top: 1em;">
                    <br>
                    <footer>DON'T HAVE AN ACCOUNT? <a href = "<%=request.getContextPath()%>/CreateAccount.jsp">SIGN UP</a></footer>
                </div>
            </div> </div>
    </body>
</html>