/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package prj301demo.Controller;

import prj301demo.utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import prj301demo.Student.StudentDAO;
import prj301demo.Student.StudentDTO;
import prj301demo.Users.UserDTO;

/**
 *
 * @author DUNGHUYNH
 */
public class StudentController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        String sortCol = request.getParameter("colSort");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usersession") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        System.out.println("action: " + action);
        System.out.println("keywork: " + keyword);
        System.out.println("sortCol: " + sortCol);

        StudentDAO studentDAO = new StudentDAO();
        try {
            switch (action != null ? action : "list") {
                case "details":
                    handleDetailsAction(request, response, studentDAO);
                    break;
                case "edit":
                    handleEditAction(request, response, studentDAO);
                    break;
                case "create":
                    handleCreateAction(request, response);
                    break;
                case "update":
                    handleUpdateAction(request, response, studentDAO);
                    break;
                case "insert":
                    handleInsertAction(request, response, studentDAO);
                    break;
                case "delete":
                    handleDeleteAction(request, response, studentDAO, keyword, sortCol);
                    break;
                default:
                    handleListAction(request, response, studentDAO, keyword, sortCol);
                    break;
            }
        } catch (Exception ex) {
            log("Error processing request: " + ex.getMessage(), ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void handleDetailsAction(HttpServletRequest request, HttpServletResponse response, StudentDAO studentDAO)
            throws ServletException, IOException {
        Integer id = parseInteger(request.getParameter("id"));
        StudentDTO student = id != null ? studentDAO.load(id) : null;
        request.setAttribute("object", student);
        request.getRequestDispatcher("./studentdetails.jsp").forward(request, response);
    }

    private void handleEditAction(HttpServletRequest request, HttpServletResponse response, StudentDAO studentDAO)
            throws ServletException, IOException {
        Integer id = parseInteger(request.getParameter("id"));
        StudentDTO student = id != null ? studentDAO.load(id) : null;
        request.setAttribute("object", student);
        request.getRequestDispatcher("./studentedit.jsp").forward(request, response);
    }

    private void handleCreateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("student", new StudentDTO());
        request.getRequestDispatcher("./studentedit.jsp").forward(request, response);
    }

    private void handleUpdateAction(HttpServletRequest request, HttpServletResponse response, StudentDAO studentDAO)
            throws ServletException, IOException {

        Integer id = parseInteger(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        StudentDTO student = id != null ? studentDAO.load(id) : new StudentDTO();
        student.setFirstname(firstName);
        student.setLastname(lastName);
        student.setAge(age);
        studentDAO.update(student);

        request.setAttribute("student", student);
        request.getRequestDispatcher("./studentdetails.jsp").forward(request, response);
    }

    private void handleInsertAction(HttpServletRequest request, HttpServletResponse response, StudentDAO studentDAO)
            throws ServletException, IOException {

        int id = parseInteger(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        StudentDTO student = new StudentDTO();
        student.setId(id);
        student.setFirstname(firstName);
        student.setLastname(lastName);
        student.setAge(age);
        studentDAO.insert(student);

        request.setAttribute("object", student);
        response.sendRedirect("./StudentController");
    }

    private void handleDeleteAction(HttpServletRequest request, HttpServletResponse response, StudentDAO studentDAO,
            String keyword, String sortCol) throws ServletException, IOException {
        Long id = parseLong(request.getParameter("id"));
        if (id != null) {
            studentDAO.delete(id);
        }
        handleListAction(request, response, studentDAO, keyword, sortCol);
    }

    private void handleListAction(HttpServletRequest request, HttpServletResponse response, StudentDAO studentDAO,
            String keyword, String sortCol) throws ServletException, IOException {
        List<StudentDTO> list = studentDAO.list(keyword, sortCol);
        request.setAttribute("studentlist", list);
        request.getRequestDispatcher("./studentlist.jsp").forward(request, response);
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            log("Invalid integer format: " + value, ex);
            return null;
        }
    }

    private Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            log("Invalid long format: " + value, ex);
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
