import controllers.MenuController;
import controllers.RevenueController;
import dtos.CalculateRevenueRequestDto;
import dtos.CalculateRevenueResponseDto;
import dtos.GetMenuItemsRequestDto;
import dtos.GetMenuItemsResponseDto;
import exceptions.UserNotFoundException;
import models.DietaryRequirement;
import models.ItemType;
import models.MenuItem;
import repositories.DailyRevenueRepositoryImpl;
import repositories.MenuRepository;
import repositories.MenuRepositoryImpl;
import repositories.UserRepositoryImpl;
import services.MenuService;
import services.MenuServiceImpl;
import services.RevenueServiceImpl;

public class Main {
    public static void main(String[] args) {
//        UserRepositoryImpl userRepository = new UserRepositoryImpl();
//        DailyRevenueRepositoryImpl dailyRevenueRepository = new DailyRevenueRepositoryImpl();
//        RevenueServiceImpl service = new RevenueServiceImpl(userRepository, dailyRevenueRepository);
//        RevenueController controller = new RevenueController(service);
//        System.out.println("Hello world!");
//        CalculateRevenueRequestDto requestDto = new CalculateRevenueRequestDto();
//        requestDto.setUserId(1L);
//        requestDto.setRevenueQueryType("PREVIOUS_MONTH");
//        CalculateRevenueResponseDto res = controller.calculateRevenue(requestDto);
//        System.out.println(res.getResponseStatus());
//        System.out.println(res.getAggregatedRevenue().getRevenueFromFoodSales());
//        System.out.println(res.getAggregatedRevenue().getTotalGst());
//        System.out.println(res.getAggregatedRevenue().getTotalServiceCharge());

        //Test get Menu Items

        MenuRepository menuRepository = new MenuRepositoryImpl();
        addFewMenuItems(menuRepository);
        MenuService menuService = new MenuServiceImpl(menuRepository);
        MenuController menuController = new MenuController(menuService);
        GetMenuItemsRequestDto requestDto = new GetMenuItemsRequestDto();
        requestDto.setDietaryRequirement(null);
        GetMenuItemsResponseDto getMenuItemsResponseDto = menuController.getMenuItems(requestDto);
        System.out.println(getMenuItemsResponseDto.getResponseStatus());
        for(MenuItem item : getMenuItemsResponseDto.getMenuItems()){
            System.out.println(item.getName());
        }
    }

    private static void addFewMenuItems( MenuRepository menuRepository ) {


        MenuItem menuItem = new MenuItem();
        menuItem.setName("Paneer Tikka");
        menuItem.setPrice(200);
        menuItem.setDietaryRequirement(DietaryRequirement.VEG);
        menuItem.setItemType(ItemType.DAILY_SPECIAL);
        menuItem.setDescription("Paneer Tikka is a vegetarian dish from the Indian subcontinent made from paneer marinated in spices and grilled in a tandoor.");
        menuRepository.save(menuItem);

        menuItem = new MenuItem();
        menuItem.setName("Chicken Tikka");
        menuItem.setPrice(300);
        menuItem.setDietaryRequirement(DietaryRequirement.NON_VEG);
        menuItem.setItemType(ItemType.DAILY_SPECIAL);
        menuItem.setDescription("Chicken Tikka is a chicken dish originating in the Indian subcontinent; the dish is popular in India, Bangladesh and Pakistan.");
        menuRepository.save(menuItem);

        menuItem = new MenuItem();
        menuItem.setName("Chicken Biryani");
        menuItem.setPrice(300);
        menuItem.setDietaryRequirement(DietaryRequirement.NON_VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Biryani, also known as biriyani, biriani, birani or briyani, is a mixed rice dish originating among the Muslims of the Indian subcontinent.");
        menuRepository.save(menuItem);


        menuItem = new MenuItem();
        menuItem.setName("Veg Biryani");
        menuItem.setPrice(300);
        menuItem.setDietaryRequirement(DietaryRequirement.VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Biryani, also known as biriyani, biriani, birani or briyani, is a mixed rice dish originating among the Muslims of the Indian subcontinent.");
        menuItem = menuRepository.save(menuItem);

        menuItem = new MenuItem();
        menuItem.setName("Vegan Tikka Masala");
        menuItem.setPrice(200);
        menuItem.setDietaryRequirement(DietaryRequirement.VEGAN);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Vegan Tikka Masala is a vegan dish of roasted vegetables in a creamy sauce.");
        menuRepository.save(menuItem);

        menuItem = new MenuItem();
        menuItem.setName("Vegan Biryani");
        menuItem.setPrice(300);
        menuItem.setDietaryRequirement(DietaryRequirement.VEGAN);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Vegan Biryani is a vegan dish of roasted vegetables in a creamy sauce.");
        menuRepository.save(menuItem);
    }
}