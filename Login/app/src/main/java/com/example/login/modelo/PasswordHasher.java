package com.example.login.modelo;

import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    // Método para generar un salt aleatorio
    public static String generarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];  // Genera un salt de 16 bytes
        random.nextBytes(salt);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(salt);  // Devuelve el salt codificado en Base64
        }
        return null;
    }

    // Método para hashear una contraseña con SHA-256 y salt
    public static String hashPassword(String password, String salt) {
        try {
            // Crear instancia de MessageDigest para SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Combinar la contraseña con el salt
            String passwordConSalt = password + salt;

            // Aplicar el hash
            byte[] hashBytes = md.digest(passwordConSalt.getBytes());

            // Convertir el hash (array de bytes) a una representación hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();  // Devuelve el hash en formato hexadecimal
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para verificar si la contraseña ingresada coincide con la almacenada
    public static boolean verificarPassword(String passwordIngresada, String hashAlmacenado, String saltAlmacenado) {
        // Hashear la contraseña ingresada con el salt almacenado
        String hashCalculado = hashPassword(passwordIngresada, saltAlmacenado);

        // Comparar el hash calculado con el hash almacenado
        return hashCalculado.equals(hashAlmacenado);
    }
}
