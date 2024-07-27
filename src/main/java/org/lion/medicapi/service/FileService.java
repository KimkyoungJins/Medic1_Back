package org.lion.medicapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.FileInfo;
import org.lion.medicapi.repository.FileRepository;
import org.lion.medicapi.util.ImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    @Value("${path.image}")
    private String ROOT_PATH;
    private final FileRepository fileRepository;

    private static final String DIRECTORY_PRODUCT = "/product";
    private static final String DIRECTORY_REVIEW = "/review";
    private static final String DIRECTORY_PROIFLE = "/profile";


    @Transactional
    public FileInfo saveFile(MultipartFile multipartFile, ImageType imageType) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        final String randomFileName = UUID.randomUUID().toString();
        final String directoryName = switch (imageType) {
            case REVIEW -> DIRECTORY_REVIEW;
            case USER_PROFILE -> DIRECTORY_PROIFLE;
            case PRODUCT -> DIRECTORY_PRODUCT;
        };
        final String fileExt = extractExt(Objects.requireNonNull(multipartFile.getOriginalFilename()));

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
        return fileRepository.save(fileInfo);
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

    // 확장자 추출
    private String extractExt(String originalFilename) {
        final int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
