package com.devs2blu.br.com.simpleBakery.exceptions;

import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;

public class BakeryNameAlreadyExists extends RuntimeException {
    public BakeryNameAlreadyExists() {
        super(ErrorMessages.BAKERY_NAME_EXISTS);
    }
}
