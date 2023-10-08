package com.devs2blu.br.com.simpleBakery.domains.baker;

import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.interfaces.Crud;

import java.util.List;
import java.util.UUID;

public interface BakerService extends Crud<BakerRequestDto, BakerResponseDto, UUID> {
    List<BreadResponseDto> findBreadsByBakerId(UUID id);
    boolean existsById(UUID id);
}
