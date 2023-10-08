package com.devs2blu.br.com.simpleBakery.domains.bakery;

import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bakery.dto.BakeryResponseDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bakery")
@RequiredArgsConstructor
public class BakeryController {
    private final BakeryService bakeryService;

    @GetMapping
    public ResponseEntity<List<BakeryResponseDto>> findAll() {
        return ResponseEntity.ok(bakeryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BakeryResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bakeryService.findById(id));
    }

    @GetMapping("/{id}/breads")
    public ResponseEntity<List<BreadResponseDto>> findBreadsByBakeryId(@PathVariable UUID id) {
        return ResponseEntity.ok(bakeryService.findBreadsByBakeryId(id));
    }

    @PostMapping
    public ResponseEntity<BakeryResponseDto> save(@RequestBody @Valid BakeryRequestDto bakery) {
        return ResponseEntity.ok(bakeryService.save(bakery));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BakeryResponseDto> update(@PathVariable UUID id, @RequestBody @Valid BakeryRequestDto bakery) {
        return ResponseEntity.ok(bakeryService.update(id, bakery));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bakeryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
