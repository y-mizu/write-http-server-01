package jp.co.topgate.mizu.web;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class ServerThread implements Runnable {
    private static final String DOCUMET_ROOT = "/Users/mizu/~Sites/photo05/photo05.html";
    private Socket socket;

    //inputstreamからのバイト列を、行単位で読み込むユーティリティメソッド
    private static String readLine(InputStream input) throws Exception {
        int ch;
        String ret = "";
        while ((ch = input.read()) != -1) {
            if (ch == 'r') {
                //何もしない
            } else if (ch == 'n') {
                break;
            } else {
                ret += (char) ch;
            }
        }
        if(ch == -1){
            return null;
        } else{
            return ret;
        }
    }

    //1行の文字列を、バイト列としてOutputstreamに書き込む
    //ユーティリティメソッド
    private static void writeLine(OutputStream output, String str)throws Exception{
        for(char ch: str.toCharArray()){
            output.write((int)ch);
        }
        output.write((int)'r');
        output.write((int)'n');
    }

    //現在時刻から、HTTP標準に合わせてフォーマットされた日付文字列を返す
    private static String getDateStringUtc(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        df.setTimeZone(cal.getTimeZone());
        return df.format(cal.getTime()) + "GMT";
    }

    //拡張子とContent-Typeの対応表
    private static final HashMap<String, String> contentTypeMap = new HashMap<String, String>(){{
        put("html", "text/html");
        put("htm", "text/html");
        put("txt", "text/plain");
        put("css", "text/css");
        put("png", "image/png");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("gif", "immage/gif");
        }
    };
    //拡張子を受け取りContent-Typeに返す
    private static String getContentType(String ext){
        String ret = contentTypeMap.get(ext.toLowerCase());
        if(ret == null){
            return "application/octet-stream";
        }else{
            return ret;
        }
    }

    @Override
    public void run(){
        OutputStream output;

        try{
            InputStream input = socket.getInputStream();

            String line;
            String path = null;
            String ext = null;

            while((line = readLine(input)) != null){
                if(line == "")
                    break;
                if(line.startsWith("GET")){
                    path = line.split("")[1];
                    String[] tmp = path.split("¥¥.");
                    ext = tmp[tmp.length -1];
                }
            }
            output = socket.getOutputStream();

            //レスポンスヘッダを返す
            writeLine(output, "HTTP /1.1 200 OK");
            writeLine(output, "Date:" + getDateStringUtc());
            writeLine(output, "Server: Modoki/0.1");
            writeLine(output, "Connectin: close");
            writeLine(output, "Content-Type:" + getContentType(ext));
            writeLine(output, "");

            //レスポンスボディ
            try(FileInputStream fis = new FileInputStream(DOCUMET_ROOT);){
                int ch;
                while((ch = fis.read()) !=  -1){
                    output.write(ch);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    ServerThread(Socket socket){
        this.socket = socket;
    }
}
