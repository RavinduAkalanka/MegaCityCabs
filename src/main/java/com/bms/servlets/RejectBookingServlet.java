package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bms.controllers.RejectBookingController;

@WebServlet("/reject-booking-servlet")
public class RejectBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RejectBookingController rejectBookingController = new RejectBookingController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Ensure the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Object userIdObj = session.getAttribute("userId");
        int rejectedBy;

        if (userIdObj instanceof Integer) {
            rejectedBy = (Integer) userIdObj;
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(request.getParameter("bookingId"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Booking/manageBooking.jsp");
            return;
        }

        rejectBookingController.rejectBooking(bookingId, rejectedBy);

        response.sendRedirect(request.getContextPath() + "/booking-list-servlet");
    }
}
