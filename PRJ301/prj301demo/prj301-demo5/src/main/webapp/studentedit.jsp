<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Details</title>
    </head>
    <body>

        <jsp:include page="/menu.jsp" flush="true" /> 
        <h1>Student Edit </h1>

        <p>Login user: ${sessionScope.usersession.username}</p>

        <%
            String action = request.getParameter("action");
        %>

        <form action="StudentController" method="GET">
            <table>

                <%
                    if (action.equals("create")) {
                %>

                <tr><td>Id</td><td><input type="number" name="id" value="${requestScope.student.id}"></td></tr>
                        <%
                        } else {
                        %>
                <tr><td>Id</td><td><input name="id" value="${requestScope.student.id}"></td></tr>
                        <%
                            }
                        %>
                <tr><td>First name</td><td><input name="firstName" value="${requestScope.student.firstname }"></td></tr>
                <tr><td>Last name</td><td><input name="lastName" value="${requestScope.student.lastname }"></td></tr>		 
                <tr><td>Age</td><td><input name="age" value="${requestScope.student.age}"></td></tr>		 

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
