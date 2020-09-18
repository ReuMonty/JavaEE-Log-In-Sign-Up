/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author PC
 */
public class Security {
    private static byte[] key = {
        0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 
        0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
    };
    
    public static String encrypt(String encryptThis){
        String encryptedMsg = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");                        
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedMsg = Base64.encodeBase64String(cipher.doFinal(encryptThis.getBytes()));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());        
        } 
        return encryptedMsg;
    }
    
    public static String decrypt(String decryptThis){
        String decryptedMsg = null;
        
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedMsg = new String(cipher.doFinal(Base64.decodeBase64(decryptThis)));
        } catch (Exception e) {
                System.err.println(e.getMessage());
        }
        return decryptedMsg;
    }
}
