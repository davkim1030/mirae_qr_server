package kr.ac.yonsei.self_check_service.api;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class QrGeneratorTest {

//    @Test
//    public void cryptoTest()
//    {
//        try {
//            String key = "2013253074";
//            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//            String plainTxt = key + date;
//
//            Crypto crypto = new Crypto();
//            String encryptTxt = crypto.encrypt(plainTxt);
////
//            QrCode qrGenerator = new QrCode();
//            qrGenerator.generateQRCodeImage(encryptTxt, key);
//
////            String decryptTxt = crypto.decrypt("LPn5FUFY0WeTjAz0OWR72vV+znvyNn2+ReSEnGXC7cA=");
////            System.out.println(decryptTxt);
//        } catch (GeneralSecurityException | IOException | WriterException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Test
    public void validateQrTest()
    {
        try {
            Crypto crypto = new Crypto();
            String cTxt = crypto.encrypt("202022332013253048");
            QrUtil.validateCode(cTxt);
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


}
