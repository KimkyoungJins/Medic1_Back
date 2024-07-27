package org.lion.medicapi.util;

import org.lion.medicapi.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

import static org.lion.medicapi.util.Const.*;

public final class FileUtils {

    private FileUtils() {
    }

    public static String getFilePullPath(FileInfo fileInfo, String rootDirectory) {
        final String fileName = fileInfo.getFileName();
        final String fileUrl = fileInfo.getFileUrl();
        final String fileExt = fileInfo.getFileExt();

        return rootDirectory + fileUrl + "/" + fileName + "." + fileExt;
    }

    public static FileInfo transformMultipart(MultipartFile multipartFile, ImageType imageType) {

        final String randomFileName = UUID.randomUUID().toString();
        final String directoryName = switch (imageType) {
            case REVIEW -> DIRECTORY_REVIEW;
            case USER_PROFILE -> DIRECTORY_PROIFLE;
            case PRODUCT -> DIRECTORY_PRODUCT;
        };
        final String fileExt = extractExt(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        return FileInfo.builder()
                .fileName(randomFileName)
                .fileOriName(multipartFile.getOriginalFilename())
                .fileUrl(directoryName)
                .fileExt(fileExt)
                .fileSize(multipartFile.getSize())
                .build();
    }

    public static String extractExt(String fileName) {
        final int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }
}
