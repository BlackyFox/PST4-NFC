package com.example.pierre.applicompanies;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Classe d'encryptage.                                                                           */
/**************************************************************************************************/

import android.util.Base64;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESencrp {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] {'h','f','W','p','3','o','U','i','e','d','Q','H','x','f','c','T'};
    private static final String keyVal = "hfWp3oUiedQHxfcT";

    private static Key generateKey() throws Exception{
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = df.format(c.getTime());
        String s = keyVal + formattedDate;
        Key key = new SecretKeySpec(s.getBytes(), ALGO);
        return key;
    }

    public String encrypt(String data) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedVal = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedVal;
    }

    public String decrypt(String data) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.decode(data, Base64.DEFAULT);
        byte[] decVal = c.doFinal(decordedValue);
        String decryptVal = new String(decVal);
        return decryptVal;
    }
}

// EXAMPLE OF USE : WAS IN HOMEFRAGMENT.JAVA
/*
        String password = "mypassword";
        String passwordEnc = null;
        try {
            passwordEnc = AESencrp.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String passwordDec = null;
        try {
            passwordDec = AESencrp.decrypt(passwordEnc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("CRYPT","Plain Text : " + password);
        Log.d("CRYPT", "Encrypted Text : " + passwordEnc);
        Log.d("CRYPT","Decrypted Text : " + passwordDec);
*/
