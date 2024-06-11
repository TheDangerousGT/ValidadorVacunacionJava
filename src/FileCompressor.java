import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileCompressor {
    public static void compressFile(String inputFile, String outputFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            File fileToZip = new File(inputFile);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            zipOut.closeEntry();
        }
    }

    public static void decompressFile(String inputFile, String outputPath) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(inputFile))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = outputPath + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[1024];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
