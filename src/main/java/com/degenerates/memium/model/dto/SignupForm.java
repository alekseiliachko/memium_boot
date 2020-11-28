package com.degenerates.memium.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SignupForm {

    String username;

    String password;

    String email;

    String name;

    String bio;

    String gender;

    Date dob;
}
