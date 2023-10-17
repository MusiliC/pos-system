package com.systechafrica.util;

import java.util.logging.Logger;

public class InputSanitizer {
    public static int sanitizeStringToInt(String input, Logger logger){
        try {
            return Integer.parseInt(input);
        }catch (Exception e){
            logger.severe("Input sanitization failed: "+e.getMessage());
            return -1;
        }
    }
    public static double sanitizeStringToDouble(String input, Logger logger){
        try {
            return Double.parseDouble(input);
        }catch (Exception e){
            logger.severe("Input sanitization failed: "+e.getMessage());
            return -1.0;
        }
    }
}
