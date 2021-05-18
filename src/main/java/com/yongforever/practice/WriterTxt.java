package com.yongforever.practice;

import java.io.*;

/**
 * 生成百万级txt数据
 */
public class WriterTxt {



    public static void main(String[] args) {

        File file = new File("D:\\develop\\yongforever\\writer.txt");
        BufferedWriter bw = null;
        String str = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 10000; j++) {
                    str = "张三,"+(i+j)%1000+",男";
                    bw.write(str);
                    bw.newLine();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
