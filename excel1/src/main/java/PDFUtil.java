import org.apache.commons.collections4.CollectionUtils;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFUtil {

    Set<String> set = new HashSet();

    public PDFUtil() {
    }

    public static Content readPDF(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println(fileName + "不存在!");
            return null;
        } else {
            FileInputStream in = null;

            try {
                in = new FileInputStream(fileName);

                PDFParser parser = new PDFParser(new RandomAccessBuffer(in));
                parser.parse();
                PDDocument pdfdocument = parser.getPDDocument();
                PDDocumentInformation info = pdfdocument.getDocumentInformation();
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(false);
                int page = pdfdocument.getNumberOfPages();
                Content content;
                if (page < 1) {
                    System.out.println("PDF页码不存在!");
                    content = null;
                    return content;
                } else {
                    stripper.setStartPage(0);
                    stripper.setEndPage(1);
                    content = new Content();
                    String result = stripper.getText(pdfdocument);
                    content.head = result;
                    List authors;
                    if (info.getAuthor() != null && info.getAuthor().trim().length() != 0) {
                        authors = Arrays.asList(info.getAuthor().split(";"));
                        Content var11;
                        if (!CollectionUtils.isEmpty(authors)) {
                            content.title = info.getTitle();
                            content.authors = authors;
                            stripper.setEndPage(page);
                            if (page == 2) {
                                stripper.setStartPage(1);
                            } else if (page == 3) {
                                stripper.setStartPage(page - 1);
                            } else if (page > 3) {
                                stripper.setStartPage(page - 2);
                            }

                            result = stripper.getText(pdfdocument);
                            content.end = result;
                            var11 = content;
                            return var11;
                        } else {
                            System.out.println(fileName + "文件作者解析失败~");
                            var11 = null;
                            return var11;
                        }
                    } else {
                        System.out.println(fileName + "文件作者解析失败~");
                        authors = null;
                        return content;
                    }
                }
            } catch (Exception var24) {
                System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + var24);
                var24.printStackTrace();
                return null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException var23) {
                    }
                }

            }
        }
    }

    public static String fen(String str) {
        String s = "";
        boolean toup = false;

        for(int i = 0; i < str.length(); ++i) {
            Character c = str.charAt(i);
            if (toup) {
                c = c.toString().toLowerCase().charAt(0);
            }

            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                toup = true;
            }

            if (str.charAt(i) == '-' || str.charAt(i) == ' ') {
                toup = false;
            }

            s = s + c.toString();
        }

        return s;
    }

    public static List<String> readEmail(String content) {
        List es = new ArrayList();
        Pattern p = Pattern.compile("[\\w\\.-]*\\w+@[\\w\\.-]*\\w+\\.\\w{2,5}");
        Matcher m = p.matcher(content);

        while(m.find()) {
            es.add(m.group());
        }

        return es;
    }
}
