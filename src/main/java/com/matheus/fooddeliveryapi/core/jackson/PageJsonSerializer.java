package com.matheus.fooddeliveryapi.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(Page<?> page, JsonGenerator generator,
                          SerializerProvider serializerProvider) throws IOException {

        generator.writeStartObject();

        generator.writeObjectField("content", page.getContent());
        generator.writeNumberField("size", page.getSize());
        generator.writeNumberField("totalElements", page.getTotalElements());
        generator.writeNumberField("totalPages", page.getTotalPages());
        generator.writeNumberField("number", page.getNumber());

        generator.writeEndObject();
    }
}
