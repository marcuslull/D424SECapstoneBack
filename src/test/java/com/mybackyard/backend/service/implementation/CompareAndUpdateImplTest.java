package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.*;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CompareAndUpdateImplTest {

    CompareAndUpdate compareAndUpdate;

    @BeforeEach
    void setUp() {
        compareAndUpdate = new CompareAndUpdateImpl();
    }

    @Test
    void updateAnimal() throws IllegalAccessException {
        Animal baseAnimal = new Animal("name");
        Animal patchAnimal = new Animal("updated name");
        Animal updateAnimal = new Animal("updated name");

        assertEquals(updateAnimal.getName(), compareAndUpdate.updateAnimal(baseAnimal, patchAnimal).getName());
    }

    @Test
    void updateImage() {
        Image baseImage= new Image("location");
        Image patchImage = new Image("updated location");
        Image updateImage = new Image("updated location");

        assertEquals(updateImage.getLocation(), compareAndUpdate.updateImage(baseImage, patchImage).getLocation());
    }

    @Test
    void updateNote() {
        Note baseNote= new Note("comment");
        Note patchNote = new Note("updated comment");
        Note updateNote= new Note("updated comment");

        assertEquals(updateNote.getComment(), compareAndUpdate.updateNote(baseNote, patchNote).getComment());
    }

    @Test
    void updatePlant() throws IllegalAccessException {
        Plant basePlant= new Plant();
        basePlant.setName("Name");
        Plant patchPlant = new Plant();
        patchPlant.setName("new Name");
        Plant updatePlant = new Plant();
        updatePlant.setName("new Name");

        assertEquals(updatePlant.getName(), compareAndUpdate.updatePlant(basePlant, patchPlant).getName());
    }

    @Test
    void updateUser() throws IllegalAccessException {
        User baseUser = new User();
        baseUser.setFirst("Name");
        User patchUser = new User();
        patchUser.setFirst("new Name");
        User updateUser = new User();
        updateUser.setFirst("new Name");

        assertEquals(updateUser.getFirst(), compareAndUpdate.updateUser(baseUser, patchUser).getFirst());
    }

    @Test
    void updateYard() throws IllegalAccessException {
        Yard baseYard= new Yard();
        baseYard.setName("Name");
        Yard patchYard = new Yard();
        patchYard.setName("new Name");
        Yard updateYard = new Yard();
        updateYard.setName("new Name");

        assertEquals(updateYard.getName(), compareAndUpdate.updateYard(baseYard, patchYard).getName());
    }
}