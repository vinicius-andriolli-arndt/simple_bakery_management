package com.devs2blu.br.com.simpleBakery.domains.bread;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devs2blu.br.com.simpleBakery.domains.baker.BakerService;
import com.devs2blu.br.com.simpleBakery.domains.bakery.BakeryService;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.exceptions.IfsAlreadyExistsException;
import com.devs2blu.br.com.simpleBakery.exceptions.PublicationYearTooNew;
import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BreadServiceImpl implements BreadService {
    private final BreadRepository breadRepository;
    private final BakerService bakerService;
    private final BakeryService bakeryService;
    private final BreadMapper mapper;

    @Override
    public List<BreadResponseDto> findAll() {
        return mapper.toResponseList(breadRepository.findAll());
    }

    @Override
    public Page<BreadResponseDto> findAll(Pageable pageable) {
        return breadRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public BreadResponseDto findById(UUID id) {
        return breadRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BREAD_NOT_FOUND));
    }

    @Override
    public BreadResponseDto save(BreadRequestDto request) {
        if (!bakerService.existsById(request.getBaker())) {
            throw new EntityNotFoundException(ErrorMessages.BAKER_NOT_FOUND);
        }

        if (!bakeryService.existsById(request.getBakery())) {
            throw new EntityNotFoundException(ErrorMessages.BAKERY_NOT_FOUND);
        }

        if (breadRepository.existsByIfs(request.getIfs())) {
            throw new IfsAlreadyExistsException();
        }

        if (request.getPublicationYear() > LocalDate.now().getYear()) {
            throw new PublicationYearTooNew();
        }

        return mapper.toResponse(
                breadRepository.save(mapper.toEntity(request))
        );
    }

    @Override
    public BreadResponseDto update(UUID id, BreadRequestDto request) {
        Bread bread = breadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BREAD_NOT_FOUND));

        if (!bakerService.existsById(request.getBaker())) {
            throw new EntityNotFoundException(ErrorMessages.BAKER_NOT_FOUND);
        }

        if (!bakeryService.existsById(request.getBakery())) {
            throw new EntityNotFoundException(ErrorMessages.BAKERY_NOT_FOUND);
        }

        if (breadRepository.existsByIfsAndIdNot(request.getIfs(), id)) {
            throw new IfsAlreadyExistsException();
        }

        if (request.getPublicationYear() > LocalDate.now().getYear()) {
            throw new PublicationYearTooNew();
        }

        return mapper.toResponse(
                breadRepository.save(mapper.updateEntity(bread, request))
        );
    }

    @Override
    public void deleteById(UUID id) {
        if (!breadRepository.existsById(id)) {
            throw new EntityNotFoundException(ErrorMessages.BREAD_NOT_FOUND);
        }

        breadRepository.deleteById(id);
    }

    @Override
    public BreadResponseDto findByIfs(String ifs) {
        return breadRepository.findByIfs(ifs)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.BREAD_NOT_FOUND));
    }

    @Override
    public List<BreadResponseDto> findByFlavor(String flavor) {
        return mapper.toResponseList(breadRepository.findByFlavorContainingIgnoreCase(flavor));
    }
}
