package com.example.spring_6_rest_mvc.events;

import com.example.spring_6_rest_mvc.model.Beer;
import org.springframework.security.core.Authentication;

public interface BeerEvent {

    Beer getBeer();

    Authentication getAuthentication();
}
