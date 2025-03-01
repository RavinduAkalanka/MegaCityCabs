package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.CustomerViewCabController;
import com.bms.dto.CabDTO;

@WebServlet("/customer-view-cab-servelet")
public class CustomerViewCabServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerViewCabController customerViewCabController = new CustomerViewCabController();

    public CustomerViewCabServelet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 6; 
        int pageNumber = 1; 

        
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNumber = 1; 
            }
        }

        
        List<CabDTO> cabList = customerViewCabController.getAllAvailableCabs(pageNumber, pageSize);

        int totalPages = customerViewCabController.getTotalPages(pageSize);

        request.setAttribute("cabList", cabList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPages", totalPages);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/availableCab.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}