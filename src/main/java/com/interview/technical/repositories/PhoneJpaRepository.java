package com.interview.technical.repositories;

import com.interview.technical.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneJpaRepository extends JpaRepository<Phone,Long> {
    Optional<Phone> findByNumberAndCityCodeAndCountryCode(String number, String cityCode, String countryCode);


}
