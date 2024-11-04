package services;

import adapters.PaymentGatewayAdapter;
import exceptions.InvalidBillException;
import models.Bill;
import models.Payment;
import repositories.BillRepository;

import java.util.Optional;

public class PaymentServiceImpl implements PaymentService{
    private BillRepository billRepo;
    private PaymentGatewayAdapter paymentApi;

    public PaymentServiceImpl(BillRepository br, PaymentGatewayAdapter api ){
        billRepo = br;
        paymentApi  = api;
    }

    public Payment makePayment(long billId) throws InvalidBillException {
        Optional<Bill> currBill = billRepo.findById(billId);
        if (currBill.isEmpty()){
            throw new InvalidBillException("Invalid bill id");
        }
        return paymentApi.makePayment(billId, currBill.get().getTotalAmount());

    }
}
