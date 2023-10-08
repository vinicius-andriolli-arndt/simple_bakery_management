package com.devs2blu.br.com.simpleBakery.domains.baker;

import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.baker.dto.BakerResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/baker")
@RequiredArgsConstructor
public class BakerController {
    private final BakerService bakerService;

    @GetMapping
    public ResponseEntity<List<BakerResponseDto>> findAll() {
        return ResponseEntity.ok(bakerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BakerResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bakerService.findById(id));
    }

    @GetMapping("/{id}/bread")
    public ResponseEntity<List<BreadResponseDto>> findBooksByAuthorId(@PathVariable UUID id) {
        return ResponseEntity.ok(bakerService.findBreadsByBakerId(id));
    }

    @PostMapping
    public ResponseEntity<BakerResponseDto> save(@RequestBody @Valid BakerRequestDto baker) {
        return ResponseEntity.ok(bakerService.save(baker));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BakerResponseDto> update(@PathVariable UUID id, @RequestBody @Valid BakerRequestDto baker) {
        return ResponseEntity.ok(bakerService.update(id, baker));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
    	bakerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
