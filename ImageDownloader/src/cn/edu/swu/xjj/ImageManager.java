package cn.edu.swu.xjj;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImageManager {
    public Map<String,String> map = new HashMap<>();
    public void downloadImage(String url,String savepath,String picname) throws IOException { //下载图片，并将信息加入集合。
        FileUtils fileUtils = new FileUtils();
        fileUtils.saveImage(url,savepath);
        map.put(picname,savepath);
        LocalDateTime time = LocalDateTime.now();
        String log = time + "Downloaded: " + url + " -> " + picname;
        fileUtils.appendLog(log);
    }

    public void listImage(){
        Set<String> keys = map.keySet();
        System.out.println("存放的图片列表为：");
        for(String key : keys){
            System.out.println(key);
        }
    }

    public void searchImage(String name){
        if(map.containsKey(name)) System.out.println("图片的路径为：" + map.get(name));
        else System.out.println("图片不存在！");
    }
}
