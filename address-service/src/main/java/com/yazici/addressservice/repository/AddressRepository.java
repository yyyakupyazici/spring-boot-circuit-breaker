package com.yazici.addressservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yazici.addressservice.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByPostalCode(String postalCode);
}
