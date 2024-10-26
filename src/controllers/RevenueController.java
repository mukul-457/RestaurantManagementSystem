package controllers;

import dtos.CalculateRevenueRequestDto;
import dtos.CalculateRevenueResponseDto;
import dtos.ResponseStatus;
import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;
import models.AggregatedRevenue;
import services.RevenueService;

public class RevenueController {

    private RevenueService revenueService;

    public RevenueController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    public CalculateRevenueResponseDto calculateRevenue(CalculateRevenueRequestDto requestDto){
        CalculateRevenueResponseDto responseDto = new CalculateRevenueResponseDto();
        try{
            AggregatedRevenue aggregatedRevenue  = revenueService.calculateRevenue(requestDto.getUserId(), requestDto.getRevenueQueryType());
            responseDto.setAggregatedRevenue(aggregatedRevenue);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (UserNotFoundException | UnAuthorizedAccess e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;

    }
}