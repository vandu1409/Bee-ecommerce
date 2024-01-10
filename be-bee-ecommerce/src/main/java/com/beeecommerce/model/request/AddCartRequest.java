package com.beeecommerce.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartRequest {

    private Long classtyId;

    private Long quantity;

}
