package com.devs2blu.br.com.simpleBakery.domains.baker;

import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerMapper;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BakerServiceImpl implements BakerService {
    private final BakerRepository bakerRepository;
    private final BakerMapper mapper;
    private final BreadMapper breadMapper;

    @Override
    public List<BakerResponseDto> findAll() {
        return mapper.toResponseList(bakerRepository.findAll());
    }

    @Override
    public BakerResponseDto findById(UUID id) {
        return bakerRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BAKER_NOT_FOUND));
    }

    @Override
    public BakerResponseDto save(BakerRequestDto request) {
        return mapper.toResponse(
        		bakerRepository.save(mapper.toEntity(request))
        );
    }

    @Override
    public BakerResponseDto update(UUID id, BakerRequestDto request) {
        Baker baker = bakerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BAKER_NOT_FOUND));

        return mapper.toResponse(
        		bakerRepository.save(mapper.updateEntity(baker, request))
        );
    }

    @Override
    public void deleteById(UUID id) {
        if (!bakerRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorMessages.BAKER_NOT_FOUND);
        }

        bakerRepository.deleteById(id);
    }

    @Override
    public List<BreadResponseDto> findBreadsByBakerId(UUID id) {
        Baker baker = bakerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BAKER_NOT_FOUND));

        return breadMapper.toResponseList(baker.getBreads());
    }

    @Override
    public boolean existsById(UUID id) {
        return bakerRepository.existsById(id);
    }
}
