package com.mybackyard.backend.model;

import com.mybackyard.backend.model.enums.*;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Plant {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    long plantId;
    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime updated;
    @NonNull
    String name;
    @Enumerated(EnumType.STRING)
    HardinessZone hardinessZone;
    @Enumerated(EnumType.STRING)
    NativeAreaType nativeAreaType;
    @Enumerated(EnumType.STRING)
    PlantSubType plantSubType;
    @Enumerated(EnumType.STRING)
    SoilType soilType;
    @Enumerated(EnumType.STRING)
    SunExposure sunExposure;
    @Enumerated(EnumType.STRING)
    WateringFrequency wateringFrequency;
    @OneToMany(mappedBy = "plant",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Note> notes;
    @OneToMany(mappedBy = "plant",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Image> images;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "yardId")
    Yard yard;
    long userId;

    public Plant() {
    }

    public Plant(@NonNull String name, Yard yard) {
        this.name = name;
        this.yard = yard;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
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

    public NativeAreaType getNativeAreaType() {
        return nativeAreaType;
    }

    public void setNativeAreaType(NativeAreaType nativeAreaType) {
        this.nativeAreaType = nativeAreaType;
    }

    public PlantSubType getPlantSubType() {
        return plantSubType;
    }

    public void setPlantSubType(PlantSubType plantSubType) {
        this.plantSubType = plantSubType;
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

    public WateringFrequency getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(WateringFrequency wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Yard getYard() {
        return yard;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "plantId=" + plantId +
                ", created=" + created +
                ", updated=" + updated +
                ", name='" + name + '\'' +
                ", hardinessZone=" + hardinessZone +
                ", nativeAreaType=" + nativeAreaType +
                ", plantSubType=" + plantSubType +
                ", soilType=" + soilType +
                ", sunExposure=" + sunExposure +
                ", wateringFrequency=" + wateringFrequency +
                '}';
    }
}
