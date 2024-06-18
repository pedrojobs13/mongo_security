package com.orbitaltech.mongo_login.integration.models.embedded;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}
