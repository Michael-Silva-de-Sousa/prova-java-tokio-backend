package com.example.api.dto;

import com.example.api.domain.Address;

import java.util.List;

public class CustomerAddressPerZipCodeDTO {
    private Long idCustomer;
    private List<String> zipCodes;

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public List<String> getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(List<String> zipCodes) {
        this.zipCodes = zipCodes;
    }
}
