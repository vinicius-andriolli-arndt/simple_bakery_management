package com.devs2blu.br.com.simpleBakery.domains.bakery.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryMapper;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryResponseDto;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BakeryMapperTest {
    private static BakeryMapper mapper;
    private Bakery bakery;
    private BakeryRequestDto bakeryRequestDto;

    @BeforeAll
    static void beforeAll() {
        mapper = new BakeryMapper();
    }

    @BeforeEach
    void setUp() {
        bakery = Bakery.builder()
                .id(UUID.randomUUID())
                .name("Bakery Name")
                .address("Bakery Address")
                .phoneNumber("123456789")
                .breads(List.of())
                .build();

        bakeryRequestDto = BakeryRequestDto.builder()
                .name(bakery.getName())
                .address(bakery.getAddress())
                .phoneNumber(bakery.getPhoneNumber())
                .build();
    }

    @Test
    @DisplayName("toResponse -> should map Bakery to BakeryResponseDto")
    void toResponse_shouldMapBakeryToBakeryResponseDto() {
        // Act
        BakeryResponseDto result = mapper.toResponse(bakery);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("toEntity -> should map BakeryRequestDto to Bakery")
    void toEntity_shouldMapBakeryRequestDtoToBakery() {
        // Act
        Bakery result = mapper.toEntity(bakeryRequestDto);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("toResponseList -> should map List<Bakery> to List<BakeryResponseDto>")
    void toResponseList_shouldMapListOfBakeryToListOfBakeryResponseDto() {
        // Act
        List<BakeryResponseDto> result = mapper.toResponseList(List.of(bakery));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("updateEntity -> should update Bakery with BakeryRequestDto")
    void updateEntity_shouldUpdateBakeryWithBakeryRequestDto() {
        // Act
        Bakery result = mapper.updateEntity(bakery, bakeryRequestDto);

        // Assert
        assertNotNull(result);
    }
}