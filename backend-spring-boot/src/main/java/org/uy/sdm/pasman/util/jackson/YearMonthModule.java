package org.uy.sdm.pasman.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.YearMonth;

public class YearMonthModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public YearMonthModule() {
        this.addDeserializer(YearMonth.class, new YearMonthDeserializer());
        this.addSerializer(YearMonth.class, new YearMonthSerializer());
    }

    private static class YearMonthDeserializer extends JsonDeserializer<YearMonth> {

        @Override
        public YearMonth deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
        ) throws IOException {
            if (jsonParser.getText().isEmpty()) {
                return null;
            }

            return YearMonth.parse(jsonParser.getText());
        }
    }

    private static class YearMonthSerializer extends JsonSerializer<YearMonth> {

        @Override
        public void serialize(
            YearMonth yearMonth,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
        ) throws IOException {
            jsonGenerator.writeString(yearMonth.toString());
        }
    }
}
