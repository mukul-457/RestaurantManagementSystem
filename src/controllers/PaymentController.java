package controllers;


import dtos.MakePaymentRequestDto;
import dtos.MakePaymentResponseDto;
import dtos.ResponseStatus;
import exceptions.InvalidBillException;
import models.Payment;
import services.PaymentService;

public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public MakePaymentResponseDto makePayment(MakePaymentRequestDto makePaymentRequestDto) {
        MakePaymentResponseDto finalResponse  = new MakePaymentResponseDto();
        try{
            Payment response = paymentService.makePayment(makePaymentRequestDto.getBillId());
            finalResponse.setPaymentStatus(response.getPaymentStatus());
            finalResponse.setResponseStatus(ResponseStatus.SUCCESS);
            finalResponse.setTxnId(response.getTxnId());
        }catch(InvalidBillException e ){
            System.out.println(e.getMessage());
            finalResponse.setResponseStatus(ResponseStatus.FAILURE);
        }
        return finalResponse;
    }
}