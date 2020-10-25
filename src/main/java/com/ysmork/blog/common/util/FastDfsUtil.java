package com.ysmork.blog.common.util;

import com.ysmork.blog.framework.web.entity.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 文件上传
 * @date 2020/10/5 19:05
 */
public class FastDfsUtil {
    /**
     *加载 tracker 链接信息
     */
    static {
        try {
            ClientGlobal.init(new ClassPathResource ("fdfs_client.conf").getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TrackerServer getTrackerServer() throws IOException {
        return new TrackerClient ().getConnection();
    }

    private static StorageClient getStorageClient() throws IOException {
        return new StorageClient(getTrackerServer(), null);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    public static Map<String,String> upload(FastDFSFile file) {

        try {
            TrackerServer trackerServer = getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer, null);

            //参数1 字节数组
            //参数2 扩展名(不带点)
            //参数3 元数据( 文件的大小,文件的作者,文件的创建时间戳)
            //byte cmd, String group_name, String local_filename, String file_ext_name, NameValuePair[] meta_list
            NameValuePair[] meta_list = new NameValuePair[]{
                    new NameValuePair("Author", file.getAuthor()),
                    new NameValuePair("Name", file.getName())
            };

            String[] strings = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
            /**
             *  strings[0]==group1  strings[1]=M00/00/00/1.jpg
             * <ul><li>results[0]: the group name to store the file</li></ul>
             * <ul><li>results[1]: the new created filename</li></ul>
             */
            String hostString = trackerServer.getInetSocketAddress().getHostString();
            int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
            //http://192.168.71.128:8080/group1/M00/00/00/wKhHgF87dTqAZk6kAAFJbAv-xZI615.jpg
            Map<String,String> map = new HashMap<> (2);
            //用于删除的路径
            map.put ("path",strings[1]);
            //访问路径
            map.put ("relPath","http://"+hostString+":"+g_tracker_http_port+"/"+strings[0]+"/"+strings[1]);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream download(String groupName, String remoteFileName) {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            StorageClient storageClient =getStorageClient();
            //参数1:指定组名
            //参数2 :指定远程的文件名
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            return byteArrayInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除
     * @param groupName
     * @param remoteFileName
     */
    public static void delete(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            int i = storageClient.delete_file(groupName, remoteFileName);
            if (i == 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件信息
     * @param groupName  group1
     * @param remoteFileName  M00/00/00/wKhHgF87dTqAZk6kAAFJbAv-xZI615.jpg
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            //参数1 指定组名
            //参数2 指定文件的路径
            FileInfo fileInfo = storageClient.get_file_info(groupName, remoteFileName);
            return fileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {

        //test getFile
//        ObjectMapper objectMapper = new ObjectMapper();
//        FileInfo fileInfo = getFile("group1", "M00/00/00/wKhHgF87dTqAZk6kAAFJbAv-xZI615.jpg");
//        System.out.println(objectMapper.writeValueAsString(fileInfo));

        //test download
//        InputStream inputStream = download("group1", "M00/00/00/wKhHgF87jEKAd9qfAAFJbAv-xZI043.jpg");
//        FileOutputStream outputStream = new FileOutputStream("F:/00.jpg");
//
//        byte[] bytes = new byte[1024];
//        while (inputStream.read(bytes) != -1){
//            outputStream.write(bytes);
//        }
//        outputStream.flush();
//        outputStream.close();
//        inputStream.close();

        //test delete
        delete("group1", "M00/00/02/rB4eal-K8hWAF7QHAAAIgCDGpFQ649.jpg");

    }
}
