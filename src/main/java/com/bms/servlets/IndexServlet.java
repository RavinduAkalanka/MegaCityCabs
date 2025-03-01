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

@WebServlet("/index-servlet")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerViewCabController customerViewCabController = new CustomerViewCabController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int limit = 3;
        List<CabDTO> limitedCabList = customerViewCabController.getLimitedCabs(limit);

        request.setAttribute("limitedCabList", limitedCabList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}