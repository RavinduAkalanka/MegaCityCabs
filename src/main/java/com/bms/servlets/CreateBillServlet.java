package com.bms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controllers.BillController;
import com.bms.controllers.BookingController;
import com.bms.dto.BillDTO;
import com.bms.dto.BookingDTO;
import com.bms.enums.PaymentMethod;


@WebServlet("/create-bill-servlet")
public class CreateBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	BookingController bookingController = new BookingController();
	BillController billController = new BillController();
    
    public CreateBillServlet() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        
        BookingDTO booking = bookingController.getBookingById(bookingId);

        if (booking != null) {
            request.setAttribute("booking", booking);

            request.getRequestDispatcher("/Bill/createBill.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/booking-servlet?alertType=danger&alertMessage=Booking not found.");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("userId") == null) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }

	    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
	    int cabId = Integer.parseInt(request.getParameter("cabId"));
	    int driverId = Integer.parseInt(request.getParameter("driverId"));
	    double travelDistance = Double.parseDouble(request.getParameter("travelDistance"));
	    double additionalCharges = Double.parseDouble(request.getParameter("additionalCharges"));
	    double driverCharge = Double.parseDouble(request.getParameter("driverCharge"));
	    double discount = Double.parseDouble(request.getParameter("discount"));
	    PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getParameter("paymentMethod").toUpperCase());

	    Integer createdBy = (Integer) session.getAttribute("userId");

	    BillDTO billDTO = new BillDTO(
	        bookingId,
	        cabId,
	        driverId,
	        travelDistance,
	        additionalCharges,
	        driverCharge,
	        discount,
	        paymentMethod,
	        createdBy
	    );

	    boolean isBillCreated = billController.createBill(billDTO);

	    if (isBillCreated) {
	        int billId = billDTO.getBillId();
	        response.sendRedirect(request.getContextPath() + "/get-bill-servlet?billId=" + billId);
	    } else {
	        response.sendRedirect(request.getContextPath() + "/createBill.jsp?error=true");
	    }
	}
}