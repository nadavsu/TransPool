<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@page import="utils.*" %>
<%@ page import="constants.Constants" %>
<head>
    <title>TransPool</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="../../common/images/icon32.png"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../login/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../login/vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../login/vendor/animsition/css/animsition.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../login/css/util.css">
    <link rel="stylesheet" type="text/css" href="../login/css/login.css">
    <!--===============================================================================================-->
</head>
<body>

<div class="limiter">
    <% String usernameFromSession = SessionUtils.getUsername(request);%>
    <% if (usernameFromSession == null) {%>
    <% Object errorMessage = request.getAttribute(Constants.USERNAME_ERROR);%>
    <% if (errorMessage != null) {%>
    <div class="container-login100">
        <div class="wrap-login100">

            <form class="login100-form validate-form" method="GET" action="login">
					<span class="login100-form-title p-b-26">
						<img src="../../common/images/login_logo.png" alt="TransPool" id="logo/">
					</span>
                <div class="wrap-input100 validate-input" data-validate="Must enter a username.">
                    <input class="input100" type="text" name="username" id="username">
                    <span class="focus-input100" data-placeholder="username"></span>
                    <span class="bg-danger" style="color:#ff4d4d;"><%=errorMessage%></span>
                    <% } %>
                    <% } else {%>
                    <h1>Welcome back, <%=usernameFromSession%></h1>
                    <a href="../../home.html">Click here to enter</a>
                    <br/>
                    <a href="login?logout=true" id="logout">logout</a>
                    <% }%>
                </div>

                <div>
                    <input type="radio" id="rider" name="account_type" value="rider" checked>
                    <label for="rider">I'm a rider</label><br>
                    <input type="radio" id="driver" name="account_type" value="driver">
                    <label for="driver">I'm a driver</label><br>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Login
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!--===============================================================================================-->
<script src="../login/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="../login/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
<script src="../login/vendor/bootstrap/js/popper.js"></script>
<script src="../login/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="../login/js/login_validator.js"></script>

</body>
</html>

<<%--%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@page import="utils.*" %>
<%@ page import="constants.Constants" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="/common/stylesheet/index.css">
    <title>TransPool</title>
    <link rel="icon" href="/common/images/icon32.png">
</head>
<body>
<div class="container">
    <% String usernameFromSession = SessionUtils.getUsername(request);%>
    <% if (usernameFromSession == null) {%>
    <img src="/common/images/splash_screen.png" alt="TransPool" id="logo">
    <br/>
    <form method="GET" action="login">
        <h2>Enter a username:</h2>
        <input type="text" name="username"/>
        <input type="submit" value="Login"/>
        <br/>
        <h2>Enter user type:</h2>
        <input type="radio" id="rider" name="user_type" value="rider" checked>
        <label for="rider">Rider</label><br>
        <input type="radio" id="driver" name="user_type" value="driver">
        <label for="driver">Driver</label><br>
    </form>
    <% Object errorMessage = request.getAttribute(Constants.USERNAME_ERROR);%>
    <% if (errorMessage != null) {%>
    <span class="bg-danger" style="color:#ff4d4d;"><%=errorMessage%></span>
    <% } %>
    <% } else {%>
    <h1>Welcome back, <%=usernameFromSession%></h1>
    <a href="../maps/home.html">Click here to enter</a>
    <br/>
    <a href="login?logout=true" id="logout">logout</a>
    <% }%>
</div>
</body>
</html>--%>