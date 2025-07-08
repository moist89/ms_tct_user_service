package com.interview.technical.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String password ;
    private String token ;
    private LocalDateTime created;
    private LocalDateTime modified;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    private boolean active;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    @PrePersist
    public void init(){
        LocalDateTime now = LocalDateTime.now();
        this.created = now;
        this.modified = now;
        this.lastLogin = now;
        this.active = true;
    }
    @PreUpdate
    public void updateLastModification(){
        this.modified=LocalDateTime.now();
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", lastLogin=" + lastLogin +
                ", active=" + active +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }
}
