<%@page import="java.util.List"%>
<%@page import="prj301demo.Student.StudentDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student List</title>
    </head>
    <body>
        <%@ include file="/menu.jsp" %>
        <form action='' method=GET> 
            <input name=keyword type=text value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">
            <input type=submit value=Search >
        </form>
        <table>
            <tr><td>Id</td>
                <td><a href=?colSort=firstname>First Name</a></td>
                <td><a href=?colSort=lastname>Last Name</a></td>
                <td>Age</td></tr>
                <%
                    List<StudentDTO> list = (List<StudentDTO>) request.getAttribute("studentlist");
                    for (StudentDTO student : list) {
                        pageContext.setAttribute("student", student);
                %>
            <tr><td>
                    <a href="StudentController?action=details&id=${student.id}&firstname=${student.firstname}&lastname=${student.lastname}"> ${student.id}</td>
                <td>${student.firstname}</td>
                <td>${student.lastname}</td>
                <td>${student.age}</td>
                <td>
                    <form action="StudentController" method="GET">
                        <input name="id" type="hidden" value="${student.id}">
                        <input name="action" type="hidden" value="delete">
                        <input type="submit" value ="delete">
                    </form>
                </td>
            </tr>
            <%
                }
            %>    

            <tr>
                <td>
                    <form action="StudentController" method="GET">
                        <input name="action" type="hidden" value="create">
                        <input type="submit" value ="create">
                    </form>
                </td>
            </tr>

        </table>
    </body>
</html>
</body>
</html>
