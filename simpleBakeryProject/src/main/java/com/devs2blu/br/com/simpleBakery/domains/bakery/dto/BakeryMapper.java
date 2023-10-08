package com.devs2blu.br.com.simpleBakery.domains.bakery.dto;

import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;
import com.devs2blu.br.com.simpleBakery.interfaces.Mapper;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BakeryMapper implements Mapper<Bakery, BakeryRequestDto, BakeryResponseDto> {
    @Override
    public BakeryResponseDto toResponse(Bakery bakery) {
        return BakeryResponseDto.builder()
                .id(bakery.getId())
                .name(bakery.getName())
                .address(bakery.getAddress())
                .phoneNumber(bakery.getPhoneNumber())
                .build();
    }

    @Override
    public Bakery toEntity(BakeryRequestDto request) {
        return Bakery.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    @Override
    public List<BakeryResponseDto> toResponseList(Collection<Bakery> list) {
        return list.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Bakery updateEntity(Bakery bakery, BakeryRequestDto request) {
    	bakery.setName(request.getName());
    	bakery.setAddress(request.getAddress());
    	bakery.setPhoneNumber(request.getPhoneNumber());

        return bakery;
    }
}
