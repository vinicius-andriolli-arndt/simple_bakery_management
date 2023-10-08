package com.devs2blu.br.com.simpleBakery.domains.bread.dto;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;
import com.devs2blu.br.com.simpleBakery.domains.bread.Bread;
import com.devs2blu.br.com.simpleBakery.domains.bread.Type;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BreadMapperTest {
    private static BreadMapper mapper;
    private Bread bread;
    private BreadRequestDto breadRequestDto;

    @BeforeAll
    static void beforeAll() {
        mapper = new BreadMapper();
    }

    @BeforeEach
    void setUp() {
        bread = Bread.builder()
                .id(UUID.randomUUID())
                .ifs("1234567890123")
                .flavor("Bread Flavor")
                .type(Type.CORNBREAD)
                .publicationYear(2021)
                .quantity(1)
                .baker(Baker.builder().id(UUID.randomUUID()).build())
                .bakery(Bakery.builder().id(UUID.randomUUID()).build())
                .build();

        breadRequestDto = BreadRequestDto.builder()
                .ifs(bread.getIfs())
                .flavor(bread.getFlavor())
                .type(bread.getType())
                .publicationYear(bread.getPublicationYear())
                .quantity(bread.getQuantity())
                .baker(bread.getBaker().getId())
                .bakery(bread.getBakery().getId())
                .build();
    }

    @Test
    @DisplayName("toResponse -> should map Bread to BreadResponseDto")
    void toResponse_shouldMapBreadToBreadResponseDto() {
        // Act
        BreadResponseDto result = mapper.toResponse(bread);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("toEntity -> should map BreadRequestDto to Bread")
    void toEntity_shouldMapBreadRequestDtoToBread() {
        // Act
        Bread result = mapper.toEntity(breadRequestDto);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("toResponseList -> should map List<Bread> to List<BreadResponseDto>")
    void toResponseList_shouldMapListOfBreadToListOfBreadResponseDto() {
        // Act
        List<BreadResponseDto> result = mapper.toResponseList(List.of(bread));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("updateEntity -> should map BreadRequestDto to Bread")
    void updateEntity_shouldMapBreadRequestDtoToBread() {
        // Act
        Bread result = mapper.updateEntity(bread, breadRequestDto);

        // Assert
        assertNotNull(result);
    }
}