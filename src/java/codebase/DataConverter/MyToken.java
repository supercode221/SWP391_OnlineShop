package codebase.DataConverter;

import codebase.hashFunction.SHA256_Encryptor;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MyToken class represents a simple token structure with a header, payload,
 * and signature. The header and payload are expected to be JSON objects, while
 * the signature is generated based on the header, payload, and a secret key.
 * This class provides constructors for creating tokens and methods to retrieve
 * their components.
 *
 * It uses the JSONObject class to handle JSON data for the header and payload.
 * The signature is created by concatenating the header, payload, and a secret
 * key.
 *
 */
public class MyToken {

    private String header;
    private String payload;
    private String signature;

    // any
    private final String secretKey = "How? How can you read this key?";
    /**
     * Primary Regular Expression to split parts Must be non of characters
     * base64 already taken Base64: [A-Z][a-z][0-9]+/=
     */
    private final String primaryRegex = ".";
    /**
     * Secondary Regular Expression to split parts Must be non of characters
     * base64 already taken and not same with primaryRegex Base64:
     * [A-Z][a-z][0-9]+/=
     */
    private final String secondaryRegex = ",";
    
    /**
     * In case of some Exception, we use this fake token
     */
    private final String defaultToken = "uTiQDxIc0DV2kUyBbSaVE56Gl0KmDb8eDHSKU6YBFOlxJl0X5DmCOMlhfmOI6UMr.eoy2hxlBH3xKIQ9lPQW638VLpsf9ZZbJiLHT8YyzRqmLMYEDTp6vL9Xv9Wf01Mpe.pWkJqhRGjZM8Bshh2CfUpwv7wFwKcu0vxpTa6FpP8PJ1ayjofVGuh4DWBGOJ2Pqs";

    /**
     * Default constructor. Creates an empty MyToken instance.
     */
    public MyToken() {
        this.header = "{}";
        this.payload = "{}";
        this.signature = "FREEZETOKEN{}";
    }

