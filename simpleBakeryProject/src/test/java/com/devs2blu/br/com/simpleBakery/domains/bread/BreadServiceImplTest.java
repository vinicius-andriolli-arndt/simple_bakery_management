package com.devs2blu.br.com.simpleBakery.domains.bread;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.domains.baker.BakerService;
import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;
import com.devs2blu.br.com.simpleBakery.domains.bakery.BakeryService;
import com.devs2blu.br.com.simpleBakery.domains.bread.Bread;
import com.devs2blu.br.com.simpleBakery.domains.bread.BreadRepository;
import com.devs2blu.br.com.simpleBakery.domains.bread.BreadServiceImpl;
import com.devs2blu.br.com.simpleBakery.domains.bread.Type;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadMapper;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;
import com.devs2blu.br.com.simpleBakery.exceptions.IfsAlreadyExistsException;
import com.devs2blu.br.com.simpleBakery.exceptions.PublicationYearTooNew;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BreadServiceImplTest {
    @InjectMocks
    private BreadServiceImpl breadService;

    @Mock
    private BreadRepository breadRepository;

    @Mock
    private BakerService bakerService;

    @Mock
    private BakeryService bakeryService;

    @Mock
    private BreadMapper mapper;

    private Bread bread;
    private BreadResponseDto response;
    private BreadRequestDto request;

    @BeforeEach
    void setUp() {
        bread = Bread.builder()
                .id(UUID.randomUUID())
                .ifs("9783161484100")
                .flavor("Clean Code")
                .type(Type.CORNBREAD)
                .publicationYear(2008)
                .quantity(464)
                .baker(new Baker())
                .bakery(new Bakery())
                .build();

        response = BreadResponseDto.builder()
                .id(bread.getId())
                .ifs(bread.getIfs())
                .flavor(bread.getFlavor())
                .type(bread.getType())
                .publicationYear(bread.getPublicationYear())
                .quantity(bread.getQuantity())
                .bakerId(bread.getBaker().getId())
                .bakeryId(bread.getBakery().getId())
                .build();
    }

    @Nested
    class QueryMethods {
        @Test
        @DisplayName("findAll() -> should return list of BreadResponseDto")
        void findAll_shouldReturnListOfBreads() {
            // Arrange
            when(breadRepository.findAll()).thenReturn(List.of(bread));
            when(mapper.toResponseList(List.of(bread))).thenReturn(List.of(response));

            // Act
            List<BreadResponseDto> result = breadService.findAll();

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(response, result.get(0));

            verify(breadRepository, times(1)).findAll();
            verify(mapper, times(1)).toResponseList(List.of(bread));
        }

        @Test
        @DisplayName("findAll(Pageable) -> should return page of BreadResponseDto")
        void findAllPageable_shouldReturnPageOfBreads() {
            // Arrange
            Pageable pageable = Pageable.unpaged();

            when(breadRepository.findAll(pageable)).thenReturn(Page.empty());

            // Act
            Page<BreadResponseDto> result = breadService.findAll(pageable);

            // Assert
            assertNotNull(result);
            assertEquals(0, result.getTotalElements());
            assertEquals(1, result.getTotalPages());

            verify(breadRepository, times(1)).findAll(pageable);
        }

        @Test
        @DisplayName("findById(UUID) -> should return BreadResponseDto by id")
        void findById_shouldReturnBread() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.of(bread));
            when(mapper.toResponse(bread)).thenReturn(response);

            // Act
            BreadResponseDto result = breadService.findById(bread.getId());

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(mapper, times(1)).toResponse(bread);
        }

        @Test
        @DisplayName("findById(UUID) -> should throw EntityNotFoundException when bread not found")
        void findById_shouldThrowEntityNotFoundException_whenBreadNotFound() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> breadService.findById(bread.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(mapper, times(0)).toResponse(bread);
        }

        @Test
        @DisplayName("findByIfs(String) -> should return BreadResponseDto by ifs")
        void findByIfs_shouldReturnBread() {
            // Arrange
            when(breadRepository.findByIfs(bread.getIfs())).thenReturn(Optional.of(bread));
            when(mapper.toResponse(bread)).thenReturn(response);

            // Act
            BreadResponseDto result = breadService.findByIfs(bread.getIfs());

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(breadRepository, times(1)).findByIfs(bread.getIfs());
            verify(mapper, times(1)).toResponse(bread);
        }

        @Test
        @DisplayName("findByIfs(String) -> should throw EntityNotFoundException when bread not found")
        void findByIfs_shouldThrowEntityNotFoundException_whenBreadNotFound() {
            // Arrange
            when(breadRepository.findByIfs(bread.getIfs())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> breadService.findByIfs(bread.getIfs());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(breadRepository, times(1)).findByIfs(bread.getIfs());
        }

        @Test
        @DisplayName("findByFlavor(String) -> should return list of BreadResponseDto by flavor")
        void findByFlavor_shouldReturnListOfBread() {
            // Arrange
            when(breadRepository.findByFlavorContainingIgnoreCase(bread.getFlavor())).thenReturn(List.of(bread));
            when(mapper.toResponseList(List.of(bread))).thenReturn(List.of(response));

            // Act
            List<BreadResponseDto> result = breadService.findByFlavor(bread.getFlavor());

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());

            verify(breadRepository, times(1)).findByFlavorContainingIgnoreCase(bread.getFlavor());
            verify(mapper, times(1)).toResponseList(List.of(bread));
        }
    }

    @Nested
    class MutationMethods {
        @BeforeEach
        void setUp() {
            request = BreadRequestDto.builder()
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
        @DisplayName("save(BreadRequestDto) -> should return saved BreadResponseDto")
        void save_shouldReturnSavedBread() {
            // Arrange
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(true);
            when(breadRepository.existsByIfs(bread.getIfs())).thenReturn(false);
            when(mapper.toEntity(request)).thenReturn(bread);
            when(breadRepository.save(bread)).thenReturn(bread);
            when(mapper.toResponse(bread)).thenReturn(response);

            // Act
            BreadResponseDto result = breadService.save(request);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
            verify(breadRepository, times(1)).existsByIfs(bread.getIfs());
            verify(mapper, times(1)).toEntity(request);
            verify(breadRepository, times(1)).save(bread);
            verify(mapper, times(1)).toResponse(bread);
        }

        @Test
        @DisplayName("save(BreadRequestDto) -> should throw EntityNotFoundException when baker not found")
        void save_shouldThrowEntityNotFoundException_whenBakerNotFound() {
            // Arrange
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(false);

            // Act
            Runnable result = () -> breadService.save(request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
        }

        @Test
        @DisplayName("save(BreadRequestDto) -> should throw EntityNotFoundException when bakery not found")
        void save_shouldThrowEntityNotFoundException_whenBakeryNotFound() {
            // Arrange
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(false);

            // Act
            Runnable result = () -> breadService.save(request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
        }

        @Test
        @DisplayName("save(BreadRequestDto) -> should throw IfsAlreadyExistsException when ifs already exists")
        void save_shouldThrowIfsAlreadyExistsException_whenIfsAlreadyExists() {
            // Arrange
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(true);
            when(breadRepository.existsByIfs(bread.getIfs())).thenReturn(true);

            // Act
            Runnable result = () -> breadService.save(request);

            // Assert
            assertThrows(IfsAlreadyExistsException.class, result::run);

            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
            verify(breadRepository, times(1)).existsByIfs(bread.getIfs());
        }

        @Test
        @DisplayName("save(BreadRequestDto) -> should throw PublicationYearTooNew when publication year is too new")
        void save_shouldThrowPublicationYearTooNew_whenPublicationYearIsTooNew() {
            // Arrange
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(true);
            when(breadRepository.existsByIfs(bread.getIfs())).thenReturn(false);
            request.setPublicationYear(LocalDate.now().getYear() + 1);

            // Act
            Runnable result = () -> breadService.save(request);

            // Assert
            assertThrows(PublicationYearTooNew.class, result::run);

            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
            verify(breadRepository, times(1)).existsByIfs(bread.getIfs());
        }

        @Test
        @DisplayName("update(UUID, BreadRequestDto) -> should return updated BreadResponseDto")
        void update_shouldReturnUpdatedBread() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.of(bread));
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(true);
            when(breadRepository.existsByIfsAndIdNot(bread.getIfs(), bread.getId())).thenReturn(false);
            when(mapper.updateEntity(bread, request)).thenReturn(bread);
            when(breadRepository.save(bread)).thenReturn(bread);
            when(mapper.toResponse(bread)).thenReturn(response);

            // Act
            BreadResponseDto result = breadService.update(bread.getId(), request);

            // Assert
            assertNotNull(result);
            assertEquals(response, result);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
            verify(breadRepository, times(1)).existsByIfsAndIdNot(bread.getIfs(), bread.getId());
            verify(mapper, times(1)).updateEntity(bread, request);
            verify(breadRepository, times(1)).save(bread);
            verify(mapper, times(1)).toResponse(bread);
        }

        @Test
        @DisplayName("update(UUID, BreadRequestDto) -> should throw EntityNotFoundException when bread not found")
        void update_shouldThrowEntityNotFoundException_whenBreadNotFound() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.empty());

            // Act
            Runnable result = () -> breadService.update(bread.getId(), request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(breadRepository, times(1)).findById(bread.getId());
        }

        @Test
        @DisplayName("update(UUID, BreadRequestDto) -> should throw EntityNotFoundException when baker not found")
        void update_shouldThrowEntityNotFoundException_whenBakerNotFound() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.of(bread));
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(false);

            // Act
            Runnable result = () -> breadService.update(bread.getId(), request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
        }

        @Test
        @DisplayName("update(UUID, BreadRequestDto) -> should throw EntityNotFoundException when bakery not found")
        void update_shouldThrowEntityNotFoundException_whenBakeryNotFound() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.of(bread));
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(false);

            // Act
            Runnable result = () -> breadService.update(bread.getId(), request);

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
        }

        @Test
        @DisplayName("update(UUID, BreadRequestDto) -> should throw IfsAlreadyExistsException when ifs already exists")
        void update_shouldThrowIfsAlreadyExistsException_whenIfsAlreadyExists() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.of(bread));
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(true);
            when(breadRepository.existsByIfsAndIdNot(bread.getIfs(), bread.getId())).thenReturn(true);

            // Act
            Runnable result = () -> breadService.update(bread.getId(), request);

            // Assert
            assertThrows(IfsAlreadyExistsException.class, result::run);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
            verify(breadRepository, times(1)).existsByIfsAndIdNot(bread.getIfs(), bread.getId());
        }

        @Test
        @DisplayName("update(UUID, BreadRequestDto) -> should throw PublicationYearTooNew when publication year is too new")
        void update_shouldThrowPublicationYearTooNew_whenPublicationYearIsTooNew() {
            // Arrange
            when(breadRepository.findById(bread.getId())).thenReturn(Optional.of(bread));
            when(bakerService.existsById(bread.getBaker().getId())).thenReturn(true);
            when(bakeryService.existsById(bread.getBakery().getId())).thenReturn(true);
            when(breadRepository.existsByIfsAndIdNot(bread.getIfs(), bread.getId())).thenReturn(false);
            request.setPublicationYear(LocalDate.now().getYear() + 1);

            // Act
            Runnable result = () -> breadService.update(bread.getId(), request);

            // Assert
            assertThrows(PublicationYearTooNew.class, result::run);

            verify(breadRepository, times(1)).findById(bread.getId());
            verify(bakerService, times(1)).existsById(bread.getBaker().getId());
            verify(bakeryService, times(1)).existsById(bread.getBakery().getId());
            verify(breadRepository, times(1)).existsByIfsAndIdNot(bread.getIfs(), bread.getId());
        }

        @Test
        @DisplayName("deleteById(UUID) -> should delete bread")
        void deleteById_shouldDeleteBread() {
            // Arrange
            when(breadRepository.existsById(bread.getId())).thenReturn(true);

            // Act
            breadService.deleteById(bread.getId());

            // Assert
            verify(breadRepository, times(1)).existsById(bread.getId());
            verify(breadRepository, times(1)).deleteById(bread.getId());
        }

        @Test
        @DisplayName("deleteById(UUID) -> should throw EntityNotFoundException when bread not found")
        void deleteById_shouldThrowEntityNotFoundException_whenBreadNotFound() {
            // Arrange
            when(breadRepository.existsById(bread.getId())).thenReturn(false);

            // Act
            Runnable result = () -> breadService.deleteById(bread.getId());

            // Assert
            assertThrows(EntityNotFoundException.class, result::run);

            verify(breadRepository, times(1)).existsById(bread.getId());
        }
    }
}