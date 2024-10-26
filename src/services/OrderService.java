package services;


import exceptions.CustomerSessionNotFound;
import models.Bill;

public interface OrderService {

    public Bill generateBill(long userId) throws CustomerSessionNotFound;
}