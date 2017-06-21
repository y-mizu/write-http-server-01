package jp.co.topgate.mizu.web;


public class ErrorPage {
    //エラーメッセージを受け取るフィールド
    private static String errorMessage;

    //エラーメッセージをセットするメソッド
    void setErrorMessage(String errorMessage){
        ErrorPage.errorMessage = errorMessage;
    }

    static byte[] makeErrorMessage(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!DOCTYPE html>")
                .append("<html lang=\"ja\">")
                .append("<head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<title>error</title>")
                .append("</head>")
                .append("<body>")
                .append("<p>" + errorMessage + "</p>")
                .append("</body>");

        return (new String(stringBuilder).getBytes());
    }
}