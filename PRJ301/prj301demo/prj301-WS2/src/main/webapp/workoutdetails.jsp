<%@page import="models.WorkoutDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workout Details</title>
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />

        <h1>Workout Details </h1>         
        <p> Login user: ${sessionScope.usersession.username}</p>

        <% String msg =  (String) request.getAttribute("msg"); %>
        <% if (msg != null) { %>
        <h3 style="color: red"> <%= msg %> </h3>
        <% } %>

        <form action="WorkoutController">
            <table>

                <tr>
                    <td>Id</td>
                    <td><input name="id" value="${requestScope.object.workoutID}" readonly=""></td>
                </tr>
                <tr>
                    <td>Workout Name</td>
                    <td><input name="name" value="${requestScope.object.workoutName }" ></td>
                </tr>
                <tr>
                    <td>Duration in Minutes</td>
                    <td><input name="duration" value="${requestScope.object.durationInMinutes}" ></td>
                </tr>
                <tr>
                    <td>Date</td>
                    <td><input name="date" value="${requestScope.object.workoutDate}" ></td>
                </tr>
                <tr>
                    <td>
                        <input type=hidden name="action" value="update">
                        <input type=submit value="Save"></form>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
