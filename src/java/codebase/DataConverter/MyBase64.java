package codebase.DataConverter;

import java.util.Base64;

/**
 * A utility class for Base64 encoding and decoding of strings and byte arrays, 
 * with additional functionality for generating Base64-encoded HTML image source strings.
 */
public class MyBase64 {

    /**
     * Encodes a string to Base64 format.
     * 
     * @param input the input string to encode
     * @return the Base64-encoded string
     */
    public static String encodeString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    /**
     * Decodes a Base64-encoded string back to its original string format.
     * 
     * @param input the Base64-encoded string to decode
     * @return the decoded string
     */
    public static String decodeString(String input) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    /**
     * Encodes a byte array (such as an image) to Base64 format.
     * 
     * @param input the byte array to encode
     * @return the Base64-encoded string representation of the byte array
     */
    public static String encodeImage(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * Generates a Base64-encoded image source string for use in an HTML <img> tag.
     * This method prepends the necessary data URL scheme for an image in Base64 format.
     * 
     * @param input the byte array representing the image to encode
     * @return a Base64-encoded image source string for HTML
     */
    public static String getHtmlImageSource(byte[] input) {
        return "data:image/jpeg;base64," + encodeImage(input);
    }

}
