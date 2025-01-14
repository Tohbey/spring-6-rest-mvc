package com.example.spring_6_rest_mvc.controller;

import com.example.spring_6_rest_mvc.dto.BeerDTO;
import com.example.spring_6_rest_mvc.exception.NotFoundException;
import com.example.spring_6_rest_mvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.BEER_PATH)
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_BY_ID =  "/{beerId}";

    private final BeerService beerService;

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BeerDTO> handlePost(@RequestBody BeerDTO beer){

        BeerDTO savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/"+savedBeer.getId().toString());

        return new ResponseEntity<BeerDTO>(beer, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = BEER_BY_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){
        if(beerService.updateBeer(id, beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = BEER_BY_ID)
    public ResponseEntity patchBeerById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){
        beerService.patchBeerById(id, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @RequestMapping(value = BEER_BY_ID, method = RequestMethod.GET)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get BeerDTO by Id - in controller");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(value = BEER_BY_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){
        if(!beerService.deleteBeer(beerId)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    this will only be handled in this controller class not suitable for a big project
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException(){
//
//        System.out.println("In exception Handler");
//        return ResponseEntity.notFound().build();
//    }
}
