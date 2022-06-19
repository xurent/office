package com.xurent.office.utils;


import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO Auto-generated method stub

           String []arg=new String []{"python","D:\\IDEA\\IDPhoto\\1.py","C:\\Users\\hxuro\\Desktop\\1.jpg"
            ,"C:\\Users\\hxuro\\Desktop","1","255","255","0","hexurong"};
//
//        String pyPath = "D:\\IDEA\\IDPhoto\\1.py"; // python文件路径
//        String pyEnvironment = "C:\\Users\\hxuro\\AppData\\Local\\Programs\\Python\\Python36\\python.exe"; // 默认为python，如果使用了anaconda创建了环境，可以找到对应的路径并替换，类似于"E:\\Anaconda3\\envs\\xxx\\python.exe"。
//        PythonRun pythonRun = new PythonRun(); // 创建实例
//        pythonRun.setEnvironment(pyEnvironment); // 设置环境
//        pythonRun.setRoot("D:\\IDEA\\IDPhoto"); // 设置python项目的执行目录，若不设置，在调用了其它包时，可能会出现错误。如果没有import其它文件夹下的包或库，可以忽略。
//        System.out.println(pythonRun.run(pyPath,
//                "C:\\Users\\hxuro\\Desktop\\1.jpg", "C:\\Users\\hxuro\\Desktop",
//                "一寸","255","255","0","hexurong"));
//
//
//
       // PythonInterpreter interpreter = new PythonInterpreter();
        //interpreter.exec("cd D:\\IDEA\\IDPhoto");

//        interpreter.exec(
//                "import sys\n"
//                        +"sys.argv = ['C:\\Users\\hxuro\\Desktop\\1.jpg', 'C:\\Users\\hxuro\\Desktop'" +
//                        ",'一寸','255','255','0','hexurong']");
        //interpreter.execfile("D:\\IDEA\\IDPhoto\\1.py");
//        PythonInterpreter interpreter = new PythonInterpreter();
//        PySystemState sys = Py.getSystemState();
//        System.out.println(sys.path.toArray().toString());
//        sys.path.add("C:\\Users\\hxuro\\AppData\\Local\\Programs\\Python\\Python36\\Lib");
//        interpreter.exec("# coding= utf-8");
//        interpreter.exec("import sys");
//        interpreter.exec("print sys.path");
//        interpreter.exec("path = \"C:\\Users\\hxuro\\AppData\\Local\\Programs\\Python\\Python36\\Lib\"");
//        interpreter.exec("sys.path.append(path)");
//        //interpreter.exec("print sys.path");
//        //interpreter.exec("a=3; b=5;");
//        InputStream filepy = new FileInputStream("D:\\IDEA\\IDPhoto\\1.py");
//        interpreter.execfile(filepy);
//        filepy.close();


    String av="python  D:\\IDEA\\IDPhoto\\1.py  C:\\Users\\hxuro\\Desktop\\1.jpg C:\\Users\\hxuro\\Desktop " +
        "一寸 255 255 0  hexurong";
        Process pr = Runtime.getRuntime().exec(av,null,new File("D:\\IDEA\\IDPhoto"));
        BufferedReader in =new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        String result ="";
        while((line = in.readLine())!= null){
            result += line;

        }
        System.out.println(result);

        in.close();

        pr.waitFor();

    }


}
