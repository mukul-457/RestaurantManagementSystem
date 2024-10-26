package repositories;

import models.DailyRevenue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DailyRevenueRepositoryImpl implements DailyRevenueRepository{
    private HashMap<Long,  DailyRevenue> revenueTable = new HashMap<>();

    public DailyRevenueRepositoryImpl(){
        DailyRevenue todayRevenue = new DailyRevenue();
        todayRevenue.setId(3L);
        todayRevenue.setDate(new Date());
        todayRevenue.setRevenueFromFoodSales(600.0);
        todayRevenue.setTotalGst(50);
        todayRevenue.setTotalServiceCharge(100);

        DailyRevenue prvMonth = new DailyRevenue();
        prvMonth.setId(2L);
        prvMonth.setDate(new Date("2024/09/02"));
        prvMonth.setRevenueFromFoodSales(500);
        prvMonth.setTotalGst(40);
        prvMonth.setTotalServiceCharge(90);

        DailyRevenue prvFscl = new DailyRevenue();
        prvFscl.setId(1L);
        prvFscl.setDate(new Date("2023/07/01"));
        prvFscl.setRevenueFromFoodSales(400);
        prvFscl.setTotalGst(30);
        prvFscl.setTotalServiceCharge(80);

        revenueTable.put(prvFscl.getId() , prvFscl);
        revenueTable.put(prvMonth.getId(), prvMonth);
        revenueTable.put(todayRevenue.getId(), todayRevenue);
    }
    @Override
    public DailyRevenue save(DailyRevenue dailyRevenue) {
        revenueTable.put(dailyRevenue.getId(), dailyRevenue);
        return dailyRevenue;
    }

    @Override
    public List<DailyRevenue> getDailyRevenueBetweenDates(Date startDate, Date endDate) {
        System.out.println("getting revenue from " + startDate + " to " + endDate);
        List<DailyRevenue> revenueResults = new ArrayList<>();
        for (DailyRevenue dr : revenueTable.values()){
            if (! dr.getDate().before(startDate) && ! dr.getDate().after(endDate)){
                revenueResults.add(dr);
            }
        }
        return revenueResults;
    }
}
