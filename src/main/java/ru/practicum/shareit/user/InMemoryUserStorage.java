package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.model.AbstractStorage;

@Repository
public class InMemoryUserStorage extends AbstractStorage {

//    private final Map<Long, User> users = new HashMap<>();
//
//    @Override
//    public User addUser(User user) {
//        users.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public User updateUser(long userId, User user) {
//        users.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public void deleteUser(long userId) {
//        users.remove(userId);
//    }
//
//    @Override
//    public Optional<User> getUserById(long userId) {
//        return Optional.ofNullable(users.get(userId));
//    }
//
//    @Override
//    public Collection<User> getAllUsers() {
//        return users.values();
//    }
//
//    @Override
//    public Optional<User> getUserByEmail(String email) {
//        return Optional.of(users.values().stream()
//                .filter(user -> user.getEmail() == email)
//                .findFirst().get()
//        );
//    }
//
//    @Override
//    public int getSize() {
//        return users.size();
//    }

}
