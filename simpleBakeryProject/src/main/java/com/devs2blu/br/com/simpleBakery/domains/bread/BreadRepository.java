package com.devs2blu.br.com.simpleBakery.domains.bread;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BreadRepository extends JpaRepository<Bread, UUID> {
    boolean existsByIfs(String ifs);

    boolean existsByIfsAndIdNot(String ifs, UUID id);

    Optional<Bread> findByIfs(String ifs);

    List<Bread> findByFlavorContainingIgnoreCase(String flavor);

    @NonNull
    Page<Bread> findAll(@NonNull Pageable pageable);
}
