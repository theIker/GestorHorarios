/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestorhorarios.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * Clase que se ocupa del tratado de "datos sensibles" en la aplicación. Se ocupa
 * de la encriptación, desencriptación y comprobación de la integridad de los datos.
 * <br><br>
 * No contiene ningún dato en su interior.
 * 
 * @author Pedro
 * 
 */
public class Crypto {
    
    /**
     * 
     * Constructor vacío. Crea un nuevo objeto {@link logic.Crypto} vacio.
     * 
     */
    public Crypto () {
        
        //Empty
        
    }
    /**
     * 
     * Método que se ocupa de comprobar si la contraseña introducida por el usuario
     * en el login se corresponde con la que el usuario tiene registrada en la base
     * de datos como dato encryptado.
     * 
     * Se utiliza el método {@link #getPasswordHash(java.lang.String)} para encriptar la contraseña
     * introducida por el usuario, y se compara ese hash con el guardado en la 
     * base de datos.
     * 
     * @param passwordHash      Hash de la contraseña correspondiente al usuario
     *                          que intenta iniciar sesión.
     * @param tryingPassword    Contraseña que ha introducido el usuario al intentar
     *                          iniciar seisón.
     * @return                  Devuelve si la contraseña introducida por el usuario
     *                          se correponde con la contraseña de dicho usuario en
     *                          la bse de datos.
     */
    public boolean checkPasswordHash (String passwordHash, String tryingPassword) {
        
        boolean equals = true;
        
        if (passwordHash.equals(getPasswordHash(tryingPassword))) {
            
            equals = false;
            
        }
        
        return equals;
        
    }
    /**
     * 
     * Método que devuelve como cadena el hash de una contraseña que se le especifica
     * en su llamada.
     * 
     * Se utiliza un algoritmo de encriptación MD5.
     * 
     * @param password  Contraseña que se va a encriptar.
     * @return          Cadena con el hash de la contraseña.
     */
    public String getPasswordHash (String password) {
        
        MessageDigest md;
        
        try {
            
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            
            byte [] mdMD5 = md.digest();
            
            StringBuilder sb = new StringBuilder();
            
            for (byte bytes: mdMD5) {
                
                sb.append(String.format("%02x", bytes & 0xff));
                
            }
            
            password = sb.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return password;
        
    }
    
    /**
     * 
     * Método que genera automáticamente una contraseña segura.
     * 
     * Genera una contraseña, no un hash de contraseña.
     * 
     * @return Cadena que contiene la contraseña segura.
     */
    public String generateAutomaticPassword () {
        
        String password = "";
        
        Random rand = new Random();
        
        int isLowercase = 0;
        int isUppercase = 0;
        int isNumeric   = 0;
        int isElse      = 0;
        
        do {
            
            int nextFill = (int) (rand.nextFloat() * 4);
        
            switch(nextFill) {

                case 1:

                    if (isLowercase < 2) {

                        password += generateLowercase(rand);
                        isLowercase++;
                        break;
                    }

                case 2:

                    if (isUppercase < 2) {

                        password += generateUppercase(rand);
                        isUppercase++;
                        break;
                    }

                case 3:

                    if (isNumeric < 2) {

                        password += generateNumeric(rand);
                        isNumeric++;
                        break;
                    }

                case 4:

                    if (isElse < 2) {

                        password += generateElse(rand);
                        isElse++;
                        break;
                    }

            }
            
        } while (password.length() < 8);
        
        return password;
        
    }
    
    /**
     * 
     * Método que genera una letra minúscula.
     * 
     * @param rand  Instancia de la variable random.
     * @return      Una letra minúscula.
     */
    private char generateLowercase(Random rand) {
        
        String salt = "abcdefghijklmnopqrstuvwxyz";
        
        return salt.charAt((int) (rand.nextFloat() * salt.length()));
        
    }
    
    /**
     * 
     * Método que genera una letra mayúscula.
     * 
     * @param rand  Instancia de la variable random.
     * @return      Una letra mayúsucula.
     */
    private char generateUppercase(Random rand) {
        
        String salt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        return salt.charAt((int) (rand.nextFloat() * salt.length()));
        
    }
    
    /**
     * 
     * Método que genera un número de un dígito.
     * 
     * @param rand  Instancia de la variable random.
     * @return      Un número de un dígito.
     */
    private char generateNumeric(Random rand) {
        
        String salt = "0123456789";
        
        return salt.charAt((int) (rand.nextFloat() * salt.length()));
        
    }
    
    /**
     * 
     * Método que genera un carácter especial.
     * 
     * @param rand  Instancia de la variable random.
     * @return      Un carácter especial.
     */
    private char generateElse(Random rand) {
        
        String salt = "!·$%&/()=?¿¡";
        
        return salt.charAt((int) (rand.nextFloat() * salt.length()));
        
    }
    
    /**
     * 
     * Método que comprueba si una contraseña es lo suficiéntemene segura. Los
     * requisitos de seguridad son los siguientes:
     * <br>
     * Mayor de 7 dígitos.
     * Contiene letras minúsculas, letras mayúsculas, números y carácteres especiales.
     * 
     * @param password  Contraseña que se va a comprobar.
     * @return          Si la contraseña es lo suficiéntemenete segura o no.
     */
    public boolean checkPasswordSecurity (String password) {
        
        boolean secure = true;
        
        int isLowercase = 0;
        int isUppercase = 0;
        int isNumeric   = 0;
        int isElse      = 0;
        
        if (password.length() < 8) {
            
            secure = false;
            
        } else {
            
            for (int i = 0; i < password.length(); i++) {
                
                char c = password.charAt(i);
                
                if (checkLowercase(c)) {
                    
                    isLowercase++;
                    
                } else if (checkUppercase(c)) {
                    
                    isUppercase++;
                    
                } else if (checkNumeric(c)) {
                    
                    isNumeric++;
                    
                } else {
                    
                    isElse++;
                    
                }
                
            }
            
            if (isLowercase == 0 || isUppercase == 0 || isNumeric == 0 || isElse == 0) {
                
                secure = false;
                
            }
            
        }
        
        return secure;
        
    }

    /**
     * 
     * Método que comprueba si un caracter es una letra minúscula.
     * 
     * @param c Caracter a comprobar.
     * @return  Si el caracter es una letra minúscula o no.
     */
    private boolean checkLowercase(char c) {
    
        return (c >= 'a' && c <= 'z');
        
    }
    
    /**
     * 
     * Método que comprueba si un caracter es una letra mayúscula.
     * 
     * @param c Caracter a comprobar.
     * @return  Si el caracter es una letra mayúscula o no.
     */
    private boolean checkUppercase(char c) {
        
        return (c >= 'A' && c <= 'Z');
        
    }
    
    /**
     * 
     * Método que comprueba si un caracter es un número.
     * 
     * @param c Caracter a comprobar.
     * @return  Si el caracter es un numero o no.
     */
    private boolean checkNumeric(char c) {
        
        return (c >= '0' && c <= '9');
        
    }
    
    /**
     * 
     * Método que encripta los datos del correo electronico que utiliza la aplicación
     * para la mensajería automática. Se carga en un file llamado "mail.mail".
     * 
     */
    public void encryptMailData () {
        
        File file = new File("mail.mail");
        
        String user = "dam.gestion.horarios-Protoc017";
        
        String userKey = "Protoc017";
        
        byte [] salt = "1234567890123456".getBytes();
        
        KeySpec spec = new PBEKeySpec(userKey.toCharArray(), salt, 65536, 128);
        try {
           
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte [] key = factory.generateSecret(spec).getEncoded();
            SecretKey privateKey = new SecretKeySpec (key, 0, key.length, "AES");
        
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte [] encodedMessage = cipher.doFinal(user.getBytes());
            byte [] iv = cipher.getIV();
            
            ObjectOutputStream dos = new ObjectOutputStream(new FileOutputStream (file));
            dos.writeObject(iv);
            dos.writeObject(encodedMessage);
            dos.close();
            
            System.out.print("data was saved");
            
        } catch (NoSuchAlgorithmException  | InvalidKeySpecException |
                 NoSuchPaddingException    | InvalidKeyException     |
                 IllegalBlockSizeException | BadPaddingException     |
                 IOException ex            ) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * 
     * Método que desencripta los datos del correo electrónico utilizado por la
     * aplicación para la mensajería instantanea. Se recoge el usuario en el primer
     * espacio del Array, y en el segundo la contraseá del mismo.
     * 
     * @return  ArrayList que contiene los datos del correo electrónico.
     */
    public ArrayList <String> decryptMailData () {
        
        ArrayList <String> data = new ArrayList <> ();
        
        String user;
        String password;
        
        String userKey = "Protoc017";
        
        byte [] salt = "1234567890123456".getBytes();
        
        KeySpec spec = new PBEKeySpec(userKey.toCharArray(), salt, 65536, 128);
        
        try {
           
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte [] key = factory.generateSecret(spec).getEncoded();
            SecretKey privateKey = new SecretKeySpec (key, 0, key.length, "AES");
        
            FileInputStream fis;
            fis = new FileInputStream ("mail.mail");
            ObjectInputStream ois;
            ois = new ObjectInputStream (fis);
                                
            byte[] iv = (byte[]) ois.readObject();
            byte[] encodedMessage = (byte[]) ois.readObject();
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte [] decodedMessage = cipher.doFinal(encodedMessage);
            
            user = new String(decodedMessage).substring(0, 20);
            password = new String(decodedMessage).substring(21,30);
            
            data.add(user);
            data.add(password);
            
            ois.close();
            
        } catch (NoSuchAlgorithmException  | InvalidKeySpecException            |
                 IOException               | NoSuchPaddingException             |
                 InvalidKeyException       | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException | BadPaddingException                |
                 ClassNotFoundException ex ) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return data;
        
    }
    
}
