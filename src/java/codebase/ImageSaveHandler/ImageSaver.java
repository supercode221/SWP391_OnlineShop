/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codebase.ImageSaveHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 *
 * Hoang Anh Dep Trai
 */
public class ImageSaver {

    public static String saveBase64toImageFile(String base64Image, String outputDirectory, String fileName) {
        try {
            // Ensure the directory exists
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();  // Create directories if they don't exist
                if (!created) {
                    System.err.println("Failed to create directory: " + directory.getAbsolutePath());
                    return null;  // Return null if the directory could not be created
                }
            }

            // Split the Base64 string into metadata and the data
            String[] parts = base64Image.split(",");
            if (parts.length != 2) {
                System.err.println("Invalid Base64 input.");
                return null;  // Return null if the Base64 input is invalid
            }

            // Extract the image type (file extension)
            String metadata = parts[0];
            String imageData = parts[1];
//            String fileType = getFileTypeFromMetadata(metadata);
            String fileType = "jpeg";
            if (fileType == null) {
                System.err.println("Could not determine file type.");
                return null;  // Return null if the file type couldn't be determined
            }

            // Use the provided file name and append the file type extension
            String outputFileName = fileName + "." + fileType;
            String outputPath = outputDirectory + "/" + outputFileName;

            // Decode Base64 string
            byte[] imageBytes = Base64.getDecoder().decode(imageData);

            // Write bytes to the file
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(imageBytes);
                fos.flush();
            }
            
            String basePath = "asset";

            // Adjust the output path to return relative path
            String relativePath = outputPath.replaceFirst(".*" + basePath, basePath);  // Replace 'web/' with an empty string
            return relativePath;  // Return the relative path of the saved file
        } catch (IOException e) {
            System.err.println("Failed to save file!");
            return null;  // Return null if there's an IO exception
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 input!");
            return null;  // Return null if the Base64 string is invalid
        }
    }

    private static String getFileTypeFromMetadata(String metadata) {
        // Metadata is like "data:image/png;base64,"
        String[] metadataParts = metadata.split(";");
        if (metadataParts.length > 0) {
            String type = metadataParts[0].split("/")[1];
            return type; // "png", "jpeg", etc.
        }
        return null;
    }
}
