package kr.ac.yonsei.self_check_service.api;

import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class QrGeneratorTest {

    @Test
    public void cryptoTest()
    {
        try {
            String key = "2013253074";
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String plainTxt = key + date;

            Crypto crypto = new Crypto();
            String encryptTxt = crypto.encrypt(plainTxt);
//
            QrGenerator qrGenerator = new QrGenerator();
            qrGenerator.generateQRCodeImage(encryptTxt, key);

//            String decryptTxt = crypto.decrypt("LPn5FUFY0WeTjAz0OWR72vV+znvyNn2+ReSEnGXC7cA=");
//            System.out.println(decryptTxt);
        } catch (GeneralSecurityException | IOException | WriterException e) {
            e.printStackTrace();
        }

    }

}
