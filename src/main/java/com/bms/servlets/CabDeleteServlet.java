package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.CabController;

@WebServlet("/cab-delete-servlet")
public class CabDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CabController cabController = new CabController();

    public CabDeleteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the cabId from the request
        int cabId = Integer.parseInt(request.getParameter("cabId"));

        // Call the controller to delete the cab
        boolean isDeleted = cabController.deleteCab(cabId);

        // Redirect to the cab-servlet with a success or error message
        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/cab-servlet?alertType=success&alertMessage=Cab deleted successfully!");
        } else {
            response.sendRedirect(request.getContextPath() + "/cab-servlet?alertType=danger&alertMessage=Failed to delete cab.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}