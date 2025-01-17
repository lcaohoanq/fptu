/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package prj301demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import prj301demo.utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class HomeServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet Dung</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");

            String keyword = request.getParameter("keyword");

            out.println("<form method=GET>"
                    + "<input name=keyword type=text value='" + keyword + "' />"
                    + "<input type=submit value=search />"
                    + "</form>");

            String sql = "SELECT * FROM Student WHERE lastname LIKE ? or firstname LIKE ?";
            if (keyword != null && !keyword.isEmpty()) {
                //table
                out.println("<h1>Student List </h1>");
                out.println("<table>");
                out.println("<tr><td>Id</td>");
                out.println("<td>First Name</td>");
                out.println("<td>Last Name</td>");
                out.println("<td>Age</td></tr>");
                try {
                    PreparedStatement pstm = DBUtils.getConnection().prepareStatement(sql);
                    pstm.setString(1, "%" + keyword + "%");
                    pstm.setString(2, "%" + keyword + "%");
                    ResultSet rs = pstm.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            out.println("<tr><td>" + rs.getString("id") + " </td>");
                            out.println("<td>" + rs.getString("firstname") + " </td>");
                            out.println("<td>" + rs.getString("lastname") + " </td>");
                            out.println("<td>" + rs.getString("age") + " </td></tr>");
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                out.println("</table>");
            }
            out.println("</body>");
            out.println("</html>");

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
