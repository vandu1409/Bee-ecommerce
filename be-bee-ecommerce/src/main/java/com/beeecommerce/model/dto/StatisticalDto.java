package com.beeecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalDto {

    private double totalRevenue;

    private int totalOrder;
}
