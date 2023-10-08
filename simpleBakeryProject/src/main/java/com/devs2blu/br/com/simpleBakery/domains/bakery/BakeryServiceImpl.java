package com.devs2blu.br.com.simpleBakery.domains.bakery;

import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryMapper;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.exceptions.BakeryNameAlreadyExists;
import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BakeryServiceImpl implements BakeryService {
    private final BakeryRepository bakeryRepository;
    private final BakeryMapper mapper;
    private final BreadMapper breadMapper;

    @Override
    public List<BakeryResponseDto> findAll() {
        return mapper.toResponseList(bakeryRepository.findAll());
    }

    @Override
    public BakeryResponseDto findById(UUID id) {
        return bakeryRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BAKERY_NOT_FOUND));
    }

    @Override
    public BakeryResponseDto save(BakeryRequestDto request) {
        if (bakeryRepository.existsByName(request.getName())) {
            throw new BakeryNameAlreadyExists();
        }

        return mapper.toResponse(
        		bakeryRepository.save(mapper.toEntity(request))
        );
    }

    @Override
    public BakeryResponseDto update(UUID id, BakeryRequestDto request) {
        Bakery publisher = bakeryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BAKERY_NOT_FOUND));

        if (bakeryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new BakeryNameAlreadyExists();
        }

        return mapper.toResponse(
        		bakeryRepository.save(mapper.updateEntity(publisher, request))
        );
    }

    @Override
    public void deleteById(UUID id) {
        if (!bakeryRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorMessages.BAKERY_NOT_FOUND);
        }

        bakeryRepository.deleteById(id);
    }

    @Override
    public List<BreadResponseDto> findBreadsByBakeryId(UUID id) {
        Bakery publisher = bakeryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BAKERY_NOT_FOUND));

        return breadMapper.toResponseList(publisher.getBreads());
    }

    @Override
    public boolean existsById(UUID id) {
        return bakeryRepository.existsById(id);
    }
}
