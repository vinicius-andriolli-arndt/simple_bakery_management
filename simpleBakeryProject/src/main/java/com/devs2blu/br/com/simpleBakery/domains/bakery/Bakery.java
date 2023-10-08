package com.devs2blu.br.com.simpleBakery.domains.bakery;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

import com.devs2blu.br.com.simpleBakery.domains.bread.Bread;

@Entity
@Table(name = "bakeries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bakery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String address;

    @Column(nullable = false, length = 16)
    private String phoneNumber;

    @OneToMany(mappedBy = "bakeries", orphanRemoval = true)
    private List<Bread> breads;
}
