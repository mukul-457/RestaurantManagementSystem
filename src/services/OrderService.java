package services;


import exceptions.CustomerSessionNotFound;
import exceptions.InvalidMenuItem;
import exceptions.UserNotFoundException;
import models.Bill;
import models.Order;

import java.util.Map;

public interface OrderService {

    public Bill generateBill(long userId) throws CustomerSessionNotFound;


    public Order placeOrder(long userId, Map<Long,Integer> orderedItems) throws UserNotFoundException, InvalidMenuItem;
}