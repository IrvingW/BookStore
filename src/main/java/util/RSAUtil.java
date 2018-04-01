package util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class RSAUtil {
	private static final String ALGORITHOM = "RSA";
	private static final int KEY_SIZE = 1024;
	private static String hexModulus;
	private static String hexPublicExponent;
	private static RSAPrivateKey privateKey;
	
	static {
		try {
			generateKey();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getHexModulus() {
		return hexModulus;
	}

	public static String getHexPublicExponent() {
		return hexPublicExponent;
	}

	// generate key pair
	private static void generateKey() throws Exception{
		SecureRandom sRandom = new SecureRandom(new SimpleDateFormat("yyyyMMdd").format(new Date()).getBytes());
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHOM, new BouncyCastleProvider());
		kpg.initialize(KEY_SIZE, sRandom);
		KeyPair kPair = kpg.generateKeyPair();
		RSAPublicKey publicKey =  (RSAPublicKey) kPair.getPublic();
		privateKey = (RSAPrivateKey) kPair.getPrivate();
		
		BigInteger prime = publicKey.getModulus();
		BigInteger exponent = publicKey.getPublicExponent();
		
		hexModulus = HexUtil.bytes2Hex(prime.toByteArray());
		hexPublicExponent = HexUtil.bytes2Hex(exponent.toByteArray());
	}

    
    public static String decryptString(String encrypttext) {
    	if(encrypttext == null || encrypttext.equals(""))
    		return null;
        try {
            byte[] en_data = HexUtil.hex2Bytes(encrypttext);
            byte[] data = decrypt(en_data);
            return new String(data);
        } catch (Exception ex) {
            System.out.println(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));

        }
        return null;
    }
    
	public static byte[] decrypt(byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, new BouncyCastleProvider());
		ci.init(Cipher.DECRYPT_MODE, privateKey);
		return ci.doFinal(data);
	}
	
}
