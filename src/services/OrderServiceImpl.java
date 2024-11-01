package services;

import exceptions.CustomerSessionNotFound;
import exceptions.InvalidMenuItem;
import exceptions.UserNotFoundException;
import models.*;
import repositories.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepo ;
    private UserRepository userRepo;
    private CustomerSessionRepository customerSessionRepo ;
    private MenuItemRepository menuItemRepo;
    public OrderServiceImpl(UserRepository ur ,  CustomerSessionRepository csr , OrderRepository or, MenuItemRepository mir){
        this.customerSessionRepo = csr;
        this.menuItemRepo = mir;
        this.userRepo = ur;
        this.orderRepo = or;
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

    public Order placeOrder(long userId, Map<Long,Integer> orderedItems) throws UserNotFoundException, InvalidMenuItem {
        Optional<User> UserOpt = userRepo.findById(userId);
        if (UserOpt.isEmpty()){
            throw new UserNotFoundException("User with id = " + userId +" not found");
        }
        User orderUser = UserOpt.get();

        CustomerSession currentSession;
        Optional<CustomerSession> activeSession = customerSessionRepo.findActiveCustomerSessionByUserId(userId);
        if (activeSession.isEmpty()){
            currentSession = new CustomerSession();
            currentSession.setUser(orderUser);
            currentSession.setCustomerSessionStatus(CustomerSessionStatus.ACTIVE);
            customerSessionRepo.save(currentSession);
        }else{
            currentSession = activeSession.get();
        }

        Map<MenuItem , Integer> itemsOfOrder = new HashMap<>();
        for (Long itemId : orderedItems.keySet() ){
            Optional<MenuItem> itemOpt  = menuItemRepo.findById(itemId);
            if (itemOpt.isEmpty()){
                throw  new InvalidMenuItem("Item with itemId= " + itemId + " is not found");
            }
            itemsOfOrder.put(itemOpt.get(), orderedItems.get(itemId));
        }

        Order newOder = new Order();
        newOder.setCustomerSession(currentSession);
        newOder.setOrderedItems(itemsOfOrder);
        orderRepo.save(newOder);

        return newOder;
    }
}
