package com.degenerates.memium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {

//    @Value()
//    String keyWord;

//    @Value()
//    String salt;

    public String hash(String string) {

        //todo

        return "ha^she-sHed_pa$$wooR5";
    }
}
