package services;

import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;
import models.AggregatedRevenue;
import models.DailyRevenue;
import models.User;
import models.UserType;
import repositories.DailyRevenueRepository;
import repositories.DailyRevenueRepositoryImpl;
import repositories.UserRepository;
import repositories.UserRepositoryImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RevenueServiceImpl implements RevenueService{
    private UserRepository userRepositoryimpl ;
    private DailyRevenueRepository dailyRevenueRepositoryimpl;

    public RevenueServiceImpl(UserRepository userRepositoryimpl, DailyRevenueRepository dailyRevenueRepositoryimpl){
        this.userRepositoryimpl = userRepositoryimpl;
        this.dailyRevenueRepositoryimpl = dailyRevenueRepositoryimpl;
    }
    @Override
    public AggregatedRevenue calculateRevenue(long userId, String queryType) throws UnAuthorizedAccess, UserNotFoundException {

        verifyUser(userId);

        Date fromDate;
        Date toDate;
        Date currDate  = new Date();

        switch (queryType){
            case ("CURRENT_MONTH"):
                fromDate = getCurrentMonthStartDate(currDate);
                toDate = currDate;
                break;
            case ("PREVIOUS_MONTH"):
                fromDate = getPreviousMonthStartDate(currDate);
                toDate =  getPreviousMonthEndDate(fromDate);
                break;
            case("CURRENT_FY"):
                fromDate = getCurrentFinancialYearStartDate(currDate);
                toDate = currDate;
                break;
            case("PREVIOUS_FY"):
                fromDate = getPreviousFinancialYearStartDate(currDate);
                toDate = getPreviousFinancialYearEndDate(currDate);
                break;
            default:
                throw  new RuntimeException("Unsupported query type !");

        }

        List<DailyRevenue> revenuesInRange = dailyRevenueRepositoryimpl.getDailyRevenueBetweenDates(fromDate, toDate);

        AggregatedRevenue aggregatedRevenue = aggregate(revenuesInRange);
        aggregatedRevenue.setFromDate(fromDate);
        aggregatedRevenue.setToDate(toDate);

        return aggregatedRevenue;
    }

    private AggregatedRevenue aggregate(List<DailyRevenue> revenueList){
        AggregatedRevenue  aggregatedRevenue = new AggregatedRevenue();
        double revenueFromFoodSales =0;
        double totalGST = 0;
        double totalServiceCharge = 0;
        for (DailyRevenue dr: revenueList){
            revenueFromFoodSales += dr.getRevenueFromFoodSales();
            totalGST += dr.getTotalGst();
            totalServiceCharge += dr.getTotalServiceCharge();
        }
        aggregatedRevenue.setRevenueFromFoodSales(revenueFromFoodSales);
        aggregatedRevenue.setTotalGst(totalGST);
        aggregatedRevenue.setTotalServiceCharge(totalServiceCharge);

        aggregatedRevenue.setTotalRevenue(revenueFromFoodSales + totalGST + totalServiceCharge);
        return aggregatedRevenue;
    }
    private void verifyUser(long userId) throws UserNotFoundException, UnAuthorizedAccess {
        Optional<User> currentUser = userRepositoryimpl.findById(userId);
        if (currentUser.isEmpty()){
            throw new UserNotFoundException("User not found ");
        }
        User user = currentUser.get();
        if (user.getUserType() != UserType.BILLING){
            throw new UnAuthorizedAccess("User should have billing privileges to run revenue queries");
        }
    }

    private Date getPreviousMonthStartDate(Date currDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }



    private Date getPreviousMonthEndDate(Date startDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private Date getCurrentMonthStartDate(Date currDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        calendar.set(Calendar.DAY_OF_MONTH ,1);
        return calendar.getTime();
    }

    private  Date getPreviousFinancialYearStartDate(Date currDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        if (month < Calendar.APRIL) {
            year -= 1;
        }

        calendar.set(Calendar.YEAR, year - 1);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private   Date getPreviousFinancialYearEndDate(Date currDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        if (month < Calendar.APRIL) {
            year -= 1;
        }

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        return calendar.getTime();
    }



    private   Date getCurrentFinancialYearStartDate(Date currDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        if (month < Calendar.APRIL) {
            year -= 1;
        }

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

}
