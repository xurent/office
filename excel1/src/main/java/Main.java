

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Main {


    public static void main(String[] args) throws IOException, Exception {
        System.out.println("请输入文件目录或者文件路径回车:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件或路径不存在，请检查！");
        } else {
            if (file.isFile()) {
                Content content = PDFUtil.readPDF(path);
                if (content == null || content.end == null || content.authors == null || content.end.length() == 0) {
                    System.out.println(file.getName() + "文件解析失败!");
                    return;
                }

                writeTxt(content);
            } else if (file.isDirectory()) {
                String[] paths = file.list();

                for(int i = 0; i < paths.length; ++i) {
                    String dir = paths[i];
                    if (dir.endsWith(".pdf")) {
                        dir = file.getPath() + "/" + dir;
                        System.out.println("正在解析：" + dir + "...");
                        Content content = PDFUtil.readPDF(dir);
                        if (content != null && content.end != null && content.authors != null && content.end.length() != 0) {
                            writeTxt(content);
                        } else {
                            System.out.println(paths[i] + "文件解析失败!");
                        }
                    }
                }
            }

            System.out.println("处理完成！！！！");
            //Desktop.getDesktop().open(new File("/out/"));
        }
    }

    public static void writeTxt(Content content) {
        int start = 2147483647;

        for(int i = 0; i < content.authors.size(); ++i) {
            String author = ((String)content.authors.get(i)).trim();
            String conetx=content.end;
            System.out.println(conetx);
            if (findIndex(author,conetx)==-1) {
                author = PDFUtil.fen(author);
                content.authors.set(i, author);
            }

            start = start < findIndex(author,content.end) ? start : findIndex(author,content.end);
        }

        if (start <= content.end.length() && start >= 0) {
            String end = content.end.substring(start,content.end.length());

            try {
                File file = new File("/out/");
                if (!file.exists()) {
                    file.mkdir();
                }

                File files = new File("/out/res.xls");
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet();
                if (files.exists()) {
                    InputStream inputStream = new FileInputStream(files);
                    workbook = new XSSFWorkbook(inputStream);
                    sheet = workbook.getSheetAt(0);
                }

                sheet.setColumnWidth(0, 15360);
                sheet.setColumnWidth(1, 15360);
                sheet.setColumnWidth(2, 23040);
                sheet.setColumnWidth(3, 23040);
                sheet.setColumnWidth(4, 23040);

                Row row;
                Cell cell;
                Cell cell1;
                int i;
                for(i = 0; i <= sheet.getLastRowNum(); ++i) {
                    if (i == 0) {
                        row = sheet.createRow(i);
                        cell = row.createCell(0);
                        cell.setCellValue("标题");
                        cell1 = row.createCell(1);
                        cell1.setCellValue("邮箱");
                        Cell cell2 = row.createCell(2);
                        cell2.setCellValue("内容...");
                    }
                }

                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(content.title);
                cell1 = row.createCell(1);
                cell1.setCellValue("");
                if (content.head != null && content.head.trim() != "") {
                    List<String> list = PDFUtil.readEmail(content.head);
                    System.out.println("邮箱：" + list.toString());
                    cell1.setCellValue(list.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
                }

                boolean find_no = false;
                ArrayList<Integer> authors = new ArrayList();

                int p;
                int j;
                for(p = 0; p < content.authors.size(); ++p) {
                    j = end.indexOf(((String)content.authors.get(p)).trim());
                    if (j >= 0) {
                        authors.add(j);
                    } else {
                        find_no = true;
                    }
                }

                if (find_no) {
                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(end);
                } else {
                    Arrays.sort(authors.toArray());
                    p = 2;

                    for(j = 0; j < authors.size(); ++j) {
                        String temp;
                        Cell cellx;
                        if (j == authors.size() - 1) {
                            temp = end.substring((Integer)authors.get(j), end.length());
                            cellx = row.createCell(p++);
                            cellx.setCellValue(temp);
                        } else {
                            temp = end.substring((Integer)authors.get(j), (Integer)authors.get(j + 1));
                            cellx = row.createCell(p++);
                            cellx.setCellValue(temp);
                        }
                    }
                }

                FileOutputStream outputStream = new FileOutputStream("/out/res.xls");
                workbook.write(outputStream);
                outputStream.close();
                System.out.println("【" + content.title + "】处理完成，输出目录:" + file.getAbsolutePath());
            } catch (Exception var17) {
                var17.printStackTrace();
            }

        } else {
            System.out.println(content.title + "匹配不到作者内容");
        }
    }

    public static boolean pipe(String res){
        boolean zhong=false;
        for(int i=0;i<res.length();i++){
            Character c=res.charAt(i);
            if(c=='['){
                zhong=true;
                continue;
            }
            if(zhong){
                Pattern p = Pattern.compile("^[0-9]*$");
                Matcher m = p.matcher(String.valueOf(c));
                if(m.find()){
                    int d=m.start();
                    while (d<res.length()){
                        d++;
                        if(res.charAt(d)==']'){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    public static int findIndex(String src,String res){

        boolean find=false;
        int pos=-1;
        src=src.replaceAll(" ","");
        int index=0,len=src.length();
        Character cur=src.charAt(0);
        for(int i=0;i<res.length();i++){
            Character c=res.charAt(i);
            if(c==' ') continue;
            if(cur==c){
                index++;

                if(index>=len){
                    find=true;
                     pos=i-len-1;
                     //判断是否引用
                     if(pos>0){
                         int start=0;
                         int end=res.length()-1;
                         if(pos-25>0){
                             start=pos-25;
                         }
                         if(pos+25<res.length()-1){
                             end=pos+25;
                         }
                         String patter=res.substring(start,end);
                         if(pipe(patter)){
                             index=0;

                         }else{
                             return pos;
                         }
                     }

                }
                cur=src.charAt(index);
            }else{
                index=0;
                cur=src.charAt(index);
            }
        }


        return -1;
    }

}
