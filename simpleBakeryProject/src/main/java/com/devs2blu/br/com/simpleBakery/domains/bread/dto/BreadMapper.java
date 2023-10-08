package com.devs2blu.br.com.simpleBakery.domains.bread.dto;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;
import com.devs2blu.br.com.simpleBakery.domains.bread.Bread;
import com.devs2blu.br.com.simpleBakery.interfaces.Mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BreadMapper implements Mapper<Bread, BreadRequestDto, BreadResponseDto> {
    @Override
    public BreadResponseDto toResponse(Bread bread) {
        return BreadResponseDto.builder()
                .id(bread.getId())
                .ifs(bread.getIfs())
                .flavor(bread.getFlavor())
                .type(bread.getType())
                .publicationYear(bread.getPublicationYear())
                .quantity(bread.getQuantity())
                .bakerId(bread.getBaker().getId())
                .bakeryId(bread.getBakery().getId())
                .build();
    }

    @Override
    public List<BreadResponseDto> toResponseList(Collection<Bread> breads) {
        return breads.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Bread toEntity(BreadRequestDto request) {
        return Bread.builder()
                .ifs(request.getIfs())
                .flavor(request.getFlavor())
                .type(request.getType())
                .publicationYear(request.getPublicationYear())
                .quantity(request.getQuantity())
                .baker(Baker.builder().id(request.getBaker()).build())
                .bakery(Bakery.builder().id(request.getBakery()).build())
                .build();
    }

    @Override
    public Bread updateEntity(Bread bread, BreadRequestDto request) {
    	bread.setIfs(request.getIfs());
    	bread.setFlavor(request.getFlavor());
    	bread.setType(request.getType());
    	bread.setPublicationYear(request.getPublicationYear());
    	bread.setQuantity(request.getQuantity());
    	bread.setBaker(Baker.builder().id(request.getBaker()).build());
    	bread.setBakery(Bakery.builder().id(request.getBakery()).build());

        return bread;
    }
}
