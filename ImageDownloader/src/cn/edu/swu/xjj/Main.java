package cn.edu.swu.xjj;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int choise;
        Scanner scanner = new Scanner(System.in);
        ImageManager imageManager = new ImageManager();
        int out=0;
        while (out!=1){
            System.out.print("===图片下载系统===\n1.下载图片\n2.查看所有图片\n3.搜索图片\n4.退出\n请输入相关需求：");
            choise = scanner.nextInt();
            scanner.nextLine();
            switch (choise){
                case 1:
                    System.out.print("请输入图片的url：");
                    String strurl = scanner.nextLine();
                    System.out.print("请输入文件存放的路径：");
                    String savepath = scanner.nextLine();
                    System.out.print("请输入文件的名称：");
                    String picname = scanner.nextLine();
                    imageManager.downloadImage(strurl,savepath,picname);
                    break;
                case 2:
                    imageManager.listImage();
                    break;
                case 3:
                    System.out.print("请输入需要查找照片的名称：");
                    String searchname = scanner.nextLine();
                    imageManager.searchImage(searchname);
                    break;
                case 4:
                    out = 1;
                    break;
            }
        }
        System.out.println("程序运行结束！");
        scanner.close();
    }
}
