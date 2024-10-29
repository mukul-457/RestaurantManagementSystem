package services;

import exceptions.UnAuthorizedAccess;
import exceptions.UserNotFoundException;
import models.*;
import repositories.MenuRepository;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService {
    private MenuRepository menuRepo ;
    private UserRepository userRepo;

    public MenuServiceImpl(MenuRepository menuRepo, UserRepository userRepo){
        this.menuRepo = menuRepo;
        this.userRepo = userRepo;
    }

    public List<MenuItem> getMenuItems(String itemType){
        List<MenuItem> dietaryRequirementItems;

        switch (itemType){
            case "VEG":
                dietaryRequirementItems=  menuRepo.getByDietaryRequirement(DietaryRequirement.VEG);
                break;
            case "NON_VEG":
                dietaryRequirementItems = menuRepo.getByDietaryRequirement(DietaryRequirement.NON_VEG);
                break;
            case "VEGAN" :
                dietaryRequirementItems = menuRepo.getByDietaryRequirement(DietaryRequirement.VEGAN);
                break;
            default:
                dietaryRequirementItems = menuRepo.getAll();
        }
        return dietaryRequirementItems;
    }
    public MenuItem addMenuItem(long userId, String name, double price, String dietaryRequirement, String itemType, String description) throws UserNotFoundException, UnAuthorizedAccess{
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()){
            throw new UserNotFoundException("Unknown user");
        }
        User user = userOpt.get();
        if (user.getUserType() != UserType.ADMIN){
            throw new UnAuthorizedAccess("Only Admin can add Item to the Menu");
        }
        MenuItem newItem = new MenuItem();
        newItem.setName(name);
        newItem.setPrice(price);
        newItem.setDescription(description);
        switch (dietaryRequirement){
            case "VEG":
                newItem.setDietaryRequirement(DietaryRequirement.VEG);
                break;
            case "NON_VEG":
                newItem.setDietaryRequirement(DietaryRequirement.NON_VEG);
                break;
            case "VEGAN":
                newItem.setDietaryRequirement(DietaryRequirement.VEGAN);
                break;
            default:
                System.out.println("Dietary requirement is illegal");
        }
        if (itemType.equals("DAILY_SPECIAL") ){
            newItem.setItemType(ItemType.DAILY_SPECIAL);
        }else{
            newItem.setItemType(ItemType.REGULAR);
        }
        menuRepo.add(newItem);
        return  newItem;

    }
}
