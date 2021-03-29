package com.degenerates.memium.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SignupForm {

    @ApiModelProperty(required = true)
    String username;

    @ApiModelProperty(required = true)
    String password;

    @ApiModelProperty(required = true)
    String email;

    @ApiModelProperty(required = true)
    String name;

    @ApiModelProperty(required = true)
    String bio;

    @ApiModelProperty(required = true)
    String gender;

    @ApiModelProperty(required = true)
    Date dob;
}
