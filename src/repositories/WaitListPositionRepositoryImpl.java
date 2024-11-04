package repositories;

import models.WaitListPosition;

import java.util.ArrayList;
import java.util.List;

public class WaitListPositionRepositoryImpl implements WaitListPositionRepository{
    private List<WaitListPosition> waitList = new ArrayList<>();
    public WaitListPosition save(WaitListPosition waitListPosition){
        waitList.add(waitListPosition);
        return waitListPosition;
    }

    public List<WaitListPosition> findAll(){
        return waitList;
    }

    public WaitListPosition delete(WaitListPosition waitListPosition){
        waitList.remove(waitListPosition);
        return waitListPosition;
    }
}
