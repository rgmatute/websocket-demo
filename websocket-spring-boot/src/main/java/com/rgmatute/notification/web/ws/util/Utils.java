package com.rgmatute.notification.web.ws.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(Utils.class);

    public static String currentDate(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        return (format.format(localDateTime));
    }

    /**
     * Acepta cualquier tipo de Expresion regular
     */
    public static boolean isMatch(String value, String PatternRegEx) {
        Pattern pattern = Pattern.compile(PatternRegEx);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
        // return value.matches(PatternRegEx);
    }

    public static String logLocation() {
        StackTraceElement[] current = Thread.currentThread().getStackTrace();
        return " - Location: " + current[2].getClassName() + "::" + current[2].getMethodName() + " - Line: " + current[2].getLineNumber();
    }

    public static String logLocation(Object message) {
        StackTraceElement[] current = Thread.currentThread().getStackTrace();
        return (
            message +
            " - Location: " +
            current[2].getClassName() +
            "::" +
            current[2].getMethodName() +
            " - Line: " +
            current[2].getLineNumber()
        );
    }

    /* Console */
    public static void console(Object message) {
        println(characterGenerate("*", 40));
        println(message);
        println(characterGenerate("*", 40));
    }

    public static void console(String title, Object message) {
        consoleTitle(title, message);
    }

    public static void consoleTitle(String title, Object message) {
        println(characterGenerate("*", 20) + title + characterGenerate("*", 20));
        println(message);
        println(characterGenerate("*", (40 + title.length())));
    }

    public static void consoleTitle(String title, Object message, int lengthCharacter) {
        println(characterGenerate("*", (lengthCharacter / 2)) + title + characterGenerate("*", (lengthCharacter / 2)));
        println(message);
        println(characterGenerate("*", (lengthCharacter + title.length())));
    }

    public static void println(Object message) {
        System.out.println(message);
    }

    public static String characterGenerate(String character, int length) {
        String r = "";
        for (int i = 0; i < length; i++) {
            r += character;
        }
        return r;
    }

    public static String fillWithZeros(int start, int end, int value) {
        // String.format("%010d00" , value);
        return String.format("%0" + start + "d" + "0".repeat(end), value);
    }

    public static String fillWithZerosLeft(int value) {
        return String.format("%06d", value);
    }

    public static <T> T jsonToEntity(String json, Class<T> entity) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, entity);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> String toJson(Object entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{}";
        try {
            json = objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String generateUuid() {
        // StartTime
        startTime = currentDate("dd/MM/yyyy HH:mm:ss.SSS");
        // UUID
        String uuid = UUID.randomUUID().toString();
        ThreadContext.put("sid", uuid);
        return uuid;
    }

    public static String getUuid() {
        return ThreadContext.get("sid");
    }

    public static String startTime;
    public static String endTime;

    public static String startTime() {
        return startTime;
    }

    public static String endTime() {
        return endTime = currentDate("dd/MM/yyyy HH:mm:ss.SSS");
    }

    public static String fileToBase64(String filePath) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        String file = java.util.Base64.getEncoder().encodeToString(bytes);
        return file;
    }

    public static String fileBytesToBase64(byte[] fileBytes) {
        String base64 = java.util.Base64.getEncoder().encodeToString(fileBytes);
        return base64;
    }

    public static boolean fileStore(String path, byte[] fileBytes) {
        try {
            Files.write(Paths.get(path), fileBytes);
        } catch (IOException e) {
            return false;
        }
        if (!Files.exists(Paths.get(path))) {
            return false;
        }
        return true;
    }

    public static String inputStreamToBase64(InputStream in, long length) throws IOException {
        byte[] imageBytes = new byte[(int) length];
        in.read(imageBytes, 0, imageBytes.length);
        in.close();
        return java.util.Base64.getEncoder().encodeToString(imageBytes);
    }

    public static String inputStreamToBase64(InputStream in) {
        try {
            return java.util.Base64.getEncoder().encodeToString(in.readAllBytes());
        } catch (IOException e) {
            return "";
        }
    }

    public static InputStream byteToinputStream(byte[] inBytes) {
        InputStream in = new ByteArrayInputStream(inBytes);
        return in;
    }

    public static InputStream base64ToInputStream(String base64) {
        byte inBytes[] = java.util.Base64.getDecoder().decode(base64);
        InputStream in = new ByteArrayInputStream(inBytes);
        return in;
    }

    @SafeVarargs
    public static <T> List<T> asList(T... a) {
        return Arrays.asList(a);
    }
}
