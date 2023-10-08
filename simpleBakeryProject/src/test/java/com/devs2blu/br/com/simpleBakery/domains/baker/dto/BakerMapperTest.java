package com.devs2blu.br.com.simpleBakery.domains.baker.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerMapper;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BakerMapperTest {
    private static BakerMapper mapper;
    private Baker baker;
    private BakerRequestDto bakerRequestDto;

    @BeforeAll
    static void beforeAll() {
        mapper = new BakerMapper();
    }

    @BeforeEach
    void setUp() {
    	baker = Baker.builder()
                .id(UUID.randomUUID())
                .name("Baker Name")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nationality("American")
                .breads(List.of())
                .build();

        bakerRequestDto = BakerRequestDto.builder()
                .name(baker.getName())
                .birthDate(baker.getBirthDate())
                .nationality(baker.getNationality())
                .build();
    }

    @Test
    @DisplayName("toResponse -> should map Baker to BakerResponseDto")
    void toResponse_shouldMapAuthorToAuthorResponseDto() {
        // Act
        BakerResponseDto result = mapper.toResponse(baker);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("toEntity -> should map BakerRequestDto to baker")
    void toEntity_shouldMapAuthorRequestDtoToAuthor() {
        // Act
        Baker result = mapper.toEntity(bakerRequestDto);

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("toResponseList -> should map list of baker to list of BakerResponseDto")
    void toResponseList_shouldMapListOfBakerToListOfBakerResponseDto() {
        // Act
        List<BakerResponseDto> result = mapper.toResponseList(List.of(baker));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("updateEntity -> should update baker with AuthorRequestDto")
    void updateEntity_shouldUpdateBakerWithBakerRequestDto() {
        // Act
        Baker result = mapper.updateEntity(baker, bakerRequestDto);

        // Assert
        assertNotNull(result);
    }
}