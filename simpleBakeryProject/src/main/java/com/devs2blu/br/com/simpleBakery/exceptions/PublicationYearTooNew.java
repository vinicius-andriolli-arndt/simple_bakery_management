package com.devs2blu.br.com.simpleBakery.exceptions;

import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;

public class PublicationYearTooNew extends RuntimeException {
    public PublicationYearTooNew() {
        super(ErrorMessages.BREAD_PUBLICATION_YEAR_TOO_NEW);
    }
}
