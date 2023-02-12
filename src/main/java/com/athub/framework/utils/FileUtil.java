package com.athub.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wang wenjun
 */
@Slf4j
public class FileUtil {

    private static String TEMPPATH = "MailAttachmentTmp";

    public static List<File> convertByMultipartFiles(MultipartFile[] multipartFiles) {
        List<File> fileList = new ArrayList<>();
        if (multipartFiles != null && multipartFiles.length > 0) {
            for (int i = 0; i < multipartFiles.length; i++) {
                try {
                    if (!"".equals(multipartFiles[i].getOriginalFilename().trim())) {
                        File file = new File("./" + TEMPPATH + "/" + multipartFiles[i].getOriginalFilename());
                        File pFfile = file.getParentFile();
                        if (!pFfile.exists()) {
                            pFfile.mkdirs();
                        }
                        FileUtils.copyInputStreamToFile((multipartFiles[i]).getInputStream(), file);
                        fileList.add(file);
                    }
                } catch (Exception e) {
                    log.debug("获取附件失败：" + e.getMessage());
                }
            }
        }
        return fileList;
    }
}
