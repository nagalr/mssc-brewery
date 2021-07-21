package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

/**
 * Created by ronnen on 21-Jul-2021
 */

public interface CustomerService {

    CustomerDto getCustomerById(UUID customerId);
}
