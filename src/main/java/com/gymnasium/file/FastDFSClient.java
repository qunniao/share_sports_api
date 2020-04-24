package com.gymnasium.file;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;

    public FastDFSClient() throws Exception {
        String confPath = FastDFSClient.class.getClass().getResource("/").getPath() + "config/fastdfs-client.properties";
        ClientGlobal.init(confPath);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    private FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    public static String uploadUtil(@RequestParam("files") MultipartFile file){

        if (file == null || file.isEmpty()) {

            return "文件为空";
        }

        String fileName = file.getOriginalFilename();// 获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));// 获取文件的后缀名
        suffixName = suffixName.substring(1, suffixName.length());


        InputStream is = null;

        String url = "";
        try {
            is = file.getInputStream();

            byte[] fileBuff = new byte[is.available()];
            is.read(fileBuff, 0, fileBuff.length);

            System.out.println(suffixName);

            // 上传
            FastDFSClient fastDFSClient = new FastDFSClient();
            url = fastDFSClient.uploadFile(fileBuff, suffixName);

            System.out.println(url);

            is.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static List<String> uploadUtil(List<MultipartFile> files) throws Exception {
        List<String> urls = new ArrayList<String>();
        InputStream is = null;
        FastDFSClient fastDFSClient = new FastDFSClient();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();// 获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));// 获取文件的后缀名
            suffixName = suffixName.substring(1, suffixName.length());
            is = file.getInputStream();
            byte[] fileBuff = new byte[is.available()];
            is.read(fileBuff, 0, fileBuff.length);
            System.out.println(fileName);
            System.out.println(suffixName);

            String url = fastDFSClient.uploadFile(fileBuff, suffixName);
            urls.add(url);
            System.out.println(url);
        }
        if (is != null) {
            is.close();
        }
        return urls;
    }


    public static void main(String args[]) throws Exception {
        //src/main/resources/client.conf 内容仅一行 tracker_server=192.168.123.85:22122
        FastDFSClient fastDFSClient = new FastDFSClient();
        String string = fastDFSClient.uploadFile("D:\\test.png", "png");
        System.out.println(string);
        //http://192.168.248.128/group1/M00/00/00/wKj4gFnZ8iiAEOiYAAAvWJjxjPk890.jpg
    }

}
