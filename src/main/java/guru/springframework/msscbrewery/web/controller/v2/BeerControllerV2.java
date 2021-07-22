package guru.springframework.msscbrewery.web.controller.v2;

import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ronnen on 22-Jul-2021
 */


@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {

    private final BeerServiceV2 beerServiceV2;

    public BeerControllerV2(BeerServiceV2 beerServiceV2) {
        this.beerServiceV2 = beerServiceV2;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDtoV2> handlePost(@RequestBody BeerDtoV2 beerDtoV2) {

        BeerDtoV2 savedDto = beerServiceV2.saveBeer(beerDtoV2);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");

        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        Date d = new Date();

        headers.setDate("Current GMT Date and Time", d. getTime());

        return new ResponseEntity<>(savedDto, headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity<BeerDtoV2> handleUpdate(@RequestBody BeerDtoV2 beerDto,
                                                @PathVariable("beerId") UUID beerId) {

        beerServiceV2.updateBeer(beerDto, beerId);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable("beerId") UUID beerId) {

        beerServiceV2.deleteBeer(beerId);
    }



}

