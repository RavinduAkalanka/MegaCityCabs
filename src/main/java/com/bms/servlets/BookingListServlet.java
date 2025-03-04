package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.BookingController;
import com.bms.dto.BookingDTO;

@WebServlet("/booking-list-servlet")
public class BookingListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BookingController bookingController = new BookingController();

    public BookingListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 7; 
        int pageNumber = 1; 

       
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNumber = 1; 
            }
        }

        List<BookingDTO> bookingList = bookingController.getAllBookings(pageNumber, pageSize);
        int totalPages = bookingController.getTotalPages(pageSize);

        
        request.setAttribute("bookingList", bookingList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPages", totalPages);

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Booking/bookingList.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}