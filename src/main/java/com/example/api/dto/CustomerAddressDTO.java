package com.example.api.dto;

import com.example.api.domain.Address;

import java.util.List;

public class CustomerAddressDTO {
    private Long idCustomer;
    private List<Address> addresses;

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
