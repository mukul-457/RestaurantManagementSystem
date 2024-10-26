import controllers.RevenueController;
import dtos.CalculateRevenueRequestDto;
import dtos.CalculateRevenueResponseDto;
import exceptions.UserNotFoundException;
import repositories.DailyRevenueRepositoryImpl;
import repositories.UserRepositoryImpl;
import services.RevenueServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        DailyRevenueRepositoryImpl dailyRevenueRepository = new DailyRevenueRepositoryImpl();
        RevenueServiceImpl service = new RevenueServiceImpl(userRepository, dailyRevenueRepository);
        RevenueController controller = new RevenueController(service);
        System.out.println("Hello world!");
        CalculateRevenueRequestDto requestDto = new CalculateRevenueRequestDto();
        requestDto.setUserId(1L);
        requestDto.setRevenueQueryType("PREVIOUS_MONTH");
        CalculateRevenueResponseDto res = controller.calculateRevenue(requestDto);
        System.out.println(res.getResponseStatus());
        System.out.println(res.getAggregatedRevenue().getRevenueFromFoodSales());
        System.out.println(res.getAggregatedRevenue().getTotalGst());
        System.out.println(res.getAggregatedRevenue().getTotalServiceCharge());
    }
}