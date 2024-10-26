package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Order;

public class OrderRepositoryImpl implements OrderRepository{
    private HashMap<Long, ArrayList<Order>> orderDb = new HashMap<>();

    public Order save(Order order){
        if (orderDb.containsKey(order.getCustomerSession().getId())){
            ArrayList<Order> sessionOrders  = orderDb.get(order.getCustomerSession().getId());
            sessionOrders.add(order);
        }else{
            ArrayList<Order> sessionOrder = new ArrayList<>();
            sessionOrder.add(order);
            orderDb.put(order.getCustomerSession().getId(), sessionOrder);
        }
        return order;
    }

    public List<Order> findOrdersByCustomerSession(long customerSessionId){
        return orderDb.get(customerSessionId);
    }

}
