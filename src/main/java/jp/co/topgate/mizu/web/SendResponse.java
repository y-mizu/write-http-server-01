package jp.co.topgate.mizu.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

class SendResponse {
    static void sendOkResponse(OutputStream output, InputStream fis, String ext) throws Exception{
        //レスポンスヘッダを返す
        Util.writeLine(output, "HTTP /1.1 200 OK");
        Util.writeLine(output, "Date:" + Util.getDateStringUtc());
        Util.writeLine(output, "Server: write-http-server-01");
        Util.writeLine(output, "Connectin: close");
        Util.writeLine(output, "Content-Type:" + Util.getContentType(ext));
        Util.writeLine(output, "");
        int ch;
        while((ch = fis.read()) !=  -1){
            output.write(ch);
        }
    }

    static void sendMovePermanentlyResponse(OutputStream output, String location) throws Exception{
        //レスポンスヘッダを返す
        Util.writeLine(output, "HTTP /1.1 301 Moved Permanently");
        Util.writeLine(output, "Date:" + Util.getDateStringUtc());
        Util.writeLine(output, "Server: write-http-server-01");
        Util.writeLine(output, "Location: " + location);
        Util.writeLine(output, "Connectin: close");
        Util.writeLine(output, "");
    }

    static void sendNotFoundResponse(OutputStream output, String errorDocumentRoot) throws Exception{
        //レスポンスヘッダを返す
        Util.writeLine(output, "HTTP /1.1 404 Not Found");
        Util.writeLine(output, "Date:" + Util.getDateStringUtc());
        Util.writeLine(output, "Server: write-http-server-01");
        Util.writeLine(output, "Connectin: close");
        Util.writeLine(output, "Content-Type: text/html");
        Util.writeLine(output, "");

        try(InputStream fis = new BufferedInputStream(new FileInputStream(errorDocumentRoot))){
            int ch;
            while((ch = fis.read()) !=  -1){
                output.write(ch);
            }
        }
    }
}
