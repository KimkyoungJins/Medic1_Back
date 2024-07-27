package org.lion.medicapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.FileInfo;
import org.lion.medicapi.service.FileService;
import org.lion.medicapi.util.ImageType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/medic/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileInfo> saveFile(final MultipartFile file) {
        if (file.getOriginalFilename() != null) {
            log.info("fileName[{}]", file.getOriginalFilename());
        }
        if (file.getContentType() != null) {
            log.info("fileContentType[{}]", file.getContentType());
        }

        return ResponseEntity.ok(fileService.saveFile(file, ImageType.PRODUCT));
    }
}
