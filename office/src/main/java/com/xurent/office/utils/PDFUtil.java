package com.xurent.office.utils;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class PDFUtil {


    public static void pdfToWord(String pdfPath) {



    }


    /**
     * pdf转word 只支持21页
     * @param pdfPath  pdf上传临时路径
     * @param wordPath 转成word后存储路径
     */
    public static void trans(String pdfPath,String wordPath) {
        long old = System.currentTimeMillis();
        //System.out.println("pdfPath:"+pdfPath+"\nwordPath:"+wordPath);
        try {
            //新建一个pdf文档
            //String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
            File file = new File(wordPath);
            File pdf=new File(pdfPath);
            //File dir=new File()
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!pdf.getParentFile().exists()){
                pdf.getParentFile().mkdirs();
            }
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //去除水印
            removeWatermark(new File(wordPath));
            //转化用时
            long now = System.currentTimeMillis();
            log.warn("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            log.warn("Pdf 转 Word 失败...");
            throw new CommonException(ServerCodeEnum.PDF_TO_WORD_FAIL);
        }
    }


    //移除文字水印
    public static boolean removeWatermark(File file) {
        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
            // 段落
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                String text=paragraph.getText();
                if("Evaluation Only. Created with Aspose.PDF. Copyright 2002-2021 Aspose Pty Ltd.".equals(text)){
                    List<XWPFRun> runs = paragraph.getRuns();
                    runs.forEach(e-> e.setText("",0));
                }
            }
            FileOutputStream outStream = new FileOutputStream(file);
            doc.write(outStream);
            outStream.close();
        } catch (IOException e) {
            log.warn("Pdf 转 Word 去水印失败...");
            e.printStackTrace();
        }
        return true;
    }


}

