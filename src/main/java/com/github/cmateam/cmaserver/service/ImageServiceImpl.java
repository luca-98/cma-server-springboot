package com.github.cmateam.cmaserver.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

import com.github.cmateam.cmaserver.entity.ImageEntity;
import com.github.cmateam.cmaserver.entity.ServiceReportEntity;
import com.github.cmateam.cmaserver.repository.ImageRepository;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl {

    private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final String CMA_TEMP_UPLOAD_FOLDER = "cma_temp_image";
    private final String CMA_UPLOAD_FOLDER = "cma_image";
    private final String CMA_SMALL_IMAGE_FOLDER = "cma_small_image";
    private final int WIDTH_OF_SMALL_IMAGE = 400;
    private final String FILE_EXTENSION = "jpg";
    private final Path fileStorageTempLocation;
    private final Path fileStorageLocation;
    private final Path fileSmallStorageLocation;
    private ServiceReportRepository serviceReportRepository;
    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ServiceReportRepository serviceReportRepository, ImageRepository imageRepository) {
        this.serviceReportRepository = serviceReportRepository;
        this.imageRepository = imageRepository;

        String storageConfig = System.getProperty("user.home");
        this.fileStorageTempLocation = Paths.get(storageConfig).toAbsolutePath().normalize()
                .resolve(CMA_TEMP_UPLOAD_FOLDER);
        this.fileStorageLocation = Paths.get(storageConfig).toAbsolutePath().normalize().resolve(CMA_UPLOAD_FOLDER);
        this.fileSmallStorageLocation = Paths.get(storageConfig).toAbsolutePath().normalize().resolve(CMA_UPLOAD_FOLDER)
                .resolve(CMA_SMALL_IMAGE_FOLDER);
        logger.info(String.format("Folder temp upload image: %s", this.fileStorageTempLocation.toString()));
        logger.info(String.format("Folder upload image: %s", this.fileStorageLocation.toString()));
        if (Files.exists(this.fileStorageTempLocation) && !Files.isDirectory(this.fileStorageTempLocation)) {
            try {
                Files.deleteIfExists(this.fileStorageTempLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Files.exists(this.fileStorageLocation) && !Files.isDirectory(this.fileStorageLocation)) {
            try {
                Files.deleteIfExists(this.fileStorageLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Files.exists(this.fileSmallStorageLocation) && !Files.isDirectory(this.fileSmallStorageLocation)) {
            try {
                Files.deleteIfExists(this.fileSmallStorageLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(this.fileStorageTempLocation)) {
            try {
                Files.createDirectories(this.fileStorageTempLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(this.fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(this.fileSmallStorageLocation)) {
            try {
                Files.createDirectories(this.fileSmallStorageLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ResponseEntity<?> uploadImage(UUID serviceReportId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is not exist");
        }
        // check service report
        ServiceReportEntity serviceReportEntity = serviceReportRepository.getOne(serviceReportId);
        if (serviceReportEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service Report does not exist");
        }

        // process image

        if (storeTempFile(file)) {
            File fileTemp = this.fileStorageTempLocation.resolve(file.getOriginalFilename()).toFile();
            String mimetype = new MimetypesFileTypeMap().getContentType(fileTemp);
            String type = mimetype.split("/")[0];
            if (!type.equals("image")) {
                clearTempFolder();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This file is not an image");
            }
            // convert to jpg and save file
            UUID newImageId = saveImageToDatabase(serviceReportEntity);
            Path targetLocation = this.fileStorageLocation
                    .resolve(String.format("%s.%s", newImageId.toString(), FILE_EXTENSION));
            Path targetSmallLocation = this.fileSmallStorageLocation
                    .resolve(String.format("%s.%s", newImageId.toString(), FILE_EXTENSION));
            try {
                BufferedImage originalImage = ImageIO.read(fileTemp);
                // jpg needs BufferedImage.TYPE_INT_RGB
                // png needs BufferedImage.TYPE_INT_ARGB

                // create a blank, RGB, same width and height
                BufferedImage newBufferedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                // draw a white background and puts the originalImage on it.
                newBufferedImage.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);
                ImageIO.write(newBufferedImage, "jpg", targetLocation.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearTempFolder();

            // create small image
            try {
                BufferedImage originalImage = ImageIO.read(targetLocation.toFile());
                int targetWidth = 0;
                int targetHeight = 0;
                if (originalImage.getWidth() <= WIDTH_OF_SMALL_IMAGE) {
                    targetWidth = originalImage.getWidth();
                    targetHeight = originalImage.getHeight();
                } else {
                    targetWidth = WIDTH_OF_SMALL_IMAGE;
                    double ratio = (double) WIDTH_OF_SMALL_IMAGE / (double) originalImage.getWidth();
                    targetHeight = (int) ((double) originalImage.getHeight() * ratio);
                    if (targetHeight <= 0) {
                        targetHeight = originalImage.getHeight();
                    }
                }
                BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = resizedImage.createGraphics();
                graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
                graphics2D.dispose();
                ImageIO.write(resizedImage, "jpg", targetSmallLocation.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newImageId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private UUID saveImageToDatabase(ServiceReportEntity serviceReportEntity) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setStatus(1);
        imageEntity.setCreatedAt(new Date());
        imageEntity.setUpdatedAt(new Date());
        imageEntity.setServiceReportByServiceReportId(serviceReportEntity);
        imageEntity = imageRepository.save(imageEntity);
        return imageEntity.getId();
    }

    private boolean storeTempFile(MultipartFile file) {
        try {
            Path targetLocation = this.fileStorageTempLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void clearTempFolder() {
        try {
            File tempFolder = this.fileStorageTempLocation.toFile();
            for (File fileEntry : tempFolder.listFiles()) {
                Files.deleteIfExists(fileEntry.toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getSmallImage(UUID id) throws IOException {
        Optional<ImageEntity> imageOptional = imageRepository.findById(id);
        if (!imageOptional.isPresent()) {
            return null;
        }
        Path filePath = this.fileSmallStorageLocation.resolve(String.format("%s.%s", id.toString(), FILE_EXTENSION));
        return Files.readAllBytes(filePath);
    }

    public byte[] getFullImage(UUID id) throws IOException {
        Optional<ImageEntity> imageOptional = imageRepository.findById(id);
        if (!imageOptional.isPresent()) {
            return null;
        }
        Path filePath = this.fileStorageLocation.resolve(String.format("%s.%s", id.toString(), FILE_EXTENSION));
        return Files.readAllBytes(filePath);
    }

    public List<UUID> getByServiceReport(UUID id) {
        List<UUID> ret = new ArrayList<>();
        ServiceReportEntity serviceReportEntity = serviceReportRepository.getOne(id);
        if (serviceReportEntity == null) {
            return ret;
        }
        List<ImageEntity> listImage = serviceReportEntity.getImagesById();
        for (ImageEntity i : listImage) {
            ret.add(i.getId());
        }
        return ret;
    }

    public void deleteImage(UUID id) {
        Path targetLocation = this.fileStorageLocation.resolve(String.format("%s.%s", id.toString(), FILE_EXTENSION));
        Path targetSmallLocation = this.fileSmallStorageLocation
                .resolve(String.format("%s.%s", id.toString(), FILE_EXTENSION));
        try {
            Files.deleteIfExists(targetLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Files.deleteIfExists(targetSmallLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<ImageEntity> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            imageRepository.delete(imageOptional.get());
        }
    }
}
