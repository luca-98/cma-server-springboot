package com.github.cmateam.cmaserver.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.service.ImageServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("image")
public class ImageController {

    private ImageServiceImpl imageServiceImpl;

    @Autowired
    public ImageController(ImageServiceImpl imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam UUID serviceReportId, @RequestParam MultipartFile file) {
        return imageServiceImpl.uploadImage(serviceReportId, file);
    }

    @GetMapping(value = "/small/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getSmallImage(@PathVariable UUID id) throws IOException {
        return imageServiceImpl.getSmallImage(id);
    }

    @GetMapping(value = "/full/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFullImage(@PathVariable UUID id) throws IOException {
        return imageServiceImpl.getFullImage(id);
    }

    @GetMapping(value = "get-by-service-report/{id}")
    public List<UUID> getByServiceReport(@PathVariable UUID id) {
        return imageServiceImpl.getByServiceReport(id);
    }

    @DeleteMapping(value = "{id}")
    public void deleteImage(@PathVariable UUID id) {
         imageServiceImpl.deleteImage(id);
         return;
    }
}
