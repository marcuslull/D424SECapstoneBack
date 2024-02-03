package com.mybackyard.backend.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    long userId;
    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime updated;
    @NonNull
    String first;
    @NonNull
    String last;
    @NonNull
    String email;
    @NonNull
    String apiKey;
    @OneToMany(mappedBy = "user",
            cascade = jakarta.persistence.CascadeType.ALL,
            fetch = jakarta.persistence.FetchType.LAZY)
    List<Yard> yards;

    public User() {
    }

    public User(@NonNull String first, @NonNull String last, @NonNull String email, @NonNull String password) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.apiKey = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
    public String getFirst() {
        return first;
    }

    public void setFirst(@Nonnull String first) {
        this.first = first;
    }

    @Nonnull
    public String getLast() {
        return last;
    }

    public void setLast(@Nonnull String last) {
        this.last = last;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull String email) {
        this.email = email;
    }

    @Nonnull
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(@Nonnull String password) {
        this.apiKey = password;
    }

    public List<Yard> getYards() {
        return yards;
    }

    public void setYards(List<Yard> yards) {
        this.yards = yards;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", created=" + created +
                ", updated=" + updated +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
