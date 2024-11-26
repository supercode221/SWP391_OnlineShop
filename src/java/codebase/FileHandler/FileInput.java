/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package codebase.FileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class FileInput {
    
    public String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    /**
     * using logger
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * java.util.logging.Logger to log issues or debug
     * 
     * @param level java.util.loggin.Level
     * @param msg optional mesage
     * @param e optional exception
     */
    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }
    
    /**
     * Mostly just a test function
     * 
     * @param args 
     */
    public static void main(String[] args) {
        String filePathTest = "Status\\UserStatus\\UserStatus.txt";
    
        String fileContent = null;
        try {
            fileContent = new FileInput().readFile(filePathTest);
        } catch (IOException ex) {
            System.err.println("Error!");
        }
        
        System.out.println(fileContent);
    }

}
