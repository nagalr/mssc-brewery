package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> handlePost(@RequestBody BeerDto beerDto) {

        BeerDto savedDto = beerService.saveBeer(beerDto);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");

        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        Date d = new Date();

        headers.setDate("Current GMT Date and Time", d. getTime());

        return new ResponseEntity<>(savedDto, headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> handleUpdate(@RequestBody BeerDto beerDto,
                                                @PathVariable("beerId") UUID beerId) {

        beerService.updateBeer(beerDto, beerId);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
