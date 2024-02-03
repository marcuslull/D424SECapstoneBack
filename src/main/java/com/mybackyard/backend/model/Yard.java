package com.mybackyard.backend.model;

import com.mybackyard.backend.model.enums.HardinessZone;
import com.mybackyard.backend.model.enums.SoilType;
import com.mybackyard.backend.model.enums.SunExposure;
import com.mybackyard.backend.model.enums.YardSubType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Yard {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    long yardId;
    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime updated;
    @NonNull
    String name;
    @Enumerated(EnumType.STRING)
    HardinessZone hardinessZone;
    @Enumerated(EnumType.STRING)
    SoilType soilType;
    @Enumerated(EnumType.STRING)
    SunExposure sunExposure;
    @Enumerated(EnumType.STRING)
    YardSubType yardSubType;
    @OneToMany(mappedBy = "yard",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Note> notes;
    @OneToMany(mappedBy = "yard",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Animal> animals;
    @OneToMany(mappedBy = "yard",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Plant> plants;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NonNull
    User user;
    long YUserId;

    public Yard() {
    }

    public Yard(@NonNull String name, @NonNull User user) {
        this.name = name;
        this.user = user;
    }

    public long getYardId() {
        return yardId;
    }

    public void setYardId(long yardId) {
        this.yardId = yardId;
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
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public HardinessZone getHardinessZone() {
        return hardinessZone;
    }

    public void setHardinessZone(HardinessZone hardinessZone) {
        this.hardinessZone = hardinessZone;
    }

    public SoilType getSoilType() {
        return soilType;
    }

    public void setSoilType(SoilType soilType) {
        this.soilType = soilType;
    }

    public SunExposure getSunExposure() {
        return sunExposure;
    }

    public void setSunExposure(SunExposure sunExposure) {
        this.sunExposure = sunExposure;
    }

    public YardSubType getYardSubType() {
        return yardSubType;
    }

    public void setYardSubType(YardSubType yardSubType) {
        this.yardSubType = yardSubType;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    public void setUser(@Nonnull User user) {
        this.user = user;
    }

    public long getYUserId() {
        return YUserId;
    }

    public void setYUserId(long yardUserId) {
        this.YUserId = yardUserId;
    }

    @Override
    public String toString() {
        return "Yard{" +
                "YardId=" + yardId +
                ", created=" + created +
                ", updated=" + updated +
                ", name='" + name + '\'' +
                ", hardinessZone=" + hardinessZone +
                ", soilType=" + soilType +
                ", sunExposure=" + sunExposure +
                ", yardSubType=" + yardSubType +
                '}';
    }
}
