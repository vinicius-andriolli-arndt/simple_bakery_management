package com.devs2blu.br.com.simpleBakery.domains.bakery.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PhoneNumberSerializer extends JsonSerializer<String> {
    private static final String PHONE_NUMBER_REGEX_WITH_DDD = "(\\d{2})(\\d{5})(\\d{4})";
    private static final String PHONE_NUMBER_REGEX_WITHOUT_DDD = "(\\d{5})(\\d{4})";

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String phoneNumber = s.trim().replaceAll("\\D", "");

        if (phoneNumber.length() == 11) {
            jsonGenerator.writeString(phoneNumber.replaceAll(PHONE_NUMBER_REGEX_WITH_DDD, "($1) $2-$3"));
            return;
        }

        jsonGenerator.writeString(phoneNumber.replaceAll(PHONE_NUMBER_REGEX_WITHOUT_DDD, "$1-$2"));
    }
}
