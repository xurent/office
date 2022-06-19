package com.xurent.office.utils;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PythonRun {
    private String environment = "python";
    private String root = null;
    private String cache = "D:\\IDEA\\IDPhoto\\";
    private boolean autoRemoveCache = true;

    public String run(String path, String ...args) throws IOException {
        System.out.println(path);
        path = createNewPy(path);
        List<String> inputArgs = new LinkedList<>(Arrays.asList(environment, path));  //设定命令行
        inputArgs.addAll(Arrays.asList(args));
        StringBuilder result = new StringBuilder();
        try {
            System.out.println(path);
            Process proc = Runtime.getRuntime().exec(inputArgs.toArray(new String[0]));  //执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
            in.close();
            //proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (autoRemoveCache && path != null)
            new File(path).delete();
        return result.toString();
    }

    private String createNewPy(String path) throws IOException {
        File file = new File(path);
        if (file.isFile()){
            String result = loadTxt(file);
            if (root != null){
                result = "# -*- coding: utf-8 -*-\n" +
                        "import sys\n" +
                        "sys.path.append(\"" + root + "\")\n" + result;
            }
            String save = cache + file.getName();
            saveTxt(save, result);
            System.out.println(result);
            return save;
        }
        return null;
    }

    private static File saveTxt(String filename, String string){

        File file = new File(filename);
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            out.write(string);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    private String loadTxt(File file){
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                result.append(str).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public boolean isAutoRemoveCache() {
        return autoRemoveCache;
    }

    public void setAutoRemoveCache(boolean autoRemoveCache) {
        this.autoRemoveCache = autoRemoveCache;
    }
}

