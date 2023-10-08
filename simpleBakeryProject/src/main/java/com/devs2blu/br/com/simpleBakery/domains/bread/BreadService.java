package com.devs2blu.br.com.simpleBakery.domains.bread;


import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.interfaces.Crud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BreadService extends Crud<BreadRequestDto, BreadResponseDto, UUID> {
    BreadResponseDto findByIfs(String ifs);

    List<BreadResponseDto> findByFlavor(String flavor);

    Page<BreadResponseDto> findAll(Pageable pageable);
}
