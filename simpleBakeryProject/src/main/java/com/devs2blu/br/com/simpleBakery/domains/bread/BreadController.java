package com.devs2blu.br.com.simpleBakery.domains.bread;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadRequestDto;
import com.devs2blu.br.com.simpleBakery.domains.bread.dto.BreadResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bread")
@RequiredArgsConstructor
public class BreadController {
    private final BreadService breadService;

    @GetMapping
    public ResponseEntity<Page<BreadResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

        return ResponseEntity.ok(breadService.findAll(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreadResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(breadService.findById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BreadResponseDto> findByIfs(@PathVariable String ifs) {
        return ResponseEntity.ok(breadService.findByIfs(ifs));
    }

    @GetMapping("/flavor")
    public ResponseEntity<List<BreadResponseDto>> findByFlavor(@RequestParam String flavor) {
        return ResponseEntity.ok(breadService.findByFlavor(flavor));
    }

    @PostMapping
    public ResponseEntity<BreadResponseDto> save(@RequestBody @Valid BreadRequestDto bread) {
        return ResponseEntity.ok(breadService.save(bread));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreadResponseDto> update(@PathVariable UUID id, @RequestBody @Valid BreadRequestDto bread) {
        return ResponseEntity.ok(breadService.update(id, bread));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
    	breadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
