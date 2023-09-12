package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.Responses.OrderResponse;
import com.example.PrintAppPOC.Entities.Orders;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private RazorpayClient client;
    private static final String secret_Id = "rzp_test_6dX0k3izLYey4o";
    private static final String secret_key = "wAJVZqvWEwWR7dkHaDun54qH";

    @PostMapping("/payOrder")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody Orders orders){
        try {
            client = new RazorpayClient(secret_Id,secret_key);
            Order order = createRazorPayOrder(orders.getOrderAmount());
            OrderResponse response = new OrderResponse();
            response.setSecretId(secret_Id);
            response.setRazorpayOrderId(order.get("id"));
            response.setApplicationFee(""+orders.getOrderAmount());
            response.setSecretKey(secret_key);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
    private Order createRazorPayOrder(BigInteger amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount.multiply(new BigInteger("100")));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);
        return client.orders.create(options);
    }

}
