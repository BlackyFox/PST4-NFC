package rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Antoine on 05/03/2015.
 */
public class RsaGen {

    private Key publicKey;
    private Key privateKey;
    private KeyPair kp;
    private KeyPairGenerator kpg;
    SecureRandom random;

    public RsaGen(){
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        random = new SecureRandom();
        kpg.initialize(2048, random);
        kp = kpg.generateKeyPair();
        this.setPublicKey(kp.getPublic());
        this.setPrivateKey(kp.getPrivate());
    }

    /**
     * @return the privateKey
     */
    public Key getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey the privateKey to set
     */
    private void setPrivateKey(Key privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @return the publicKey
     */
    public Key getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     */
    private void setPublicKey(Key publicKey) {
        this.publicKey = publicKey;
    }
}
