package org.lion.medicapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.FileInfo;
import org.lion.medicapi.exception.APIException;
import org.lion.medicapi.repository.FileRepository;
import org.lion.medicapi.util.FileUtils;
import org.lion.medicapi.util.ImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.lion.medicapi.util.Const.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    @Value("${path.image}")
    private String ROOT_PATH;
    private final FileRepository fileRepository;

    public UrlResource getFileV1(Long fileId) {
        final FileInfo fileInfo = getFileInfo(fileId);
        final String targetFile = FileUtils.getFilePullPath(fileInfo, ROOT_PATH);

        try {
            return new UrlResource("file:" + targetFile);
        } catch (MalformedURLException e) {
            log.error("file not found", e);
            throw new APIException("file not found", HttpStatus.BAD_REQUEST);
        }
    }

    public byte[] getFileV2(Long fileId) {
        final FileInfo fileInfo = getFileInfo(fileId);
        final String targetFile = FileUtils.getFilePullPath(fileInfo, ROOT_PATH);

        try {
            return FileCopyUtils.copyToByteArray(new File(targetFile));
        } catch (IOException e) {
            log.error("file not found", e);
            throw new APIException("file not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public List<FileInfo> saveFile(List<MultipartFile> files, ImageType imageType) {

        if (files.isEmpty()) {
            return null;
        }

        final String directoryName = switch (imageType) {
            case REVIEW -> DIRECTORY_REVIEW;
            case USER_PROFILE -> DIRECTORY_PROIFLE;
            case PRODUCT -> DIRECTORY_PRODUCT;
        };

        List<FileInfo> result = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            final String randomFileName = UUID.randomUUID().toString();
            final String fileExt = FileUtils.extractExt(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            final FileInfo fileInfo = FileInfo.builder()
                    .fileName(randomFileName)
                    .fileOriName(multipartFile.getOriginalFilename())
                    .fileUrl(directoryName)
                    .fileExt(fileExt)
                    .fileSize(multipartFile.getSize())
                    .build();
            log.info("fileInfo[{}]", fileInfo);

            // Image Save To Server
            saveImage(multipartFile, directoryName, randomFileName, fileExt);

            // DB save
            result.add(fileRepository.save(fileInfo));
        }

        return result;
    }

    private FileInfo getFileInfo(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new APIException("file not found", HttpStatus.BAD_REQUEST));
    }

    private void saveImage(MultipartFile multipartFile, String directoryName, String fileName, String ext) {
        final String path = ROOT_PATH + directoryName;
        createImageDirectory(path);

        try {
            multipartFile.transferTo(new File(path + "/" + fileName + "." + ext));
        } catch (IOException e) {
            log.error("file save fail", e);
        }
    }

    private void createImageDirectory(String path) {
        final File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉터리 생성
        }
    }
}
