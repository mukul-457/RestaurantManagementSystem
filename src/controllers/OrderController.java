package controllers;


import dtos.GenerateBillRequestDto;
import dtos.GenerateBillResponseDto;
import dtos.ResponseStatus;
import exceptions.CustomerSessionNotFound;
import models.Bill;
import services.OrderService;

public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public GenerateBillResponseDto generateBill(GenerateBillRequestDto requestDto){
        GenerateBillResponseDto responseDto = new GenerateBillResponseDto();
        try{
            Bill generatedBill = orderService.generateBill(requestDto.getUserId());
            responseDto.setBill(generatedBill);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch(CustomerSessionNotFound e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return  responseDto;
    }


}