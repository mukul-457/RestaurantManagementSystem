package controllers;

import dtos.*;
import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;
import models.MenuItem;
import services.MenuService;

import java.util.List;

public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    public GetMenuItemsResponseDto getMenuItems(GetMenuItemsRequestDto requestDto){
        GetMenuItemsResponseDto responseDto = new GetMenuItemsResponseDto();
        try{
            List<MenuItem> menu;
            if (requestDto.getDietaryRequirement() != null){
                menu = menuService.getMenuItems(requestDto.getDietaryRequirement());
            }else{
                menu = menuService.getMenuItems("");
            }

            responseDto.setMenuItems(menu);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch(RuntimeException e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
    public AddMenuItemResponseDto addMenuItem(AddMenuItemRequestDto requestDto){
        AddMenuItemResponseDto responseDto = new AddMenuItemResponseDto();
        try{
            MenuItem addedItem = menuService.addMenuItem(requestDto.getUserId(),
                    requestDto.getName() , requestDto.getPrice(),
                    requestDto.getDietaryRequirement(),
                    requestDto.getItemType(), requestDto.getDescription());
            responseDto.setMenuItem(addedItem);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        }catch(UserNotFoundException | UnAuthorizedAccess e ){
            responseDto.setStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
