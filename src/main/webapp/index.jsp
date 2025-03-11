<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            scroll-behavior: smooth;
            background-color: #f4f7fc;
            padding-top: 70px;
        }
        .navbar {
            background-color: black;
            padding: 15px;
            border-bottom: 3px solid #FFBF00;
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
        }
        .navbar-brand {
            color: #FFBF00 !important;
            font-size: 1.8rem;
            margin-left: 40px;
            font-weight: bold;
        }
        .nav-link {
            color: white !important;
            padding: 10px 15px;
            font-weight: 500;
            margin: 0 10px;
        }
        .nav-link:hover, .nav-link.active {
            color: #FFBF00 !important;
            font-weight: bold;
        }
        .explore-btn {
            background-color: #FFBF00;
            color: black;
            font-weight: bold;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-top: 2px;
            margin-right: 40px;
            transition: background-color 0.3s ease;
        }
        .explore-btn:hover {
            background-color: #e6a800;
            color: white;
        }
        .hero {
            min-height: 100vh;
            display: flex;
            align-items: center;
            background-color: #f8f9fa; /* Same as Cabs section */
            padding: 100px 0;
        }
        .hero-content {
            padding: 50px;
        }
        .hero-content h1 {
            font-size: 3.5rem;
            font-weight: bold;
            color: #333;
            margin-bottom: 20px;
        }
        .hero-content p {
            font-size: 1.25rem;
            color: #555;
            margin-bottom: 30px;
        }
        .hero-image {
            background: url('images/main1.jpg') no-repeat center center/cover;
            min-height: 500px;
            border-radius: 20px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }
        .about-section, .contact-section {
            padding: 100px 0;
            background-color: #ffffff; /* Same for About and Contact sections */
        }
        .cabs-section {
            padding: 100px 0;
            background-color: #f8f9fa; /* Same as Home section */
        }
        .about-section h2, .cabs-section h2, .contact-section h2 {
            font-size: 2.75rem;
            font-weight: bold;
            margin-bottom: 30px;
            color: black;
        }
        .about-section .lead, .cabs-section .lead, .contact-section .lead {
            font-size: 1.25rem;
            color: #555;
            max-width: 800px;
            margin: 0 auto 50px auto;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        }
        .card-title {
            font-size: 1.75rem;
            font-weight: bold;
            margin-bottom: 15px;
            color: black;
        }
        .card-text {
            font-size: 1.1rem;
            color: #555;
        }
        .custom-btn {
            background-color: #FFBF00 !important;
            border-color: #FFBF00 !important;
            color: black !important;
        }
        .view-all-link {
            font-size: 1.1rem;
            color: #FFBF00;
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s ease;
        }
        .view-all-link:hover {
            color: #e6a800;
            text-decoration: underline;
        }
        .contact-info {
            background-color: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }
        .contact-info h4 {
            font-size: 1.75rem;
            font-weight: bold;
            margin-bottom: 20px;
            color: black;
        }
        .contact-info ul {
            list-style: none;
            padding: 0;
        }
        .contact-info ul li {
            font-size: 1.1rem;
            color: #555;
            margin-bottom: 15px;
        }
        .contact-info ul li i {
            color: #FFBF00;
            margin-right: 10px;
        }
        .map {
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }
        .footer {
            background-color: black;
            color: white;
            text-align: center;
            padding: 30px;
            margin-top: 2px;
        }
        .footer a {
            color: #FFBF00;
            text-decoration: none;
            transition: color 0.3s ease;
        }
        .footer a:hover {
            color: #e6a800;
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Mega City Cabs</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="#home">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="#about">About Us</a></li>
                    <li class="nav-item"><a class="nav-link" href="#cabs">Cabs</a></li>
                    <li class="nav-item"><a class="nav-link" href="#contact">Contact</a></li>
                </ul>
            </div>
            <a href="customer-view-cab-servelet" class="explore-btn">Explore Cabs</a>
        </div>
    </nav>

    <!-- Hero Section -->
    <section id="home" class="container-fluid hero">
        <div class="row w-100">
            <!-- Left Side Content -->
            <div class="col-md-6 d-flex align-items-center">
                <div class="hero-content">
                    <h1>Welcome to Mega City Cabs</h1>
                    <p>Reliable, fast, and comfortable cab service in your city. Whether you're commuting to work, heading to the airport, or exploring the city, 
                    we’ve got you covered. With a fleet of well-maintained vehicles and professional drivers, we ensure a safe and enjoyable ride every time. 
                    Explore our services and book your ride today!</p>
                    <a href="customer-view-cab-servelet" class="explore-btn">Explore Cabs</a>
                </div>
            </div>
            <!-- Right Side Image -->
            <div class="col-md-6 hero-image"></div>
        </div>
    </section>

    <!-- About Us Section -->
    <section id="about" class="about-section">
        <div class="container">
            <div class="text-center">
                <h2>About Us</h2>
                <p class="lead">We are a premium cab service dedicated to providing safe, reliable, and comfortable rides across the city. With years of experience, we ensure every journey is smooth and enjoyable.</p>
            </div>

            <!-- Feature Cards -->
            <div class="row mt-5">
                <!-- Card 1: Reliable Service -->
                <div class="col-md-4 mb-4">
                    <div class="card h-100 text-center p-4">
                        <div class="card-body">
                            <i class="fas fa-shield-alt fa-3x mb-3" style="color: #FFBF00;"></i>
                            <h5 class="card-title">Reliable Service</h5>
                            <p class="card-text">We prioritize punctuality and reliability, ensuring you reach your destination on time, every time.</p>
                        </div>
                    </div>
                </div>

                <!-- Card 2: Professional Drivers -->
                <div class="col-md-4 mb-4">
                    <div class="card h-100 text-center p-4">
                        <div class="card-body">
                            <i class="fas fa-user-tie fa-3x mb-3" style="color: #FFBF00;"></i>
                            <h5 class="card-title">Professional Drivers</h5>
                            <p class="card-text">Our drivers are highly trained, licensed, and committed to providing a safe and pleasant ride.</p>
                        </div>
                    </div>
                </div>

                <!-- Card 3: Comfortable Rides -->
                <div class="col-md-4 mb-4">
                    <div class="card h-100 text-center p-4">
                        <div class="card-body">
                            <i class="fas fa-car fa-3x mb-3" style="color: #FFBF00;"></i>
                            <h5 class="card-title">Comfortable Rides</h5>
                            <p class="card-text">Our fleet of well-maintained vehicles ensures a comfortable and enjoyable journey for every passenger.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Our Cabs Section -->
    <section id="cabs" class="cabs-section">
        <div class="container">
            <div class="text-center">
                <h2>Our Cabs</h2>
                <p class="lead">Choose from our wide range of vehicles for your perfect ride experience.</p>
            </div>
            
            <!-- Cab Cards -->
            <div class="row mt-5">
                <c:forEach var="cab" items="${limitedCabList}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <img src="${cab.cabImgUrl}" class="card-img-top" alt="${cab.model}" style="width: 100%; height: 300px; object-fit: cover;">
                            <div class="card-body text-center">
                                <h5 class="card-title">${cab.model}</h5>
                                <p class="card-text">Price per KM: Rs.<fmt:formatNumber value="${cab.pricePerKM}" pattern="#.00" /></p>
                                <a href="booking-servlet?action=showBookingForm&cabId=${cab.cabId}" class="btn custom-btn">Book Now</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- View All Link -->
            <div class="text-center mt-4">
                <a href="customer-view-cab-servelet" class="view-all-link">View All Cabs</a>
            </div>
        </div>
    </section>

    <!-- Contact Section -->
    <section id="contact" class="contact-section">
        <div class="container">
            <div class="text-center">
                <h2>Contact Us</h2>
                <p class="lead">Have questions or need assistance? Reach out to us!</p>
            </div>

            <div class="row mt-5">
                <!-- Contact Information -->
                <div class="col-md-6">
                    <div class="contact-info">
                        <h4>Contact Information</h4>
                        <ul class="list-unstyled">
                            <li><i class="fas fa-map-marker-alt"></i> 123 Mega City, Dehiwala, Sri Lanka</li>
                            <li><i class="fas fa-phone"></i> +9470 237 5050</li>
                            <li><i class="fas fa-envelope"></i> contact@megacitycabs.com</li>
                        </ul>
                    </div>
                </div>

                <!-- Map -->
                <div class="col-md-6 mt-4 mt-md-0">
                    <div class="map">
                        <iframe
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3153.8354345093747!2d144.9537353153166!3d-37.816279742021665!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6ad642af0f11fd81%3A0xf577d6a32f4b4b1d!2sMelbourne%20VIC%2C%20Australia!5e0!3m2!1sen!2sus!4v1633033226787!5m2!1sen!2sus"
                            width="100%"
                            height="300"
                            style="border:0;">
                        </iframe>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2025 Mega City Cabs | <a href="login.jsp">Control Access</a></p>
        <p>Quick Access: <a href="#home">Home</a> | <a href="#about">About Us</a> | <a href="#cabs">Cabs</a> | <a href="#contact">Contact</a></p>
    </footer>

    <!-- JavaScript for Active Navigation Highlight on Scroll -->
    <script>
        document.addEventListener("scroll", function() {
            let sections = document.querySelectorAll("section");
            let navLinks = document.querySelectorAll(".nav-link");

            let scrollPosition = document.documentElement.scrollTop || document.body.scrollTop;

            sections.forEach((section, index) => {
                if (
                    scrollPosition >= section.offsetTop - 100 &&
                    scrollPosition < section.offsetTop + section.offsetHeight
                ) {
                    navLinks.forEach((navLink) => navLink.classList.remove("active"));
                    navLinks[index].classList.add("active");
                }
            });
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>