    /**
     * Constructor that accepts JSON objects for both the header and payload.
     * The signature is generated using the provided header and payload.
     *
     * @param header the JSON object representing the token's header
     * @param payload the JSON object representing the token's payload
     */
    public MyToken(JSONObject header, JSONObject payload) {
        this.header = header.toString();
        this.payload = payload.toString();
        try {
            this.signature = this.createSignature(this.header, this.payload);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName())
                    .log(Level.WARNING, "How the fvck can this log is printed?", e);
        }
    }

    /**
     * Constructor that accepts only the payload as a JSON object. The header is
     * initialized as an empty JSON object, and the signature is generated using
     * the provided payload and an empty header.
     *
     * @param payload the JSON object representing the token's payload
     */
    public MyToken(JSONObject payload) {
        this.header = "{}"; // Empty JSON object as default header
        this.payload = payload.toString();
        try {
            this.signature = createSignature(this.header, this.payload);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName())
                    .log(Level.WARNING, "How the fvck can this log is printed?", e);
        }
    }

    /**
     * Generates the signature by concatenating the header, payload, and the
     * secret key.
     *
     * @param header the token's header as a string
     * @param payload the token's payload as a string
     * @return the generated signature for the token
     */
    private String createSignature(String header, String payload) throws Exception {
        if (this.isEmptyPayload()) {
            throw new Exception("Empty Payload field to create Signature!");
        }
        return SHA256_Encryptor.sha256Hash("FREEZETOKEN{"
                + header + this.primaryRegex
                + payload + this.secondaryRegex
                + this.secretKey + '}');
    }

    /**
     * Retrieves the token's header.
     *
     * @return the header as a string
     */
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrimaryRegex() {
        return primaryRegex;
    }

    /**
     * Retrieves the token's payload.
     *
     * @return the payload as a string
     */
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload.toString();
    }

    /**
     * Retrieves the token's signature.
     *
     * @return the signature as a string
     * @throws java.lang.Exception for empty payload field
     */
    public String getSignature() throws Exception {
        return this.createSignature(this.header, this.payload);
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Checks if the token's header is empty or set to "{}".
     *
     * @return true if the header is empty or set to "{}"; false otherwise
     */
    public boolean isEmptyHeader() {
        return this.header == null
                || this.header.isBlank()
                || this.header.isEmpty()
                || this.header.equalsIgnoreCase("{}");
    }

    /**
     * Checks if the token's payload is empty or set to "{}".
     *
     * @return true if the payload is empty or set to "{}"; false otherwise
     */
    public boolean isEmptyPayload() {
        return this.payload == null
                || this.payload.isBlank()
                || this.payload.isEmpty()
                || this.payload.equalsIgnoreCase("{}");
    }

    /**
     * To get default token.
     * That's mostly fake token.
     * 
     * @return private final String defaultToken
     */
    public String getDefaultToken() {
        return defaultToken;
    }

    /**
     * Generates the full token by encoding the header, payload, and signature
     * in Base64.
     *
     * @return the Base64-encoded token string
     * @throws Exception if the payload is empty
     */
    public String getToken() throws Exception {
        if (this.isEmptyPayload()) {
            throw new Exception("Empty Payload field to get Token!");
        }
        if (this.isEmptyHeader()) {
            this.header = "{}";
        }
        String encodedHeader = MyBase64.encodeString(this.header);
        String encodedPayload = MyBase64.encodeString(this.payload);
        this.signature = this.createSignature(this.header, this.payload);
        String encodedSignature = MyBase64.encodeString(SHA256_Encryptor.sha256Hash(this.signature));
        return encodedHeader + this.primaryRegex + encodedPayload + this.primaryRegex + encodedSignature;
    }

    /**
     * Validates the given token string by comparing it with the calculated
     * signature.
     *
     * @param checkToken the token string to validate
     * @return true if the token is valid; false otherwise
     */
    public boolean isValidToken(String checkToken) {
        String[] splitStream = checkToken.split('[' + this.primaryRegex + ']');

        if (splitStream.length != 3) {
            return false;
        }

        String encodedHeader = splitStream[0];
        String encodedPayload = splitStream[1];
        String encodedSignature = splitStream[2];

        String recalculateSignature = null;

        MyToken checkingToken = new MyToken();

        try {
            checkingToken.setHeader(MyBase64.decodeString(encodedHeader));
            checkingToken.setPayload(MyBase64.decodeString(encodedPayload));

            recalculateSignature = checkingToken.getToken();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Exception in check valid token", ex);
            return false;
        }

        return recalculateSignature.equals(checkToken);
    }

    // Test case in main function
    public static void main(String[] args) throws Exception {
        // Test creating token
        JSONObject headerJson = new JSONObject();
        headerJson.put("shop", "FREEZE");
        headerJson.put("type", "Token");

        JSONObject payloadJson = new JSONObject();
        payloadJson.put("id", "1");
        payloadJson.put("name", "Hoang Anh");
        payloadJson.put("role", "0");

        MyToken token = new MyToken(headerJson, payloadJson);
        String generatedToken = token.getToken();
        System.out.println("Generated Token: " + generatedToken);

        // Test validating token
        boolean isValid = token.isValidToken(generatedToken);
        System.out.println("Is token valid? " + isValid);
        System.out.println();

        // Test creating token
        JSONObject secondHeaderJson = new JSONObject();
        secondHeaderJson.put("shop", "FREEZE");
        secondHeaderJson.put("type", "Token");

        JSONObject secondPayloadJson = new JSONObject();
        secondPayloadJson.put("id", "1");
        secondPayloadJson.put("name", "Hoang Anh");
        secondPayloadJson.put("role", "99");

        MyToken secondToken = new MyToken(secondHeaderJson, secondPayloadJson);
        String secondGeneratedToken = secondToken.getToken();
        System.out.println("Second Generated Token: " + secondGeneratedToken);

        // Test validating token
        boolean secondIsValid = secondToken.isValidToken(secondGeneratedToken);
        System.out.println("Is token valid? " + secondIsValid);
        System.out.println();

        // Test creating token
        JSONObject thirdHeaderJson = new JSONObject();
        thirdHeaderJson.put("shop", "FREEZE");
        thirdHeaderJson.put("type", "Token");

        JSONObject thirdPayloadJson = new JSONObject();
        thirdPayloadJson.put("id", "1");
        thirdPayloadJson.put("name", "Hoang Anh");
        thirdPayloadJson.put("role", "99");

        MyToken thirdToken = new MyToken(thirdHeaderJson, thirdPayloadJson);
        String thirdGeneratedToken = "eyJzaG9wIjoiRlJFRVpFIiwidHlwZSI6IlRva2VuIn0="
                + ".eyJyb2xlIjoiOTkiLCJuYW1lIjoiSG9hbmcgQW5oIiwiaQiOiIxIn0="
                + ".NTRmYzFjYjQ5NWVlOGRkMDZhY2M3MGEwYTA0MDc2YzRmMDZlNGMyNzdjZGRkZjdmY2Q4YzA4Mzk2N2ZmNmI3ZQ==";
        System.out.println("Third Generated Token: " + thirdGeneratedToken);

        // Test validating token
        boolean thirdIsValid = thirdToken.isValidToken(thirdGeneratedToken);
        System.out.println("Is token valid? " + thirdIsValid);
    }

}
