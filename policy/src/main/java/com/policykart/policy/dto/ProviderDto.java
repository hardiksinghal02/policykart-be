package com.policykart.policy.dto;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;

@Builder
@Getter
public class ProviderDto {
    private String id;
    private String name;
    private String address;
    private String city;
    private String state;
    private Integer pinCode;
    private URL coverImageUrl;
}
