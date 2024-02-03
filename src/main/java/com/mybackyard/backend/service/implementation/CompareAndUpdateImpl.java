package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.*;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class CompareAndUpdateImpl implements CompareAndUpdate {
    @Override
    public Animal updateAnimal(Animal baseAnimal, Animal newAnimal) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : newAnimal.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(newAnimal) != null && !(field.getName().contains("userId"))) field.set(baseAnimal, field.get(newAnimal));
        }
        return baseAnimal;
    }

    @Override
    public Image updateImage(Image baseImage, Image image) throws IllegalArgumentException{
        baseImage.setLocation(image.getLocation());
        return baseImage;
    }

    @Override
    public Note updateNote(Note baseNote, Note note) throws IllegalArgumentException{
        baseNote.setComment(note.getComment());
        return baseNote;
    }

    @Override
    public Plant updatePlant(Plant basePlant, Plant newPlant) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : newPlant.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(newPlant) != null && !(field.getName().contains("userId"))) field.set(basePlant, field.get(newPlant));
        }
        return basePlant;
    }

    @Override
    public User updateUser(User baseUser, User user) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : user.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(user) != null && !(field.getName().contains("userId"))) field.set(baseUser, field.get(user));
        }
        return baseUser;
    }

    @Override
    public Yard updateYard(Yard baseYard, Yard yard) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : yard.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(yard) != null && !(field.getName().contains("YUserId"))) field.set(baseYard, field.get(yard));
        }
        return baseYard;
    }
}
