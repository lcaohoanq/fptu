<%@page import="prj301demo.Student.StudentDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Details</title>
    </head>
    <body>
        <jsp:include page="/menu.jsp" flush="true" />

        <h1>Student Details </h1>         
        <p> Login user: ${sessionScope.usersession.username}</p>

        <form action="StudentController">
            <table>

                <tr>
                    <td>Id</td>
                    <td><input name="id" value="${requestScope.object.id}" readonly=""></td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td><input name="firstName" value="${requestScope.object.firstname }" ></td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td><input name="lastName" value="${requestScope.object.lastname}" ></td>
                </tr>
                <tr>
                    <td>Age</td>
                    <td><input name="age" value="${requestScope.object.age}" ></td>
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
