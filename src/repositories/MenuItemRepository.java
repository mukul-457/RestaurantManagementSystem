package repositories;


import models.MenuItem;

import java.util.Optional;

public interface MenuItemRepository {

    MenuItem add(MenuItem menuItem);

    Optional<MenuItem> findById(long id);
}
