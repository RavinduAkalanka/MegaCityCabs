<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill Details</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
        .bill-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .bill-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .bill-header h2 {
            color: #333;
            font-weight: bold;
        }
        .bill-header p {
            color: #666;
            margin: 0;
        }
        .bill-details table {
            width: 100%;
            margin-bottom: 20px;
        }
        .bill-details th, .bill-details td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        .bill-details th {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #333;
        }
        .bill-total {
            text-align: right;
            font-size: 1.2em;
            font-weight: bold;
            margin-top: 20px;
        }
        .btn-print {
           margin-top: 20px;
           width: 100%;
           background-color: #FFBF00; 
           border-color: #FFBF00;
           color: #000; 
        }

       .btn-print:hover {
          background-color: #FFBF00; 
          border-color: #FFBF00;
          color: #000; 
         }
        .btn-back {
          margin-top: 10px;
          width: 100%;
        }
        @media print {
        .btn-print, .btn-back {
            display: none;
        }
       }
    </style>
</head>
<body>
    <div class="bill-container">
        <!-- Bill Header -->
        <div class="bill-header">
            <h2>Invoice</h2>
            <p>Thank you for choosing our service!</p>
        </div>

        <!-- Bill Details -->
        <div class="bill-details">
            <table>
                <tr>
                    <th>Bill ID</th>
                    <td>${bill.billId}</td>
                </tr>
                <tr>
                    <th>Booking ID</th>
                    <td>${bill.bookingId}</td>
                </tr>
                <tr>
                    <th>Travel Distance (KM)</th>
                    <td><fmt:formatNumber value="${bill.travelDistance}" type="number" minFractionDigits="2"/></td>
                </tr>
                <tr>
                    <th>Additional Charges</th>
                    <td>Rs. <fmt:formatNumber value="${bill.additionalCharges}" type="number" minFractionDigits="2"/></td>
                </tr>
                <tr>
                    <th>Driver Charge</th>
                    <td>Rs. <fmt:formatNumber value="${bill.driverCharge}" type="number" minFractionDigits="2"/></td>
                </tr>
                <tr>
                    <th>Discount (%)</th>
                    <td>${bill.discount}</td>
                </tr>
                <tr>
                    <th>Payment Method</th>
                    <td>${bill.paymentMethod}</td>
                </tr>
                <tr>
                    <th>Created At</th>
                    <td><fmt:formatDate value="${bill.createdAt}" pattern="dd MMM yyyy HH:mm:ss"/></td>
                </tr>
            </table>
        </div>

        <!-- Total Amount -->
        <div class="bill-total">
            <label>Total Amount:</label>
            <p>Rs. <fmt:formatNumber value="${bill.totalAmount}" type="number" minFractionDigits="2"/></p>
        </div>

        <!-- Print Button -->
        <button class="btn btn-print" onclick="window.print()">Print Bill</button>
        
        <!-- Back Button -->
        <a href="manage-bill-list-servlet" class="btn btn-secondary btn-back">Back</a>
    </div>

    <!-- Bootstrap 5 JS & dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>