package com.devs2blu.br.com.simpleBakery.domains.bread.dto;

import com.devs2blu.br.com.simpleBakery.domains.bread.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BreadResponseDto {
    private UUID id;
    private String ifs;

    private String flavor;
    private Type type;

    @JsonProperty("publication_year")
    private Integer publicationYear;

    private Integer quantity;

    @JsonProperty("baker_id")
    private UUID bakerId;

    @JsonProperty("bakery_id")
    private UUID bakeryId;
}
