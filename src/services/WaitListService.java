package services;

import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;

public interface WaitListService {

    int addUserToWaitList(long userId) throws UserNotFoundException;

    int getWaitListPosition(long userId) throws UserNotFoundException;

    void updateWaitList(long adminUserId, int numberOfSpots) throws UserNotFoundException, UnAuthorizedAccess;

}
