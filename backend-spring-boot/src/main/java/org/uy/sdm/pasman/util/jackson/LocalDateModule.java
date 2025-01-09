package org.uy.sdm.pasman.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uy.sdm.pasman.util.DateUtils;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;

public class LocalDateModule extends SimpleModule {

    @Serial
	private static final long serialVersionUID = 1L;

    public LocalDateModule() {
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        this.addSerializer(LocalDate.class, new LocalDateSerializer());
    }

    private static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
        ) throws IOException {
            if (jsonParser.getText().isEmpty()) {
                return null;
            }

            String[] dateSplit = jsonParser.getText().split("-");

            return LocalDate.of(
                Integer.parseInt(dateSplit[0]),
                Integer.parseInt(dateSplit[1]),
                Integer.parseInt(dateSplit[2])
            );
        }
    }

    private static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(
            LocalDate localDate,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
        ) throws IOException {
            jsonGenerator.writeString(DateUtils.convertLocalDateToString(localDate));
        }
    }
}
