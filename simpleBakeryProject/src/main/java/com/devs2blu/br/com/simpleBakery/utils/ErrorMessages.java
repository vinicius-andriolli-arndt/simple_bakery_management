package com.devs2blu.br.com.simpleBakery.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    // Validation
    public final String VALIDATION_ERROR = "Erro de validação";
    public final String INVALID_PATH_PARAMETER = "Parâmetro de rota inválido";

    // Baker
    public final String BAKER_NOT_FOUND = "Padeiro não encontrado";

    // Bread
    public final String BREAD_NOT_FOUND = "Livro não encontrado";
    public final String BREAD_IFS_EXISTS = "IFS já cadastrado";
    public final String BREAD_PUBLICATION_YEAR_TOO_NEW = "Ano de publicação maior que o ano atual";

    // Bakery
    public final String BAKERY_NOT_FOUND = "Padaria não encontrada";
    public final String BAKERY_NAME_EXISTS = "Nome da padaria já cadastrado";
}
