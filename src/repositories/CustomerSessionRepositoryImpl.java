package repositories;

import models.CustomerSession;

import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
public class CustomerSessionRepositoryImpl implements CustomerSessionRepository{
    private HashMap<Long, ArrayList<CustomerSession>> sessionDb = new HashMap<>();

    public CustomerSession save(CustomerSession customerSession){
        if (sessionDb.containsKey(customerSession.getUser().getId())){
            sessionDb.get(customerSession.getUser().getId()).add(customerSession);
        }else{
            sessionDb.put(customerSession.getUser().getId(), new ArrayList<>(Arrays.asList(customerSession)));
        }
        return customerSession;
    }

    public Optional<CustomerSession> findActiveCustomerSessionByUserId(long userId){
        if (!sessionDb.containsKey(userId)){
            return Optional.empty();
        }
        for (CustomerSession cs : sessionDb.get(userId)){
            if (cs.isActive()){
                return Optional.of(cs);
            }
        }
        return Optional.empty();
    }

}