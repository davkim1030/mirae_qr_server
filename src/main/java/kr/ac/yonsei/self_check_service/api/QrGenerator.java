package kr.ac.yonsei.self_check_service.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author hyun-wookkim
 * QR 코드를 생성하는 클래스
 */
public class QrGenerator {
    private static final String pathPrefix = "./src/main/resources/qrcode/";    // 코드를 저장할 위치
    private static final String pathSuffix = ".png";                            // 기본 확장자
    private static final int size = 200;                                        // 기본 이미지 가로 세로 길이

    /**
     * 키값을 QR코드 파일명 그리고 개인 URL에 쓰일 String으로 만드는 메소드
     * @param msg : 해쉬할 키값
     * @return 해쉬되고 URL 인코딩 된 암호문
     * @throws NoSuchAlgorithmException : 암호화 알고리즘이 없는 경우 -> SHA-256이 없는 경우는 없으므로 예외 안 남
     */
    public static String nameHash(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        StringBuilder builder = new StringBuilder();
        for (byte b : md.digest())
            builder.append(String.format("%02x", b));
        return URLEncoder.encode(builder.toString(), StandardCharsets.UTF_8);
    }

    /**
     * QR코드 이미지를 생성해서 저장하는 메소드
     * @param text : 코드에 담을 메시지
     * @param fileName : 저장할 파일 이름
     * @throws WriterException : 코드를 만들 때 날 수도 있는 에러 -> 발생 시 에러 메시지 출력 후 이전 페이지로
     * @throws IOException : 이미지 저장시 날 수 있는 에러
     * @throws NoSuchAlgorithmException : nameHash 메소드 때문에 넣은 에러 -> 안 남
     */
    public void generateQRCodeImage (String text, String fileName)
            throws WriterException, IOException, NoSuchAlgorithmException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
        String pathName = pathPrefix + nameHash(fileName) + pathSuffix;

        Path path = FileSystems.getDefault().getPath(pathName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * QR코드 이미지를 생성해서 저장하는 메소드
     * @param text : 코드에 담을 메시지
     * @param width : 코드 이미지 너비
     * @param height : 코드 이미지 높이
     * @param fileName : 저장할 파일 이름
     * @param fileFormat : 저장할 파일 확장자
     * @throws WriterException : 코드를 만들 때 날 수도 있는 에러 -> 발생 시 에러 메시지 출력 후 이전 페이지로
     * @throws IOException : 이미지 저장시 날 수 있는 에러
     * @throws NoSuchAlgorithmException : nameHash 메소드 때문에 넣은 에러 -> 안 남
     */
    public void generateQRCodeImage (String text, int width, int height, String fileName, String fileFormat)
            throws WriterException, IOException, NoSuchAlgorithmException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        String pathName = pathPrefix + nameHash(fileName) + fileFormat;

        Path path = FileSystems.getDefault().getPath(pathName);
        MatrixToImageWriter.writeToPath(bitMatrix, fileFormat, path);
    }
}
