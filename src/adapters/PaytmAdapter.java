package adapters;

import libraries.paytm.PaytmApi;
import libraries.paytm.PaytmPaymentResponse;
import models.Payment;
import models.PaymentStatus;

public class PaytmAdapter implements PaymentGatewayAdapter{

    private PaytmApi api =  new PaytmApi();

    public Payment makePayment(long billId, double amount){
        PaytmPaymentResponse response = api.makePayment(billId, amount);
        Payment paymentResponse  = new Payment();
        paymentResponse.setBillId(Long.parseLong(response.getOrderId()));
        paymentResponse.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentResponse.setTxnId(response.getTxnId());
        return paymentResponse;
    }
}

