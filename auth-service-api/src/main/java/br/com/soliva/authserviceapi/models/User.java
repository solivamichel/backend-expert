package br.com.soliva.authserviceapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.enums.ProfileEnum;

import java.io.Serializable;

import java.util.Set;

@Getter
@AllArgsConstructor
public class User implements Serializable{

    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}