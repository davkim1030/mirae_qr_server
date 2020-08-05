package kr.ac.yonsei.self_check_service.api;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * @author hyun-wookkim
 * 암호화, 복호화 기능을 수행하는 클래스
 */
public class Crypto {
    private static final String cryptoKey = "KimChoShin3588274758"; // 비밀키
    private final String iv;                                        // 초기 벡터
    private final Key keySpec;                                      // 키 스펙

    // Constructor
    public Crypto() throws UnsupportedEncodingException {
        this.iv = cryptoKey.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = cryptoKey.getBytes(StandardCharsets.UTF_8);
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        this.keySpec = new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 암호화 메소드
     * AES256 알고리즘으로 평문을 암호화 한 후 Base64인코딩으로 변형하여 리턴
     * @param plainTxt : 암호화할 평문
     */
    public String encrypt(String plainTxt)
            throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

        byte[] encrypted = c.doFinal(plainTxt.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(encrypted));
    }

    /**
     * 복호화 메소드
     * AES256 알고리즘으로 암호화된 암호문을 복호화하여 리턴
     * @param cryptoTxt : 복호화할 암호문
     */
    public String decrypt(String cryptoTxt)
        throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

        byte[] byteStr = Base64.decodeBase64(cryptoTxt.getBytes());
        return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
    }
}
