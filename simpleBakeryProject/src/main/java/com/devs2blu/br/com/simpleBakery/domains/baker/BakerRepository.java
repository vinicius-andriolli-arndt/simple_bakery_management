package com.devs2blu.br.com.simpleBakery.domains.baker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BakerRepository extends JpaRepository<Baker, UUID> {
}
