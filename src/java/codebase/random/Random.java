/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codebase.random;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Random extends java.util.Random {

    /**
     * 
     * to get a random string with custom length and characterList
     * 
     * @param length output string length, must be greater than 0, 
     * if not, the return string will be null
     * 
     * @param characterList list of characters to be get random into output string, 
     * default is "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" 
     * if characterList contains only space or null or is empty string
     * 
     * @return string with custom length and character list
     */
    public String getString(int length, String characterList) {
        if (length < 1) {
            return null;
        }
        if (characterList == null
                || characterList.isEmpty()
                || characterList.isBlank()) {
            characterList = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        String toRet = new String();
        for (int i = 0; i < length; i++) {
            toRet += characterList.charAt(this.nextInt(characterList.length()));
        }
        return toRet;
    }
    
    /**
     * 
     * to get a random integer in range of [min; max]
     *
     * @param min min output integer
     * @param max max output integer
     * @return an integer in [min; max]
     */
    public int getInt(int min, int max) {
        return min + this.nextInt(max - min) + 1;
    }
    
    /**
     * 
     * to get a random integer with Long size in range of [min; max]
     *
     * @param min min output Long size integer
     * @param max max output Long size integer
     * @return an integer in [min; max] in Long size
     */
    public long getLong(long min, long max) {
        return min + this.nextLong(max - min) + 1;
    }

    /**
     * Test function
     */
    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.getString(6, ""));
        System.out.println(random.getString(6, "0123456789"));
        System.out.println(random.getInt(0, 10));
    }

}
