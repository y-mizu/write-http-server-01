package jp.co.topgate.mizu.web;

import java.io.*;

public class Files {

    static byte[] readFile(File fileName) throws IOException {

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName))){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int tmp = 0;
            while ((tmp = bis.read()) !=  -1) {
                byteArrayOutputStream.write(tmp);
            }

            byte[] body = byteArrayOutputStream.toByteArray();
            return body;
        }
    }
}