package com.example.spring_6_rest_mvc.events;

import com.example.spring_6_rest_mvc.model.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BeerUpdatedEvent implements BeerEvent {
    private Beer beer;

    private Authentication authentication;
}