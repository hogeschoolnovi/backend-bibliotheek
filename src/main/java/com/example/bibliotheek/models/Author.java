package com.example.bibliotheek.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
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

    public Author(UUID uuid, String initials, String firstname, String lastname, LocalDate dateOfBirth, Gender gender) {
        this.uuid = uuid;
        this.initials = initials;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Author() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return getUuid().equals(author.getUuid()) && getInitials().equals(author.getInitials()) && getFirstname().equals(author.getFirstname()) && getLastname().equals(author.getLastname()) && getDateOfBirth().equals(author.getDateOfBirth()) && getGender() == author.getGender();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getInitials(), getFirstname(), getLastname(), getDateOfBirth(), getGender());
    }
}
