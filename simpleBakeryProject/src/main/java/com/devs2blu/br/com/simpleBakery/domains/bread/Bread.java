package com.devs2blu.br.com.simpleBakery.domains.bread;

import com.devs2blu.br.com.simpleBakery.domains.baker.Baker;
import com.devs2blu.br.com.simpleBakery.domains.bakery.Bakery;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "breads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bread {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 13)
    private String ifs;

    @Column(nullable = false)
    private String flavor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, length = 4)
    private Integer publicationYear;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baker_id", nullable = false)
    private Baker baker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bakery_id", nullable = false)
    private Bakery bakery;
}
