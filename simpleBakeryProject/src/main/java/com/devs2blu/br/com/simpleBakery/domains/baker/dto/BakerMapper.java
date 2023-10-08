package com.devs2blu.br.com.simpleBakery.domains.baker.dto;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.interfaces.Mapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BakerMapper implements Mapper<Baker, BakerRequestDto, BakerResponseDto> {
    @Override
    public BakerResponseDto toResponse(Baker baker) {
        return BakerResponseDto.builder()
                .id(baker.getId())
                .name(baker.getName())
                .birthDate(baker.getBirthDate())
                .nationality(baker.getNationality())
                .build();
    }

    @Override
    public Baker toEntity(BakerRequestDto request) {
        return Baker.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .nationality(request.getNationality())
                .build();
    }

    @Override
    public List<BakerResponseDto> toResponseList(Collection<Baker> list) {
        return list.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Baker updateEntity(Baker baker, BakerRequestDto request) {
    	baker.setName(request.getName());
    	baker.setBirthDate(request.getBirthDate());
    	baker.setNationality(request.getNationality());

        return baker;
    }
}
