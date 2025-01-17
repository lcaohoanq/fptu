<%@page import="java.util.List"%>
<%@page import="models.WorkoutDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workout List</title>
    </head>
    <body>
        <%@ include file="/menu.jsp" %>
        <form action='' method=GET> 
            <input name=keyword type=text value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">
            <input type=submit value=Search >
        </form>
        <table>
            <tr><td>Id</td>
                <td><a href=?colSort=workoutname>Workout Name</a></td>
                <td>Duration in Minutes</td>
                <td><a href=?colSort=workoutdate>Date</a></td>
                <td>Action</td>
            </tr>
            <%
                List<WorkoutDTO> list = (List<WorkoutDTO>) request.getAttribute("workoutlist");
                for (WorkoutDTO workout : list) {
                    pageContext.setAttribute("workout", workout);
            %>
            <tr>
                <td> <a href="WorkoutController?action=details&id=${workout.workoutID}"> ${workout.workoutID}</td>
                <td>${workout.workoutName}</td>
                <td>${workout.durationInMinutes}</td>
                <td>${workout.workoutDate}</td>
                <td>
                    <form action="WorkoutController" method="GET">
                        <input name="id" type="hidden" value="${workout.workoutID}">
                        <input name="action" type="hidden" value="delete">
                        <input type="submit" value ="Delete">
                    </form>
                </td>
            </tr>
            <%
                }
            %>    

            <tr>
                <td>
                    <form action="WorkoutController" method="GET">
                        <input name="action" type="hidden" value="create">
                        <input type="submit" value ="Create">
                    </form>
                </td>
            </tr>

        </table>
    </body>
</html>
</body>
</html>
