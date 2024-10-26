package services;

import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;
import models.AggregatedRevenue;

public interface RevenueService {
    public AggregatedRevenue calculateRevenue(long userId, String queryType) throws UnAuthorizedAccess, UserNotFoundException;
}