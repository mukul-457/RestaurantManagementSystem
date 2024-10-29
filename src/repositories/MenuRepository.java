package repositories;


import models.DietaryRequirement;
import models.MenuItem;

import java.util.List;

public interface MenuRepository {
    MenuItem add(MenuItem menuItem);
    List<MenuItem> getAll();

    List<MenuItem> getByDietaryRequirement(DietaryRequirement dietaryRequirement);

    MenuItem save(MenuItem menuItem);
}
