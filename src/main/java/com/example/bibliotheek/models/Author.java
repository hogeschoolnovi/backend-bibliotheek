package com.example.bibliotheek.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String initials;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private Gender gender;

    public Author(String initials, String firstname, String lastname, LocalDate dateOfBirth, Gender gender) {
        this.initials = initials;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getInitials() {
        return initials;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
