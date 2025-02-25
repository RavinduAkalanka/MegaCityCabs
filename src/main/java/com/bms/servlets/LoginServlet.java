package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controllers.UserController;
import com.bms.dto.UserDTO;
import com.bms.enums.Role;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserController userController = new UserController();

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserDTO userDTO = userController.loginUser(email, password);

            if (userDTO != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", userDTO);
                session.setAttribute("role", userDTO.getRole().toString().toUpperCase());

                if (Role.ADMIN.equals(userDTO.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp");
                } else if (Role.STAFF.equals(userDTO.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/staffDashboard.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid credentials");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}