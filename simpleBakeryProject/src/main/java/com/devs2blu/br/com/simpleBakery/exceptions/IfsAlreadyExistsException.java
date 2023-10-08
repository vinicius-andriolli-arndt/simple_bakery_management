package com.devs2blu.br.com.simpleBakery.exceptions;

import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;

public class IfsAlreadyExistsException extends RuntimeException {
    public IfsAlreadyExistsException() {
        super(ErrorMessages.BREAD_IFS_EXISTS);
    }
}
