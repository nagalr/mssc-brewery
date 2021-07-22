package guru.springframework.msscbrewery.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by ronnen on 21-Jul-2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private UUID id;
    private String name;
}
