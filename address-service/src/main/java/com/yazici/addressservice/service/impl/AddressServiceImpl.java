package com.yazici.addressservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yazici.addressservice.model.Address;
import com.yazici.addressservice.repository.AddressRepository;
import com.yazici.addressservice.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address getAddressByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode)
                .orElseThrow(() -> new RuntimeException("Address Not Found: " + postalCode));
    }
}
