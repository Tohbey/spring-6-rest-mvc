package com.example.spring_6_rest_mvc.listener;

import com.example.spring_6_rest_mvc.events.*;
import com.example.spring_6_rest_mvc.mappers.BeerMapper;
import com.example.spring_6_rest_mvc.model.BeerAudit;
import com.example.spring_6_rest_mvc.repositories.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerListener {

    private BeerMapper beerMapper;
    private BeerAuditRepository beerAuditRepository;

    @Async
    @EventListener
    public void listen(BeerEvent event) {
        System.out.println("I heard a beer was created ::: "+event.getBeer().getBeerName());

        System.out.println("Current Thread Name ::: "+Thread.currentThread().getName());
        System.out.println("Current Thread Id ::: "+Thread.currentThread().getId());

        BeerAudit beerAudit = beerMapper.beerToBeerAudit(event.getBeer());

        String eventType = null;

        switch (event) {
            case BeerCreatedEvent beerCreatedEvent -> eventType = "BEER_CREATED";
            case BeerPatchedEvent beerPatchedEvent -> eventType = "BEER_PATCHED";
            case BeerUpdatedEvent beerUpdatedEvent -> eventType = "BEER_UPDATED";
            case BeerDeletedEvent beerDeletedEvent -> eventType = "BEER_DELETED";
            default -> eventType = "UNKNOWN";
        }

        if(event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
        log.info("Saved beer audit ::: {}", savedBeerAudit.getAuditEventType());
        log.info("Event type audit ::: {}", eventType);
    }
}
