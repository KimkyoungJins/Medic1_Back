package org.lion.medicapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.FileInfo;
import org.lion.medicapi.service.FileService;
import org.lion.medicapi.util.ImageType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/medic/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileInfo>> saveFile(final List<MultipartFile> files) {
        log.info("files size[{}]", files.size());
        return ResponseEntity.ok(fileService.saveFile(files, ImageType.PRODUCT));
    }

    @GetMapping("/v1")
    public ResponseEntity<?> getFileV1(@RequestParam final Long fileId) {
        log.info("fileId[{}]", fileId);
        return ResponseEntity.ok(fileService.getFileV1(fileId));
    }

    @GetMapping("/v2")
    public ResponseEntity<?> getFileV2(@RequestParam final Long fileId) {
        log.info("fileId[{}]", fileId);
        return ResponseEntity.ok(fileService.getFileV2(fileId));
    }
}
