/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Random;
public class cadenaAletoria {
    public static String generarCadena(int longitud) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(longitud * 5, random).toString(32);
    }
    
    public static String generarNumeros(int longitud){
        String numeros = "";
        Random rnd = new Random();

        for (int i = 0; i < longitud; i++)
                numeros += rnd.nextInt(10);

        return numeros;
    }
}
