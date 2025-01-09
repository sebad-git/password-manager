package org.uy.sdm.pasman.util.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class Jackson {

    private static final ObjectMapper YML_MAPPER = newYmlMapper();
    private static final ObjectMapper OBJECT_MAPPER = newObjectMapper();
    private static final ObjectMapper MINIMAL_OBJECT_MAPPER = newMinimalObjectMapper();
    private static final ObjectMapper NON_NULL_OBJECT_MAPPER = newObjectMapperNonNull();

    private Jackson() {}

    /** Returns a Mapper for YML files. */
    private static ObjectMapper newYmlMapper() {
        return configure(new ObjectMapper(new YAMLFactory()));
    }

    /** Returns a Mapper which serializes ALL fields -- even null value ones. */
    private static ObjectMapper newObjectMapper() {
        return configure(new ObjectMapper());
    }

    /** Returns a minimal object mapper. */
    private static ObjectMapper newMinimalObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new GuavaModule());

        return mapper;
    }

    /**
     * Returns a Mapper which doesn't serialize NULL value fields. Used to reduce bandwidth for data
     * returned over HTTP.
     */
    private static ObjectMapper newObjectMapperNonNull() {
        final ObjectMapper mapper = configure(new ObjectMapper());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }

    public static ObjectMapper configure(ObjectMapper mapper) {
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new AfterburnerModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ZonedDateTimeModule());
        mapper.registerModule(new LocalDateModule());
        mapper.registerModule(new LocalTimeModule());
        mapper.registerModule(new YearMonthModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        return mapper;
    }

    public static ObjectMapper getYmlMapper() {
        return YML_MAPPER;
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper getMinimalObjectMapper() {
        return MINIMAL_OBJECT_MAPPER;
    }

    public static ObjectMapper getNonNullObjectMapper() {
        return NON_NULL_OBJECT_MAPPER;
    }
}
