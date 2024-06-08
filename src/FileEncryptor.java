import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor {
    private static final String ALGORITHM = "AES";

    public static void encryptFile(String inputFile, String outputFile, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = createSecretKey(secretKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        encryptOrDecryptFile(cipher, inputFile, outputFile);
    }

    public static void decryptFile(String inputFile, String outputFile, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = createSecretKey(secretKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        encryptOrDecryptFile(cipher, inputFile, outputFile);
    }

    private static SecretKeySpec createSecretKey(String key) throws Exception {
        byte[] keyBytes = key.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16); // use only first 128 bits
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    private static void encryptOrDecryptFile(Cipher cipher, String inputFile, String outputFile) throws Exception {
        File input = new File(inputFile);
        FileInputStream inputStream = new FileInputStream(input);
        byte[] inputBytes = new byte[(int) input.length()];
        inputStream.read(inputBytes);
        inputStream.close();

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);
        outputStream.close();
    }

    static String decrypt(String encryptedText, int shift) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char currentChar = encryptedText.charAt(i);
            if (Character.isLetter(currentChar)) {
                char decryptedChar = (char) (currentChar - shift);
                if (decryptedChar < 'A') {
                    decryptedChar += 26;
                }
                decryptedText.append(decryptedChar);
            } else {
                decryptedText.append(currentChar);
            }
        }
        return decryptedText.toString();
    }

    static String encrypt(String text, int shift) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                char encryptedChar = (char) (currentChar + shift);
                if (encryptedChar > 'Z') {
                    encryptedChar -= 26;
                }
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(currentChar);
            }
        }
        return encryptedText.toString();
    }
}
