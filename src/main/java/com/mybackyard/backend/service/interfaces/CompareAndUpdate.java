package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.*;

public interface CompareAndUpdate {
    Animal updateAnimal(Animal baseAnimal, Animal newAnimal) throws IllegalArgumentException, IllegalAccessException;

    Image updateImage(Image baseImage, Image image) throws IllegalArgumentException;

    Note updateNote(Note baseNote, Note note) throws IllegalArgumentException;

    Plant updatePlant(Plant basePlant, Plant plant)throws IllegalArgumentException, IllegalAccessException;

    User updateUser(User baseUser, User user) throws IllegalArgumentException, IllegalAccessException;

    Yard updateYard(Yard baseYard, Yard yard) throws IllegalArgumentException, IllegalAccessException;
}
