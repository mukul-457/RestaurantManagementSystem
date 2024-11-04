package services;

import controllers.WaitListController;
import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;
import models.User;
import models.UserType;
import models.WaitListPosition;
import repositories.UserRepository;
import repositories.WaitListPositionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class WaitListServiceImpl implements WaitListService{
    private UserRepository userRepo ;
    private WaitListPositionRepository waitListRepo;

    public WaitListServiceImpl(UserRepository ur, WaitListPositionRepository wpr){
        this.userRepo = ur;
        this.waitListRepo = wpr;
    }

    public int addUserToWaitList(long userId) throws UserNotFoundException {
        User user  = verifyUser(userId);
        List<WaitListPosition> currWaitList = waitListRepo.findAll();
        int i = 1;
        for (WaitListPosition pos : currWaitList){
            if (pos.getUser() == user){
                return i;
            }
            i++;
        }
        WaitListPosition userWaitListPosition = new WaitListPosition();
        userWaitListPosition.setUser(user);
        userWaitListPosition.setInsertedAt(new Date());
        waitListRepo.save(userWaitListPosition);
        return i;
    }

    public int getWaitListPosition(long userId) throws UserNotFoundException{
        User user = verifyUser(userId);
        List<WaitListPosition> currWaitList = waitListRepo.findAll();
        int i = 1;
        for (WaitListPosition pos : currWaitList){
            if (pos.getUser() == user){
                return i;
            }
            i++;
        }
        return -1;
    }

    public void updateWaitList(long adminUserId, int numberOfSpots) throws UserNotFoundException, UnAuthorizedAccess{
        User user = verifyUser(adminUserId);
        if (user.getUserType() != UserType.ADMIN){
            throw new UnAuthorizedAccess("Only Admin can update the waitlist");
        }
        List<WaitListPosition> curWaitListPositions = waitListRepo.findAll();
        List<WaitListPosition> toBeDeleted = new ArrayList<>();
        for(WaitListPosition pos :  curWaitListPositions){
            if (numberOfSpots <=0 ){
                break;
            }
            toBeDeleted.add(pos);
            numberOfSpots--;
        }

        for (WaitListPosition pos : toBeDeleted){
            waitListRepo.delete(pos);
        }
    }

    private User verifyUser(long userId) throws UserNotFoundException{
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = userOpt.get();
        return user;
    }

}