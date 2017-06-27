package jp.co.topgate.mizu.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

class Util {
    //inputstreamからのバイト列を、行単位で読み込むユーティリティメソッド
    static String readLine(InputStream input) throws Exception {
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
    static void writeLine(OutputStream output, String str)throws Exception{
        for(char ch: str.toCharArray()){
            output.write((int)ch);
        }
        output.write((int)'r');
        output.write((int)'n');
    }

    //現在時刻から、HTTP標準に合わせてフォーマットされた日付文字列を返す
    static String getDateStringUtc(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        df.setTimeZone(cal.getTimeZone());
        return df.format(cal.getTime()) + "GMT";
    }

    //拡張子とContent-Typeの対応表
    static final HashMap<String, String> contentTypeMap = new HashMap<String, String>(){
        static final long serialVersionUID = 1L;
        {
        put("html", "text/html; charset=UTF-8");
        put("htm", "text/html; charset=UTF-8");
        put("txt", "text/plain; charset=UTF-8");
        put("css", "text/css; charset=UTF-8");
        put("png", "image/png; charset=UTF-8");
        put("jpg", "image/jpeg; charset=UTF-8");
        put("jpeg", "image/jpeg; charset=UTF-8");
        put("gif", "immage/gif; charset=UTF-8");
        }
    };

    //拡張子を受け取りContent-Typeに返す
    static String getContentType(String ext){
        String ret = contentTypeMap.get(ext.toLowerCase());
        if(ret == null){
            return "application/octet-stream";
        }else{
            return ret;
        }
    }
}
