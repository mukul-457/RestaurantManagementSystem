package repositories;

import models.ItemType;
import models.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuItemRepositoryImpl implements MenuItemRepository{

    private Map<Long, MenuItem>  menuItemDb = new HashMap<>();
    private Long itemId = 0L;
    public MenuItem add(MenuItem menuItem){
        menuItemDb.put(itemId, menuItem);
        menuItem.setId(itemId);
        itemId++;
        return menuItem;
    }

    public Optional<MenuItem> findById(long id){
        if (menuItemDb.containsKey(id)){
            return Optional.of(menuItemDb.get(id));
        }
        return Optional.empty();
    }

}
