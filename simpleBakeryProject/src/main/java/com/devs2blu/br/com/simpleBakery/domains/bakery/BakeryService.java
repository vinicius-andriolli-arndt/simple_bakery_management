package com.devs2blu.br.com.simpleBakery.domains.bakery;

import com.devs2blu.br.com.simpleBakery.interfaces.Crud;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;

import java.util.List;
import java.util.UUID;

public interface BakeryService extends Crud<BakeryRequestDto, BakeryResponseDto, UUID> {
    List<BreadResponseDto> findBreadsByBakeryId(UUID id);
    boolean existsById(UUID id);
}
