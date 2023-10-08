package com.devs2blu.br.com.simpleBakery.domains.baker;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.devs2blu.br.com.simpleBakery.domains.bread.Bread;

@Entity
@Table(name = "bakers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Baker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String nationality;

    @OneToMany(mappedBy = "baker", orphanRemoval = true)
    private List<Bread> breads;
}
