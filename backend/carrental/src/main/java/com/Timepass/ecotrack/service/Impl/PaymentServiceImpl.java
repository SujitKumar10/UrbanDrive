package com.Timepass.ecotrack.service.Impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import com.razorpay.RazorpayException;
import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.BookingRequest;
import com.Timepass.ecotrack.dto.CreateOrderResponse;
import com.Timepass.ecotrack.dto.VerifyPaymentRequest;
import com.Timepass.ecotrack.service.BookingService;
import com.Timepass.ecotrack.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${Razorpay.key}")
    private String razorpayKey;

    @Value("${Razorpay.secret}")
    private String razorpaySecret;

    @Autowired
    private BookingService bookingService;

    @Override
    public CreateOrderResponse createOrder(Double amountInr) {
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKey, razorpaySecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (amountInr * 100)); // Razorpay expects paise
            orderRequest.put("currency", "INR");

            Order order = razorpay.orders.create(orderRequest);
            return new CreateOrderResponse(
                    order.get("id").toString(),
                    amountInr,
                    "INR",
                    razorpayKey
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage());
        }
    }

    @Override
    public BookingDto verifyPaymentAndCreateBooking(VerifyPaymentRequest request, String userEmail) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());

            Utils.verifyPaymentSignature(options, razorpaySecret);

            BookingRequest bookingRequest = new BookingRequest();
            bookingRequest.setCarId(request.getCarId());
            bookingRequest.setStartDate(request.getStartDate());
            bookingRequest.setEndDate(request.getEndDate());

            return bookingService.createBooking(bookingRequest, userEmail);
        } catch (RazorpayException e) {
            throw new RuntimeException("Payment verification failed: " + e.getMessage());
        }
    }
}
