package jp.co.topgate.mizu.web;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileTest {

    @Test
    public void readFileでファイル内のデータを取得する() throws IOException{
        String expectedfile = "<!DOCTYP  html>\n" +
                " <html lang=\"en\">\n" +
                " <head>\n" +
                "   <meta charset=\"UTF-8\">\n" +
                "   <title>Title</title>\n" +
                "   <script type=\"text/javascript\" src=\"index.js\"></script>\n" +
                "   <link rel=\"stylesheet\" type=\"text/css\" href=\"index.css\">\n" +
                " </head>\n" +
                " <body>\n" +
                " テスト\n" +
                " <a href=\"./next.html\" targert=\"_blank\">次のページに移行します</a>\n" +
                " <a href=\"javascript:void(0)\" onClick=\"color1()\"><span id=\"mouse\">色を変えます</span></a>\n" +
                " </body>\n" +
                " </html>" ;

    byte[] expected = expectedfile.getBytes();

    File file = new File("src/main/resource/index.html");

    assertThat(expected, is(Files.readFile(file)));

    }
}
