package com.policykart.member.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class ObjectMapperUtils {
    private final Logger logger = LoggerFactory.getLogger(ObjectMapperUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> String convertToJsonString(T jsonObject) {
        try {
            return mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            logger.error("Unable to serialize json object : {}", jsonObject, e);
            return null;
        }
    }

    public static <T> T convertToObject(String s, Class<T> type) {
        try {
            return mapper.readValue(s, type);
        } catch (JsonProcessingException e) {
            logger.error("Unable to deserialize json string : {} to type : {}", s, type, e);
            return null;
        }
    }

    public static <T> T convert(Object from, Class<T> to) {
        try {
            return mapper.convertValue(from, to);
        } catch (Exception e) {
            logger.error("Unable to convert object : {} to type : {}", from, to, e);
            return null;
        }
    }
}
