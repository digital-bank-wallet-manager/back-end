package com.prog4.digitalbank.CrudOperations;

public class CamelCaseToSnakeCase {
    public static String convertToSnakeCase(String string){
        StringBuilder snakeCase = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isUpperCase(c)) {
                snakeCase.append('_').append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }
        return snakeCase.toString();
    }
}

