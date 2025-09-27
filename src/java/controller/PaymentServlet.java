package controller;

import com.google.gson.JsonObject;
import util.PayHereUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/api/payhere/hash")
public class PaymentServlet extends HttpServlet {

    private final String MERCHANT_ID = "";
    private final String MERCHANT_SECRET = "";
    private final String CURRENCY = "LKR";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderId = "ORDER_" + System.currentTimeMillis();
        String amount = "100.00"; // Or parse from request

        String hash = PayHereUtil.generatePayHereHash(MERCHANT_ID, orderId, amount, CURRENCY, MERCHANT_SECRET);

JsonObject paymentResponse = new JsonObject();
paymentResponse.addProperty("sandbox", true);
paymentResponse.addProperty("merchant_id", MERCHANT_ID);
paymentResponse.addProperty("return_url", "http://localhost:8080/Payhere/success.html");
paymentResponse.addProperty("cancel_url", "http://localhost:8080/Payhere/cancel.html");
paymentResponse.addProperty("notify_url", "http://localhost:8080/Payhere/notify");
paymentResponse.addProperty("order_id", orderId);
paymentResponse.addProperty("items", "Test Product");
paymentResponse.addProperty("amount", amount); // must be string like "100.00"
paymentResponse.addProperty("currency", CURRENCY);
paymentResponse.addProperty("hash", hash);

// Customer details (mandatory)
paymentResponse.addProperty("first_name", "John");
paymentResponse.addProperty("last_name", "Doe");
paymentResponse.addProperty("email", "john@example.com");
paymentResponse.addProperty("phone", "0712345678");
paymentResponse.addProperty("address", "No.123, Main Street");
paymentResponse.addProperty("city", "Colombo");
paymentResponse.addProperty("country", "Sri Lanka");


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(paymentResponse.toString());
    }
}
