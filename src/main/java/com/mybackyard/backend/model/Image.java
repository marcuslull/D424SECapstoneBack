package com.mybackyard.backend.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    long imageId;
    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime updated;
    @NonNull
    String location;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "yardId")
    Yard yard;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "plantId")
    Plant plant;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "animalId")
    Animal animal;
    long userId;

    public Image() {
    }

    public Image(@NonNull String location) {
        this.location = location;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Nonnull
    public String getLocation() {
        return location;
    }

    public void setLocation(@Nonnull String location) {
        this.location = location;
    }

    public Yard getYard() {
        return yard;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", created=" + created +
                ", updated=" + updated +
                ", location='" + location + '\'' +
                '}';
    }
}
