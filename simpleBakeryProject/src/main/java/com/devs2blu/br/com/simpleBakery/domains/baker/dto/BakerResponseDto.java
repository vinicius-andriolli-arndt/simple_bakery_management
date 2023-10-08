package com.devs2blu.br.com.simpleBakery.domains.baker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BakerResponseDto {
    private UUID id;
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private String nationality;
}
