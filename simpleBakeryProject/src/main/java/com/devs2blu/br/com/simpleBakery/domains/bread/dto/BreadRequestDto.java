package com.devs2blu.br.com.simpleBakery.domains.bread.dto;

import com.devs2blu.br.com.simpleBakery.domains.bread.Type;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BreadRequestDto {
    @NotNull(message = "O IFS não pode ser nulo")
    @NotBlank(message = "O IFS não pode ser vazio")
    @Size(min = 13, max = 13, message = "O IFS deve ter 13 caracteres")
    private String ifs;

    @NotNull(message = "O sabor não pode ser nulo")
    @NotBlank(message = "O sabor não pode ser vazio")
    private String flavor;

    @NotNull(message = "O tipo não pode ser nulo")
    private Type type;

    @NotNull(message = "O ano de publicação não pode ser nulo")
    @Positive(message = "O ano de publicação deve ser positivo")
    @JsonProperty("publication_year")
    private Integer publicationYear;

    @NotNull(message = "A quantidade não pode ser nula")
    @Positive(message = "A quantidade deve ser positiva")
    private Integer quantity;

    @NotNull(message = "O padeiro não pode ser nulo")
    @JsonAlias({"baker_id", "baker"})
    private UUID baker;

    @NotNull(message = "A padaria não pode ser nula")
    @JsonAlias({"bakery_id", "bakery"})
    private UUID bakery;
}
