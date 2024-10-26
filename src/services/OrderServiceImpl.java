package services;

import exceptions.CustomerSessionNotFound;
import models.*;
import repositories.CustomerSessionRepositoryImpl;
import repositories.OrderRepositoryImpl;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService{
    private OrderRepositoryImpl orderRepo ;
    private CustomerSessionRepositoryImpl customerSessionRepo ;

    public OrderServiceImpl(OrderRepositoryImpl or , CustomerSessionRepositoryImpl cr){
        this.orderRepo = or;
        this.customerSessionRepo = cr;
    }

    public Bill generateBill(long userId) throws CustomerSessionNotFound {
        Optional<CustomerSession> activeUserSession = this.customerSessionRepo.findActiveCustomerSessionByUserId(userId);
        if (activeUserSession.isEmpty()){
            throw new CustomerSessionNotFound("No active session for user");
        }
        List<Order> currentOrders = this.orderRepo.findOrdersByCustomerSession(activeUserSession.get().getId());
        Bill  currentBill = new Bill();
        HashMap<MenuItem, Integer> allItems = new HashMap<>();

        double totalAmount = 0;
        for (Order order :  currentOrders){
            Map<MenuItem, Integer> orderedItems = order.getOrderedItems();
            for (MenuItem item : orderedItems.keySet()){
                totalAmount += item.getPrice() * orderedItems.get(item);
                allItems.put(item, orderedItems.get(item));
            }
        }
        double serviceCharge = totalAmount * 0.1;
        double GST = totalAmount * 0.05 ;
        currentBill.setTotalAmount(totalAmount+serviceCharge+GST);
        currentBill.setGst(GST);
        currentBill.setServiceCharge(serviceCharge);
        currentBill.setOrderedItems(allItems);
        activeUserSession.get().setCustomerSessionStatus(CustomerSessionStatus.ENDED);
        return currentBill;
    }

}
