package adapters;

import models.Payment;

public interface PaymentGatewayAdapter {

    Payment makePayment(long billId, double amount);
}