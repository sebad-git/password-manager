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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public ZonedDateTimeModule() {
        this.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());
        this.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
    }

    private static class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

        @Override
        public ZonedDateTime deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
        ) throws IOException {
            return ZonedDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }
    }

    private static class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

        @Override
        public void serialize(
            ZonedDateTime zonedDateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
        ) throws IOException {
            jsonGenerator.writeString(DateUtils.formatZonedDateTime(zonedDateTime));
        }
    }
}
