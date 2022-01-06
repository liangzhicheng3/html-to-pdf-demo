package com.liangzhicheng.pdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

public class HtmlToPDF {

    public static void main(String[] args) {
        new HtmlToPDF().convert();
    }

    private void convert() {
        OutputStream os = null;
        try {
            File inputHtml = new File("D:\\WorkDirectory\\IdeaProjects\\html-to-pdf-demo\\src\\main\\resources\\test.html");
            //加载html文件
            Document document = Jsoup.parse(inputHtml, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.html);
            //引入资源目录下文件，css，图片等
            String filePath = FileSystems
                    .getDefault()
                    .getPath("D:\\WorkDirectory\\IdeaProjects\\html-to-pdf-demo\\src\\main\\resources")
                    .toUri()
                    .toString();
            //pdf结果输出
            os = new FileOutputStream("D:\\WorkDirectory\\test.pdf");
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri("D:\\WorkDirectory\\test.pdf");
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), filePath);
            //引入自定义字体，注意字体名需要和css样式中指定的字体名相同
            builder.useFont(
                    new File("D:\\WorkDirectory\\IdeaProjects\\html-to-pdf-demo\\src\\main\\resources\\font.ttf"),
                    "font",
                    1,
                    BaseRendererBuilder.FontStyle.NORMAL,
                    true);
            builder.run();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
