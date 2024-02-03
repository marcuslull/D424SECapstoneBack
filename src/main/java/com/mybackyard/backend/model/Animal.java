package com.mybackyard.backend.model;

import com.mybackyard.backend.model.enums.AnimalSubType;
import com.mybackyard.backend.model.enums.DietType;
import com.mybackyard.backend.model.enums.NativeAreaType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    long animalId;
    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime updated;
    @NonNull
    String name;
    @Enumerated(EnumType.STRING)
    AnimalSubType animalSubType;
    @Enumerated(EnumType.STRING)
    DietType dietType;
    @Enumerated(EnumType.STRING)
    NativeAreaType nativeAreaType;
    @OneToMany(mappedBy = "animal",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Note> notes;
    @OneToMany(mappedBy = "animal",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Image> images;
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "yardId")
    Yard yard;
    long userId;

    public Animal() {
    }

    public Animal(@NonNull String name) {
        this.name = name;
    }

    public long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(long animalId) {
        this.animalId = animalId;
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

    public AnimalSubType getAnimalSubType() {
        return animalSubType;
    }

    public void setAnimalSubType(AnimalSubType animalSubType) {
        this.animalSubType = animalSubType;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public NativeAreaType getNativeAreaType() {
        return nativeAreaType;
    }

    public void setNativeAreaType(NativeAreaType nativeAreaType) {
        this.nativeAreaType = nativeAreaType;
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
        return "Animal{" +
                "animalId=" + animalId +
                ", created=" + created +
                ", updated=" + updated +
                ", name='" + name + '\'' +
                ", animalSubType=" + animalSubType +
                ", dietType=" + dietType +
                ", nativeAreaType=" + nativeAreaType +
                '}';
    }
}
