package rsa;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Antoine on 05/03/2015.
 */
public class RsaChif {
    RsaGen couple;
    Key pub;
    Key priv;

    public void RsaChif(){
        this.couple = new RsaGen();
        this.pub = this.couple.getPublicKey();
        this.priv = this.couple.getPrivateKey();
    }

    /**
     *
     * Le message que tu dois chiffrer : @param clair
     * La clé publique du destinataire : @param key
     * @return le message chiffré
     */
    String Cipherisation(String clair, Key key){
        Cipher c;
        try {
            c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] cyp = c.doFinal(clair.getBytes());
            return new String(cyp);
        } catch (NoSuchAlgorithmException e) {
            Log.e("ALGO", "The algorithm is wrong");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.e("KEY", "Invalid Key for encryption");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            Log.e("PADDING", "Bad padding cipher");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Log.e("BLOCK_SIZE", "Bloc size to decrypt is wrong");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * Le message chiffré que l'on doit déchiffrer : @param m
     * @return le message en clair
     */
    String Decipher(String m){
        Cipher c;
        try {
            c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, this.priv);
            byte[] cyp = c.doFinal(m.getBytes());
            return new String(cyp);
        } catch (NoSuchAlgorithmException e) {
            Log.e("ALGO", "The algorithm is wrong");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.e("KEY", "Invalid Key for encryption");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            Log.e("PADDING", "Bad padding cipher");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Log.e("BLOCK_SIZE", "Bloc size to decrypt is wrong");
            e.printStackTrace();
        }
        return null;
    }
}
