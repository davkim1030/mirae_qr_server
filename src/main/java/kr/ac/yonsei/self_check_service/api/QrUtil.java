package kr.ac.yonsei.self_check_service.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author hyun-wookkim
 * QR 코드를 생성하는 클래스
 */
public class QrUtil {
    private static final String pathPrefix = "./src/main/resources/qrcode/";    // 코드를 저장할 위치
    private static final String pathSuffix = ".png";                            // 기본 확장자
    private static final int size = 200;                                        // 기본 이미지 가로 세로 길이
    public enum ResultType {
        FALSE,                  // 0 오늘 QR이 아님
        TRUE,                   // 1 오늘 QR이 맞음
        WRONG_DATE_FORMAT,      // 2 날짜의 형식이 틀린 경우
        UNKNOWN_ERROR           // 3 그 이외 케이스의 모르는 에러
    }                           // validateCode 결과 종류

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
     * 암호문에서 날짜 데이터를 추출해서 오늘 QR이 맞는지 확인하는 메소드
     * @param plainTxt : 검증할 암호문
     * @return
     */
    public static ResultType validateCode(String plainTxt) {
        LocalDate date;

        try {
            // 평문에서 날짜 정보를 뽑아내는 부분, TODO: 데이터 포맷에 따라 수정 필요
            date = LocalDate.parse(plainTxt.substring(0, 8), DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            return ResultType.WRONG_DATE_FORMAT;    // 날짜 정보를 잘 못 넣으면 생기는 오류 리턴
        }
        if (date.isEqual(LocalDate.now()))          // 추출한 날짜 정보가 오늘 날짜와 같으면 TRUE
            return ResultType.TRUE;
        return ResultType.FALSE;                    // 다르면 FALSE 리턴
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
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size); // 이미지 인스턴스 생성
        String pathName = pathPrefix + nameHash(fileName) + pathSuffix;                     // 경로 + 파일명 + 확장자 문자열

        Path path = FileSystems.getDefault().getPath(pathName);                             // 파일 위치
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);                    // 설정한 위치에 저장
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
