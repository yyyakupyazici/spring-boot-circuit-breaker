package com.yazici.addressservice.service;

import com.yazici.addressservice.model.Address;

public interface AddressService {
    Address getAddressByPostalCode(String postalCode);
}
