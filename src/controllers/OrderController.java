package controllers;


import dtos.*;
import exceptions.CustomerSessionNotFound;
import models.Bill;
import models.Order;
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

    public PlaceOrderResponseDto placeOrder(PlaceOrderRequestDto requestDto){
        PlaceOrderResponseDto responseDto = new PlaceOrderResponseDto();
        try {
            Order order = orderService.placeOrder(requestDto.getUserId(), requestDto.getOrderedItems());
            responseDto.setOrder(order);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }

}