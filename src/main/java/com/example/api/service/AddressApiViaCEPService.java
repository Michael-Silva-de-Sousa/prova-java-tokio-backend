package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.dto.EnderecoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddressApiViaCEPService {

    RestTemplate restTemplate = new RestTemplate();

    private static final String URI = "http://viacep.com.br/ws/{cep}/json/";

    public Address findAddressPerZipCode(String zipCode){
        Map<String, String> params = new HashMap<>();
        params.put("cep", zipCode);

        EnderecoDTO enderecoDTO = restTemplate.getForObject(URI, EnderecoDTO.class, params);

        return convertToAddress(enderecoDTO);
    }

    private Address convertToAddress(EnderecoDTO enderecoDTO) {
        return new Address(
                enderecoDTO.getCep(),
                enderecoDTO.getLogradouro(),
                enderecoDTO.getComplemento(),
                enderecoDTO.getBairro(),
                enderecoDTO.getLocalidade(),
                enderecoDTO.getUf());
    }
}
