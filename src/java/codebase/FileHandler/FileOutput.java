/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package codebase.FileHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class FileOutput {
    
    public static void writeFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        }
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
        
    }

}
