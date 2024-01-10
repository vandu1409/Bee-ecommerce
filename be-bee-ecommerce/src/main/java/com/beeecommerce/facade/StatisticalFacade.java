package com.beeecommerce.facade;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.model.dto.StatisticalDto;
import com.beeecommerce.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticalFacade {
    private final StatisticalService statisticalService;

    public StatisticalDto statistical() throws AuthenticateException {
        StatisticalDto statisticalDto = new StatisticalDto();

        double totalRevenue = statisticalService.statisticalRevenueByCurrentMonth();

        int totalOrder = statisticalService.totalOrderByCurrentMoth();

        statisticalDto.setTotalRevenue(totalRevenue);
        statisticalDto.setTotalOrder(totalOrder);

        return statisticalDto;
    }
}
