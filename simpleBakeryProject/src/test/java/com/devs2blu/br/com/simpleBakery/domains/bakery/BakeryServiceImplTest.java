package com.devs2blu.br.com.simpleBakery.domains.bakery;

import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;
import com.devs2blu.br.com.simpleBakery.domains.bakery.BakeryRepository;
import com.devs2blu.br.com.simpleBakery.domains.bakery.BakeryServiceImpl;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryMapper;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.exceptions.BakeryNameAlreadyExists;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BakeryServiceImplTest {
    @InjectMocks
    private BakeryServiceImpl bakeryService;

    @Mock
    private BakeryRepository bakeryRepository;

    @Mock
    private BakeryMapper mapper;

    @Mock
    private BreadMapper breadMapper;

    private Bakery bakery;
    private BakeryResponseDto response;
    private BakeryRequestDto request;

    @BeforeEach
    void setUp() {
        bakery = Bakery.builder()
                .id(UUID.randomUUID())
                .name("Bakery")
                .address("Address")
                .phoneNumber("1234567890")
                .breads(new ArrayList<>())
                .build();

        response = BakeryResponseDto.builder()
                .id(bakery.getId())
                .name(bakery.getName())
                .address(bakery.getAddress())
                .phoneNumber(bakery.getPhoneNumber())
                .build();
    }

    @Nested
    class QueryMethods {
        @Test
        @DisplayName("findAll() -> should return list of BakeryResponseDto")
        void findAll_shouldReturnListOfBakerys() {
            // Arrange
            when(bakeryRepository.findAll()).thenReturn(List.of(bakery));
            when(mapper.toResponseList(List.of(bakery))).thenReturn(List.of(response));

            // Act
            List<BakeryResponseDto> result = bakeryService.findAll();

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(response, result.get(0));

            verify(bakeryRepository, times(1)).findAll();
            verify(mapper, times(1)).toResponseList(List.of(bakery));
        }

        @Test
        @DisplayName("findById() -> should return BakeryResponseDto")
        void findById_shouldReturnBakery() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.of(bakery));
            when(mapper.toResponse(bakery)).thenReturn(response);

            // Act
            BakeryResponseDto result = bakeryService.findById(bakery.getId());

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(mapper, times(1)).toResponse(bakery);
        }

        @Test
        @DisplayName("findById() -> should throw EntityNotFoundException when bakery not found")
        void findById_shouldThrowEntityNotFoundException_whenBakeryNotFound() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> bakeryService.findById(bakery.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(mapper, times(0)).toResponse(bakery);
        }

        @Test
        @DisplayName("existsById() -> should return true when bakery exists")
        void existsById_shouldReturnTrue_whenBakeryExists() {
            // Arrange
            when(bakeryRepository.existsById(bakery.getId())).thenReturn(true);

            // Act
            boolean result = bakeryService.existsById(bakery.getId());

            // Assert
            assertTrue(result);

            verify(bakeryRepository, times(1)).existsById(bakery.getId());
        }

        @Test
        @DisplayName("existsById() -> should return false when bakery not exists")
        void existsById_shouldReturnFalse_whenBakeryNotExists() {
            // Arrange
            when(bakeryRepository.existsById(bakery.getId())).thenReturn(false);

            // Act
            boolean result = bakeryService.existsById(bakery.getId());

            // Assert
            assertFalse(result);

            verify(bakeryRepository, times(1)).existsById(bakery.getId());
        }

        @Test
        @DisplayName("findBreadsByBakeryId() -> should return list of BookResponseDto")
        void findBreadsByBakeryId_shouldReturnListOfBreads() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.of(bakery));
            when(breadMapper.toResponseList(bakery.getBreads())).thenReturn(new ArrayList<>());

            // Act
            List<BreadResponseDto> result = bakeryService.findBreadsByBakeryId(bakery.getId());

            // Assert
            assertNotNull(result);
            assertEquals(0, result.size());

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(breadMapper, times(1)).toResponseList(bakery.getBreads());
        }

        @Test
        @DisplayName("findBreadsByBakeryId() -> should throw EntityNotFoundException when bakery not found")
        void findBreadsByBakeryId_shouldThrowEntityNotFoundException_whenBakeryNotFound() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> bakeryService.findBreadsByBakeryId(bakery.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(breadMapper, times(0)).toResponseList(bakery.getBreads());
        }
    }

    @Nested
    class MutationMethods {
        @BeforeEach
        void setUp() {
            request = BakeryRequestDto.builder()
                    .name(bakery.getName())
                    .address(bakery.getAddress())
                    .phoneNumber(bakery.getPhoneNumber())
                    .build();
        }

        @Test
        @DisplayName("save() -> should return BakeryResponseDto")
        void save_shouldReturnBakery() {
            // Arrange
            when(bakeryRepository.existsByName(request.getName())).thenReturn(false);
            when(mapper.toEntity(request)).thenReturn(bakery);
            when(bakeryRepository.save(bakery)).thenReturn(bakery);
            when(mapper.toResponse(bakery)).thenReturn(response);

            // Act
            BakeryResponseDto result = bakeryService.save(request);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakeryRepository, times(1)).existsByName(request.getName());
            verify(mapper, times(1)).toEntity(request);
            verify(bakeryRepository, times(1)).save(bakery);
            verify(mapper, times(1)).toResponse(bakery);
        }

        @Test
        @DisplayName("save() -> should throw BakeryNameAlreadyExists when bakery name already exists")
        void save_shouldThrowBakeryNameAlreadyExists_whenBakeryNameAlreadyExists() {
            // Arrange
            when(bakeryRepository.existsByName(request.getName())).thenReturn(true);

            // Act
            Runnable result = () -> bakeryService.save(request);

            // Assert
            assertThrows(BakeryNameAlreadyExists.class, result::run);

            verify(bakeryRepository, times(1)).existsByName(request.getName());
            verify(mapper, times(0)).toEntity(request);
            verify(bakeryRepository, times(0)).save(bakery);
            verify(mapper, times(0)).toResponse(bakery);
        }

        @Test
        @DisplayName("update() -> should return BakeryResponseDto")
        void update_shouldReturnBakery() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.of(bakery));
            when(bakeryRepository.existsByNameAndIdNot(request.getName(), bakery.getId())).thenReturn(false);
            when(mapper.updateEntity(bakery, request)).thenReturn(bakery);
            when(bakeryRepository.save(bakery)).thenReturn(bakery);
            when(mapper.toResponse(bakery)).thenReturn(response);

            // Act
            BakeryResponseDto result = bakeryService.update(bakery.getId(), request);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(bakeryRepository, times(1)).existsByNameAndIdNot(request.getName(), bakery.getId());
            verify(mapper, times(1)).updateEntity(bakery, request);
            verify(bakeryRepository, times(1)).save(bakery);
            verify(mapper, times(1)).toResponse(bakery);
        }

        @Test
        @DisplayName("update() -> should throw EntityNotFoundException when bakery not found")
        void update_shouldThrowEntityNotFoundException_whenBakeryNotFound() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> bakeryService.update(bakery.getId(), request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(bakeryRepository, times(0)).existsByNameAndIdNot(request.getName(), bakery.getId());
            verify(mapper, times(0)).updateEntity(bakery, request);
            verify(bakeryRepository, times(0)).save(bakery);
            verify(mapper, times(0)).toResponse(bakery);
        }

        @Test
        @DisplayName("update() -> should throw BakeryNameAlreadyExists when bakery name already exists")
        void update_shouldThrowBakeryNameAlreadyExists_whenBakeryNameAlreadyExists() {
            // Arrange
            when(bakeryRepository.findById(bakery.getId())).thenReturn(Optional.of(bakery));
            when(bakeryRepository.existsByNameAndIdNot(request.getName(), bakery.getId())).thenReturn(true);

            // Act
            Runnable result = () -> bakeryService.update(bakery.getId(), request);

            // Assert
            assertThrows(BakeryNameAlreadyExists.class, result::run);

            verify(bakeryRepository, times(1)).findById(bakery.getId());
            verify(bakeryRepository, times(1)).existsByNameAndIdNot(request.getName(), bakery.getId());
            verify(mapper, times(0)).updateEntity(bakery, request);
            verify(bakeryRepository, times(0)).save(bakery);
            verify(mapper, times(0)).toResponse(bakery);
        }

        @Test
        @DisplayName("deleteById() -> should delete bakery")
        void deleteById_shouldDeleteBakery() {
            // Arrange
            when(bakeryRepository.existsById(bakery.getId())).thenReturn(true);

            // Act
            bakeryService.deleteById(bakery.getId());

            // Assert
            verify(bakeryRepository, times(1)).existsById(bakery.getId());
            verify(bakeryRepository, times(1)).deleteById(bakery.getId());
        }

        @Test
        @DisplayName("deleteById() -> should throw EntityNotFoundException when bakery not found")
        void deleteById_shouldThrowEntityNotFoundException_whenBakeryNotFound() {
            // Arrange
            when(bakeryRepository.existsById(bakery.getId())).thenReturn(false);

            // Act
            Runnable result = () -> bakeryService.deleteById(bakery.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakeryRepository, times(1)).existsById(bakery.getId());
            verify(bakeryRepository, times(0)).deleteById(bakery.getId());
        }
    }
}