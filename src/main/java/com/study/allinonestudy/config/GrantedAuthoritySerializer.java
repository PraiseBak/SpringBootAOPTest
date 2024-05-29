package com.study.allinonestudy.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

public class GrantedAuthoritySerializer extends JsonSerializer<List<GrantedAuthority>> {

    @Override
    public void serialize(List<GrantedAuthority> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        for (GrantedAuthority v : value){
            gen.writeString(v.getAuthority());
        }

    }
}

