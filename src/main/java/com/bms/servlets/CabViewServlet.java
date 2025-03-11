package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.CabController;
import com.bms.dto.CabDTO;

@WebServlet("/cab-view-servlet")
public class CabViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CabController cabController = new CabController();

    public CabViewServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        int cabId = Integer.parseInt(request.getParameter("cabId"));

        
        CabDTO cab = cabController.getCabById(cabId);

        if (cab != null) {
            
            request.setAttribute("cab", cab);

            
            request.getRequestDispatcher("/Cab/viewCab.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cab-servlet?alertType=danger&alertMessage=Cab not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}