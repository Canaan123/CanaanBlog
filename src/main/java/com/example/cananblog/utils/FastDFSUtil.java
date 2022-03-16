package com.example.cananblog.utils;

import com.example.cananblog.bean.MyFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public class FastDFSUtil {

    static {
        //从classpath下获取文件对象获取路径
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 Storage 客户端
     * @return
     */
    public static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }
    /***
     * 获取TrackerServer
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public static String[] upload(MyFile file) {
        try {
            StorageClient storageClient = getStorageClient();
            NameValuePair[] meta_list = new NameValuePair[]{new NameValuePair(file.getAuthor()), new NameValuePair(file.getName())};
            String[] strings = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);

            return strings;
            // strings[0]==group1  strings[1]=M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 文件下载
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName,String remoteFileName){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient对象创建TrackerServer
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过TrackerServer创建StorageClienDt
            StorageClient storageClient = new StorageClient(trackerServer,null);
            //通过StorageClient下载文件
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            //将字节数组转换成字节输入流
            return new ByteArrayInputStream(fileByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static int downFile(String groupName, String remoteFileName,String localFile) {
//        ByteArrayInputStream byteArrayInputStream = null;
//        try {
//            StorageClient storageClient = getStorageClient();
//            return storageClient.download_file(groupName, remoteFileName, localFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        } finally {
//            try {
//                if (byteArrayInputStream != null) {
//                    byteArrayInputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


    /**
     * 文件删除
     * @param groupName
     * @param remoteFileName
     */
    public static String deleteFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            int i = storageClient.delete_file(groupName, remoteFileName);
            if (i == 0) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    /**
     * 根据组名获取组的信息
     * @param groupName
     * @return
     */
    public static StorageServer getStorages(String groupName) {
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = getTrackerServer();
            StorageServer group1 = trackerClient.getStoreStorage(trackerServer, groupName);
            return group1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件名和组名获取文件的信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            FileInfo fileInfo = storageClient.get_file_info(groupName, remoteFileName);
            return fileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据文件名和组名 获取组信息的数组信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName){
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
            return group1s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Track地址信息
     * @return
     */
    public static String getTrackerUrl(){
        try {
            TrackerServer trackerServer = getTrackerServer();
            String hostString = trackerServer.getInetSocketAddress().getHostString(); // 主机地址
            int TrackPort= ClientGlobal.getG_tracker_http_port();
            return "http://" + hostString + ":" + TrackPort;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
