package com.interview.technical.models;

import jakarta.persistence.*;

@Entity
@Table(name="PHONES")
public class Phone {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id ;
    private String number ;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "country_code")

    private String countryCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user = user;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
