package repositories;

import models.User;
import models.UserType;

import java.util.HashMap;
import java.util.Optional;

public class UserRepositoryImpl  implements UserRepository{
    private HashMap<Long,User> usertable  = new HashMap<>();

    public UserRepositoryImpl(){
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("test");
        testUser.setUserType(UserType.BILLING);
        usertable.put(testUser.getId(), testUser);
    }
    @Override
    public Optional<User> findById(long id) {
        if (usertable.containsKey(id)){
            return Optional.of(usertable.get(id));
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        usertable.put(user.getId(), user);
        return  user;
    }
}
