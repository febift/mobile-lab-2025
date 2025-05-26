package com.example.tp6;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static List<UserEntity> toEntityList(List<User> users) {
        List<UserEntity> entities = new ArrayList<>();
        for (User user : users) {
            UserEntity entity = new UserEntity();
            entity.setId(user.getId());
            entity.setName(user.getName());
            entity.setSpecies(user.getSpecies());
            entity.setImage(user.getImage());
            entities.add(entity);
        }
        return entities;
    }

    public static List<User> fromEntityList(List<UserEntity> entities) {
        List<User> users = new ArrayList<>();
        for (UserEntity entity : entities) {
            User user = new User();
            user.setId(entity.getId());
            user.setName(entity.getName());
            user.setSpecies(entity.getSpecies());
            user.setImage(entity.getImage());
            users.add(user);
        }
        return users;
    }
}
