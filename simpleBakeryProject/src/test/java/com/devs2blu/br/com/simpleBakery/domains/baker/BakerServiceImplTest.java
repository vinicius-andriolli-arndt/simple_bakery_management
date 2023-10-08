package com.devs2blu.br.com.simpleBakery.domains.baker;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.domains.baker.BakerRepository;
import com.devs2blu.br.com.simpleBakery.domains.baker.BakerServiceImpl;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerMapper;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BakerServiceImplTest {
    @InjectMocks
    private BakerServiceImpl bakerService;

    @Mock
    private BakerRepository bakerRepository;

    @Mock
    private BakerMapper mapper;

    @Mock
    private BreadMapper bookMapper;

    private Baker baker;
    private BakerResponseDto response;
    private BakerRequestDto request;

    @BeforeEach
    void setUp() {
        baker = Baker.builder()
                .id(UUID.randomUUID())
                .name("Baker Name")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nationality("American")
                .breads(new ArrayList<>())
                .build();

        response = BakerResponseDto.builder()
                .id(baker.getId())
                .name(baker.getName())
                .birthDate(baker.getBirthDate())
                .nationality(baker.getNationality())
                .build();
    }

    @Nested
    class QueryMethods {
        @Test
        @DisplayName("findAll() -> should return a list of BakerResponseDto")
        void findAll_shouldReturnListOfAuthors() {
            // Arrange
            when(bakerRepository.findAll()).thenReturn(List.of(baker));
            when(mapper.toResponseList(List.of(baker))).thenReturn(List.of(response));

            // Act
            List<BakerResponseDto> result = bakerService.findAll();

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(response, result.get(0));

            verify(bakerRepository, times(1)).findAll();
            verify(mapper, times(1)).toResponseList(List.of(baker));
        }

        @Test
        @DisplayName("findById(UUID id) -> should return BakerResponseDto by id")
        void findById_shouldReturnAuthorById() {
            // Arrange
            when(bakerRepository.findById(baker.getId())).thenReturn(Optional.of(baker));
            when(mapper.toResponse(baker)).thenReturn(response);

            // Act
            BakerResponseDto result = bakerService.findById(baker.getId());

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakerRepository, times(1)).findById(baker.getId());
            verify(mapper, times(1)).toResponse(baker);
        }

        @Test
        @DisplayName("findById(UUID id) -> should throw EntityNotFoundException when baker not found")
        void findById_shouldThrowEntityNotFoundException() {
            // Arrange
            when(bakerRepository.findById(baker.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> bakerService.findById(baker.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakerRepository, times(1)).findById(baker.getId());
            verify(mapper, times(0)).toResponse(baker);
        }

        @Test
        @DisplayName("existsById(UUID id) -> should return true when baker exists")
        void existsById_shouldReturnTrue() {
            // Arrange
            when(bakerRepository.existsById(baker.getId())).thenReturn(true);

            // Act
            boolean result = bakerService.existsById(baker.getId());

            // Assert
            assertTrue(result);

            verify(bakerRepository, times(1)).existsById(baker.getId());
        }

        @Test
        @DisplayName("existsById(UUID id) -> should return false when baker does not exist")
        void existsById_shouldReturnFalse() {
            // Arrange
            when(bakerRepository.existsById(baker.getId())).thenReturn(false);

            // Act
            boolean result = bakerService.existsById(baker.getId());

            // Assert
            assertFalse(result);

            verify(bakerRepository, times(1)).existsById(baker.getId());
        }

        @Test
        @DisplayName("findBreadByAuthorId(UUID id) -> should return a list of BreadResponseDto")
        void findBooksByAuthorId_shouldReturnListOfBooks() {
            // Arrange
            when(bakerRepository.findById(baker.getId())).thenReturn(Optional.of(baker));
            when(bookMapper.toResponseList(baker.getBreads())).thenReturn(new ArrayList<>());

            // Act
            List<BreadResponseDto> result = bakerService.findBreadsByBakerId(baker.getId());

            // Assert
            assertNotNull(result);
            assertEquals(0, result.size());

            verify(bakerRepository, times(1)).findById(baker.getId());
            verify(bookMapper, times(1)).toResponseList(baker.getBreads());
        }

        @Test
        @DisplayName("findBreadsByBakerId(UUID id) -> should throw EntityNotFoundException when baker not found")
        void findBooksByAuthorId_shouldThrowEntityNotFoundException() {
            // Arrange
            when(bakerRepository.findById(baker.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> bakerService.findBreadsByBakerId(baker.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakerRepository, times(1)).findById(baker.getId());
            verify(bookMapper, times(0)).toResponseList(baker.getBreads());
        }
    }

    @Nested
    class MutationMethods {
        @BeforeEach
        void setUp() {
            request = BakerRequestDto.builder()
                    .name(baker.getName())
                    .birthDate(baker.getBirthDate())
                    .nationality(baker.getNationality())
                    .build();
        }

        @Test
        @DisplayName("save(BakerRequestDto request) -> should return BakerResponseDto")
        void save_shouldReturnAuthor() {
            // Arrange
            when(bakerRepository.save(baker)).thenReturn(baker);
            when(mapper.toEntity(request)).thenReturn(baker);
            when(mapper.toResponse(baker)).thenReturn(response);

            // Act
            BakerResponseDto result = bakerService.save(request);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakerRepository, times(1)).save(baker);
            verify(mapper, times(1)).toEntity(request);
            verify(mapper, times(1)).toResponse(baker);
        }

        @Test
        @DisplayName("update(UUID id, BakerRequestDto request) -> should return BakerResponseDto")
        void update_shouldReturnAuthor() {
            // Arrange
            when(bakerRepository.findById(baker.getId())).thenReturn(Optional.of(baker));
            when(bakerRepository.save(baker)).thenReturn(baker);
            when(mapper.updateEntity(baker, request)).thenReturn(baker);
            when(mapper.toResponse(baker)).thenReturn(response);

            // Act
            BakerResponseDto result = bakerService.update(baker.getId(), request);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakerRepository, times(1)).findById(baker.getId());
            verify(bakerRepository, times(1)).save(baker);
            verify(mapper, times(1)).updateEntity(baker, request);
            verify(mapper, times(1)).toResponse(baker);
        }

        @Test
        @DisplayName("update(UUID id, BakerRequestDto request) -> should throw EntityNotFoundException when baker not found")
        void update_shouldThrowEntityNotFoundException() {
            // Arrange
            when(bakerRepository.findById(baker.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> bakerService.update(baker.getId(), request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakerRepository, times(1)).findById(baker.getId());
            verify(bakerRepository, times(0)).save(baker);
            verify(mapper, times(0)).updateEntity(baker, request);
            verify(mapper, times(0)).toResponse(baker);
        }

        @Test
        @DisplayName("deleteById(UUID id) -> should delete baker by id")
        void deleteById_shouldDeleteAuthorById() {
            // Arrange
            when(bakerRepository.existsById(baker.getId())).thenReturn(true);

            // Act
            bakerService.deleteById(baker.getId());

            // Assert
            verify(bakerRepository, times(1)).existsById(baker.getId());
            verify(bakerRepository, times(1)).deleteById(baker.getId());
        }

        @Test
        @DisplayName("deleteById(UUID id) -> should throw EntityNotFoundException when baker not found")
        void deleteById_shouldThrowEntityNotFoundException() {
            // Arrange
            when(bakerRepository.existsById(baker.getId())).thenReturn(false);

            // Act
            Runnable result = () -> bakerService.deleteById(baker.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakerRepository, times(1)).existsById(baker.getId());
            verify(bakerRepository, times(0)).deleteById(baker.getId());
        }
    }
}