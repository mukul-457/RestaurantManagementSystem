package repositories;

import models.Bill;

import java.util.HashMap;
import java.util.Optional;

public class BillRepositoryImpl implements BillRepository{

    private HashMap<Long , Bill> billDatabase = new HashMap<>();

    public Bill save(Bill bill){
        billDatabase.put(bill.getId(), bill);
        return bill;
    }

    public Optional<Bill> findById(long billId){
        return Optional.ofNullable(billDatabase.get(billId));
    }

}
