package adapters;


import libraries.razorpay.RazorpayApi;
import libraries.razorpay.RazorpayPaymentResponse;
import models.Payment;
import models.PaymentStatus;

public class RazorpayAdapter implements PaymentGatewayAdapter{

    private RazorpayApi api = new RazorpayApi();

    public Payment makePayment(long billId , double amount){
        RazorpayPaymentResponse response = api.processPayment(billId, amount);
        Payment paymentResponse  = new Payment();
        paymentResponse.setBillId(billId);
        paymentResponse.setTxnId(response.getTransactionId());
        paymentResponse.setPaymentStatus(PaymentStatus.SUCCESS);
        return paymentResponse;
    }
}

