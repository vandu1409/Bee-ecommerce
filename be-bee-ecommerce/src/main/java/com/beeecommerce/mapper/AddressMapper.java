package com.beeecommerce.mapper;

import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Address;
import com.beeecommerce.entity.Ward;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.mapper.core.BaseMapper;
import com.beeecommerce.model.core.SimpleAddress;
import com.beeecommerce.model.dto.AddressDto;
import com.beeecommerce.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressMapper implements BaseMapper<Address, AddressDto> {

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    @Override
    public AddressDto apply(Address address) {

        String detailsAddress = address.getStreet() +", "+address.getWard().getName()+", "+address.getWard().getDistrict().getName()
                +", "+address.getWard().getDistrict().getProvince().getName();
        return AddressDto
                .builder()
                .id(address.getId())
                .userId(address.getId())
                .receiverName(address.getReceiverName())
                .receiverPhone(address.getReceiverPhone())
                .note(address.getNote())
                .wardId(address.getWard().getId())
                .street(address.getStreet())
                .detailsAddress(detailsAddress)
                .build();
    }

    public Address loadSimpleAddress(SimpleAddress simpleAddress) throws AuthenticateException {
        Address address = new Address();

        Ward ward = new Ward();
        ward.setId(simpleAddress.getWardId());

        Account account = accountService.getAuthenticatedAccount();

        address.setWard(ward);
        address.setStreet(simpleAddress.getStreet());
        address.setReceiverName(simpleAddress.getReceiverName());
        address.setReceiverPhone(simpleAddress.getReceiverPhone());
        address.setNote(simpleAddress.getNote());
        address.setUser(account);

        return address;

    }

}
