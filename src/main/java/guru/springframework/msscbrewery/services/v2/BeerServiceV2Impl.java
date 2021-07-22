package guru.springframework.msscbrewery.services.v2;

import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.model.v2.BeerStyleEnum;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by ronnen on 22-Jul-2021
 */

@Service
public class BeerServiceV2Impl implements BeerServiceV2 {
    @Override
    public BeerDtoV2 getBeerById(UUID beerId) {
        return BeerDtoV2.builder().id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .build();
    }

    @Override
    public BeerDtoV2 saveBeer(BeerDtoV2 beerDtoV2) {
        return BeerDtoV2.builder()
                .beerName(beerDtoV2.getBeerName())
                .beerStyle(beerDtoV2.getBeerStyle())
                .id(beerDtoV2.getId())
                .upc(beerDtoV2.getUpc())
                .build();
    }

    @Override
    public void updateBeer(BeerDtoV2 beerDto, UUID beerId) {
        // todo - void method
    }

    @Override
    public void deleteBeer(UUID beerId) {
        // todo - void method
    }
}
