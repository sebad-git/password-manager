package org.uy.sdm.pasman.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public LocalTimeModule() {
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        this.addSerializer(LocalTime.class, new LocalTimeSerializer());
    }

    private static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

        @Override
        public LocalTime deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
        ) throws IOException {
            if (jsonParser.getText().isEmpty()) {
                return null;
            }

            String[] timeSplit = jsonParser.getText().split(":");
            return LocalTime.of(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
        }
    }

    private static class LocalTimeSerializer extends JsonSerializer<LocalTime> {

        @Override
        public void serialize(
            LocalTime localTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
        ) throws IOException {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

            jsonGenerator.writeString(localTime == null ? null : timeFormat.format(localTime));
        }
    }
}
