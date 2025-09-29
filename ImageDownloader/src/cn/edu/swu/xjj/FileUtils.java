package cn.edu.swu.xjj;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;

public class FileUtils {
    //从url下载图片且保存到指定位置
    public static void saveImage(String strurl,String savePath) throws IOException {
        URL url = new URL (strurl);
        URLConnection connection = url.openConnection();

        try (InputStream inputStream = connection.getInputStream()){
            ensureDirectoryExists(savePath);

            try(FileOutputStream outputStream = new FileOutputStream(savePath)){
                ensureDirectoryExists(savePath);
                int count;
                byte[] buffer = new byte[1024 * 4]; //创建一个4K的缓存区
                while ((count = inputStream.read(buffer))!=-1){  //读取文件，当读到文件末尾时会返回-1，否则返回读取的文件大小
                    outputStream.write(buffer,0, count);  //设置缓存区buffer，每次最多读取1024*4(4KB)大小的文件再写入；从0位置开始读取；读取count个大小的文件
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //查看目录是否存在，不存在则创建
    public static void ensureDirectoryExists(String dirPath){
        File outfile = new File(dirPath);
        File dirs = outfile.getParentFile();
        dirs.mkdirs();
    }

    //将下载记录追加到download_log.txt文件
    public static void appendLog(String log) throws IOException {
        String logpath = "D:\\University\\sophomoric_first_year\\java_learning\\ImageDownloader\\log.txt";
        try(FileWriter fileWriter = new FileWriter(logpath,true)){
            fileWriter.write(log);
            fileWriter.write(System.lineSeparator());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
