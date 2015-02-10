package fr.esiea.nfc.pst4.loyalties;

import android.util.Base64;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Antoine on 10/02/2015.
 */
public class AESencrp {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] {'h','f','W','p','3','o','U','i','e','d','Q','H','x','f','c','T'};

    private static Key generateKey() throws Exception{
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public static String encrypt(String data) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedVal = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedVal;
    }

    public static String decrypt(String data) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.decode(data, Base64.DEFAULT);
        byte[] decVal = c.doFinal(decordedValue);
        String decryptVal = new String(decVal);
        return decryptVal;
    }
}
