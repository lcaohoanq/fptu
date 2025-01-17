<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workout Edit</title>
    </head>
    <body>

        <jsp:include page="/menu.jsp" flush="true" /> 
        <h1>Workout Edit </h1>

        <p>Login user: ${sessionScope.usersession.username}</p>

        <% String msg = (String) request.getAttribute("msg"); %>
        <% if (msg != null) {%>
        <h3 style="color: red"> <%= msg%> </h3>
        <% } %>

        <%
            String action = request.getParameter("action");
        %>

        <form action="WorkoutController" method="GET">
            <table>

                <%
                    if (action.equals("create")) {
                %>

                <tr><td>Id</td><td><input type="number" name="id" value="${requestScope.workout.workoutID}"></td></tr>
                        <%
                        } else {
                        %>
                <tr><td>Id</td><td><input name="id" value="${requestScope.workout.workoutID}"></td></tr>
                        <%
                            }
                        %>
                <tr><td>Workout Name</td><td><input name="name" value="${requestScope.workout.workoutName }"></td></tr>
                <tr><td>Duration in Minutes</td><td><input name="duration" value="${requestScope.workout.durationInMinutes }"></td></tr>		 
                <tr><td>Date</td><td><input name="date" value="${requestScope.workout.workoutDate}"></td></tr>		 

                <%
                    if (action.equals("create")) {
                %>
                <tr>
                    <td colspan="2">
                        <input type=hidden name="action" value="insert">
                        <input type=submit value="Create New">
                    </td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td colspan="2">
                        <input type=hidden name="action" value="update">
                        <input type=submit value="Save">
                    </td>
                </tr>
                <%
                    }
                %>

            </table>
        </form>


    </body>
</html>
