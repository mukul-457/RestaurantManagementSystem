package repositories;

import models.DietaryRequirement;
import models.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MenuRepositoryImpl implements MenuRepository{

    private HashMap<DietaryRequirement, List<MenuItem>> itemDb = new HashMap<>();

    public MenuItem add(MenuItem menuItem){
        if (itemDb.containsKey(menuItem.getDietaryRequirement())){
            itemDb.get(menuItem.getDietaryRequirement()).add(menuItem);
        }else{
            itemDb.put(menuItem.getDietaryRequirement(), new ArrayList<>(List.of(menuItem)));
        }
        return menuItem;
    }

    public List<MenuItem> getAll(){
        ArrayList<MenuItem> allType = new ArrayList<>();
        for(List<MenuItem> oneType : itemDb.values()){
            allType.addAll(oneType);
        }
        return allType;
    }

    public List<MenuItem> getByDietaryRequirement(DietaryRequirement dietaryRequirement){
        if (itemDb.containsKey(dietaryRequirement)){
            return itemDb.get(dietaryRequirement);
        }
        return null;
    }

    public MenuItem save(MenuItem menuItem){
        if (itemDb.containsKey(menuItem.getDietaryRequirement())){
            itemDb.get(menuItem.getDietaryRequirement()).add(menuItem);
        }else{
            itemDb.put(menuItem.getDietaryRequirement(), new ArrayList<>(List.of(menuItem)));
        }
        return menuItem;
    }

}