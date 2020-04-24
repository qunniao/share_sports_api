package com.gymnasium.Util.oss;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.gymnasium.Util.SCException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author x
 */
public class FileUtils {
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String endpoint1 = "http://gymfiles.oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAI8VoT591CDwLd";
    private static String accessKeySecret = "fK8hu4R3k1y24EFsrlLDpuRsIM8O6U";
    private static String bucketName = "gymfiles";
    private static String JPG = "jpg";
    private static String PNG = "png";
    private static String GIF = "gif";

    /**
     * 上传文件
     *
     * @param request
     * @return map
     */
    public static Map<String, Object> upload(HttpServletRequest request) {
        Map<String, Object> mapReturn = new HashMap<String, Object>();
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            try {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 接收的图片
                List<MultipartFile> images = multiRequest.getFiles("image");
                System.out.println("images1" + images.size());
                if (null != images && images.size() > 0) {
                    ArrayList<String> strings = addFile(images);
                    System.out.println("strings2" + strings.size());
                    if (strings.size() > 0) {
                        System.out.println("images3" + strings);
                        mapReturn.put("images", strings);
                    }
                }
            } catch (OSSException oe) {
                oe.printStackTrace();
            } catch (ClientException ce) {
                ce.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ossClient.shutdown();
            }
        }
        return mapReturn;
    }

    /**
     * 上传图片
     *
     * @param images
     * @return
     */
    public static String uploadImage(List<MultipartFile> images) {

        if (ObjectUtil.isEmpty(images) || images.size() == 0) {
            return null;
        }

        Map<String, Object> mapReturn = new HashMap<String, Object>();
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        if (null != images && images.size() > 0) {
            ArrayList<String> strings = addFile(images);
            if (strings.size() > 0) {
                mapReturn.put("images", strings);
            }
        }

        ossClient.shutdown();

        List list = (List) mapReturn.get("images");

        String strip = StringUtils.strip(list.toString(), "[]");

        return strip.replaceAll(" ", "");
    }

    /**
     * 上传图片
     *
     * @return
     */
    public static String uploadImage(MultipartFile files) {

        if (ObjectUtil.isEmpty(files) || files.getSize() == 0) {
            return null;
        }

        // 文件类型
        String type = verifyImage(files);

        Map<String, Object> mapReturn = new HashMap<>(16);

        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            ArrayList<String> strings = addFile(files, type);

            mapReturn.put("image", strings);

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        List list = (List) mapReturn.get("image");

        System.err.println("images\t" + StringUtils.strip(list.toString(), "[]"));

        return StringUtils.strip(list.toString(), "[]");
    }

    /**
     * 富文本上传文件方法
     *
     * @param request
     * @return map
     */
    public static Map<String, Object> uploadfile(MultipartFile request) {
        Map<String, Object> mapReturn = new HashMap<String, Object>();
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        if (true) {
            try {
                // 接收的图片
                List<MultipartFile> images = new ArrayList<>();
                images.add(request);
                if (null != images && images.size() > 0) {
                    ArrayList<String> strings = addFile(images);
                    if (strings.size() > 0) {
                        mapReturn.put("images", strings);
                    }
                }
            } catch (OSSException oe) {
                oe.printStackTrace();
            } catch (ClientException ce) {
                ce.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ossClient.shutdown();
            }
        }
        return mapReturn;
    }


    public static ArrayList<String> addFile(List<MultipartFile> parameter) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ArrayList<String> imagesinfos = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(parameter)) {
            ArrayList<String> imagesInfo = new ArrayList<>();
            for (MultipartFile multipartFile : parameter) {

                String type = verifyImage(multipartFile);
                // 文件存储入OSS，Object的名称为uuid
                String uuid = FileUtils.uuid();

                System.out.println("uuid" + uuid);

                File f = null;
                try {
                    f = File.createTempFile("tmp", null);
                    multipartFile.transferTo(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imagesInfo.add(endpoint1 + "/" + uuid + "." + type);

                ossClient.putObject(bucketName, uuid + "." + type, f);
            }
            return imagesInfo;
        }
        return imagesinfos;
    }

    public static ArrayList<String> addFile(MultipartFile files, String extendFileName) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ArrayList<String> imagesInfo = new ArrayList<>();
        try {
            if (files != null) {

                String originalFilename = files.getOriginalFilename();
                if (IsObjectNullUtils.is(originalFilename)) {
                    return null;
                }

                System.err.println(extendFileName);
                String uuid = FileUtils.uuid();
                File f = File.createTempFile("tmp", null);
                files.transferTo(f);
                imagesInfo.add(endpoint1 + "/" + uuid + "." + extendFileName);
                ossClient.putObject(bucketName, uuid + "." + extendFileName, f);

                return imagesInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagesInfo;
    }


    private static File multipartToFile(MultipartFile multfile) {
        CommonsMultipartFile cf = (CommonsMultipartFile) multfile;
        // 这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        // 手动创建临时文件
        if (file.length() < 20971520) {
            File tmpFile = new File(
                    System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file.getName());
            try {
                multfile.transferTo(tmpFile);
            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new File(tmpFile.getAbsolutePath());
        }
        return file;
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean deleteFile(String filePath) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        boolean exist = ossClient.doesObjectExist(bucketName, filePath);
        if (!exist) {
            System.out.println("文件不存在,filePath=" + filePath);
            return false;
        }
        System.out.println("删除文件,filePath=" + filePath);
        ossClient.deleteObject(bucketName, filePath);
        ossClient.shutdown();
        return true;
    }


    public static String verifyImage(MultipartFile files) {

        String type = "";
        try {
            type = FileTypeUtil.getType(files.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (JPG.equals(type) || PNG.equals(type) || GIF.equals(type)) {
            return type;
        } else {
            throw new SCException(400753, "图片格式不匹配");
        }
    }
}
