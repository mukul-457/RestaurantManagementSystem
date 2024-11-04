package services;


import exceptions.InvalidBillException;
import models.Payment;

public interface PaymentService {

    Payment makePayment(long billId) throws InvalidBillException;
}