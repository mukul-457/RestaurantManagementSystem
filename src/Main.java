import controllers.MenuController;
import controllers.OrderController;
import controllers.RevenueController;
import dtos.*;
import exceptions.UserNotFoundException;
import models.*;
import repositories.*;
import services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        MenuRepository menuRepository = new MenuRepositoryImpl();
//        addFewMenuItems(menuRepository);
//        UserRepository  userRepository = new UserRepositoryImpl();
//        MenuService menuService = new MenuServiceImpl(menuRepository, userRepository);
//        MenuController menuController = new MenuController(menuService);
//        GetMenuItemsRequestDto requestDto = new GetMenuItemsRequestDto();
//        requestDto.setDietaryRequirement(null);
//        GetMenuItemsResponseDto getMenuItemsResponseDto = menuController.getMenuItems(requestDto);
//        System.out.println(getMenuItemsResponseDto.getResponseStatus());
//        for(MenuItem item : getMenuItemsResponseDto.getMenuItems()){
//            System.out.println(item.getName());
//        }

        //Test Order Placement

        MenuItemRepository menuItemRepository = new MenuItemRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        CustomerSessionRepository csr = new CustomerSessionRepositoryImpl();
        OrderRepository or = new OrderRepositoryImpl();
        User user = setupUser(userRepository);
        List<MenuItem> menuItems = setupMenuItems(menuItemRepository);
        PlaceOrderRequestDto requestDto = new PlaceOrderRequestDto();
        requestDto.setUserId(user.getId());
        Map<Long, Integer> orderedItems = new HashMap<>();
        orderedItems.put(menuItems.get(0).getId(), 2);
        orderedItems.put(menuItems.get(1).getId(), 1);
        OrderService orderService = new OrderServiceImpl(userRepository, csr, or, menuItemRepository);
        OrderController orderController = new OrderController(orderService);
        requestDto.setOrderedItems(orderedItems);
        PlaceOrderResponseDto placeOrderResponseDto = orderController.placeOrder(requestDto);
        Order order  = placeOrderResponseDto.getOrder();
    }


    private static User setupUser(UserRepository userRepository){
        User user = new User();
        user.setName("Test User");
        user.setPhone("1234567890");
        user.setUserType(UserType.CUSTOMER);
        return userRepository.save(user);
    }
    private static List<MenuItem> setupMenuItems(MenuItemRepository menuItemRepository){
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Paneer Tikka");
        menuItem.setPrice(200);
        menuItem.setDietaryRequirement(DietaryRequirement.VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Paneer Tikka is a vegetarian dish from the Indian subcontinent made from paneer marinated in spices and grilled in a tandoor.");
        menuItems.add(menuItemRepository.add(menuItem));

        menuItem = new MenuItem();
        menuItem.setName("Chicken Tikka");
        menuItem.setPrice(300);
        menuItem.setDietaryRequirement(DietaryRequirement.NON_VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Chicken tikka is a chicken dish originating in the Indian subcontinent; the dish is popular in India, Bangladesh and Pakistan.");
        menuItems.add(menuItemRepository.add(menuItem));

        menuItem = new MenuItem();
        menuItem.setName("Chicken Tikka Masala");
        menuItem.setPrice(400);
        menuItem.setDietaryRequirement(DietaryRequirement.NON_VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Chicken tikka masala is a dish consisting of roasted marinated chicken chunks (chicken tikka) in spiced curry sauce.");
        menuItems.add(menuItemRepository.add(menuItem));

        menuItem = new MenuItem();
        menuItem.setName("Paneer Tandoori");
        menuItem.setPrice(250);
        menuItem.setDietaryRequirement(DietaryRequirement.VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Paneer tikka is an Indian dish made from chunks of paneer marinated in spices and grilled in a tandoor.");
        menuItems.add(menuItemRepository.add(menuItem));

        menuItem = new MenuItem();
        menuItem.setName("Paneer Tikka Masala");
        menuItem.setPrice(350);
        menuItem.setDietaryRequirement(DietaryRequirement.VEG);
        menuItem.setItemType(ItemType.REGULAR);
        menuItem.setDescription("Paneer tikka masala is an Indian dish of marinated paneer cheese served in a spiced gravy.");
        menuItems.add(menuItemRepository.add(menuItem));

        return menuItems;
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