package com.nexora.course_service.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final String VIDEO_UPLOAD_FOLDER = "course/lectures";
    private final String VIDEO_RESOURCE_FOLDER = "course/lecture_resources";
    private final Cloudinary cloudinary;


    public Map uploadVideo(MultipartFile file, String publicID) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("video/")) {
            throw new IllegalArgumentException("File is not a video");
        }

        long maxSize = 100 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("File too large");
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String extension = originalName.substring(originalName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;

        Map uploadResult;

        try {
            uploadResult = cloudinary.uploader().upload(
                    file.getInputStream(), // ✅ FIXED
                    Map.of(
                            "folder", VIDEO_UPLOAD_FOLDER,
                            "public_id", fileName,
                            "resource_type", "video"
                    )
            );
        } catch (Exception e) {
            throw new IOException("Upload failed", e);
        }

        // delete old AFTER success
        if (publicID != null && !publicID.isBlank()) {
            cloudinary.uploader().destroy(publicID, Map.of("resource_type", "video"));
        }

        return uploadResult;
    }

    public Map uploadResource(MultipartFile file, String publicID) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String type = file.getContentType();

        if (type == null || !isValidResourceType(type)) {
            throw new IllegalArgumentException("Invalid resource type");
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String extension = originalName.substring(originalName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;

        Map uploadResult;

        try {
            uploadResult = cloudinary.uploader().upload(
                    file.getBytes(), // ✅ FIXED
                    Map.of(
                            "folder", VIDEO_RESOURCE_FOLDER,
                            "public_id", fileName,
                            "resource_type", "auto"
                    )
            );
        } catch (Exception e) {
            throw new IOException("Upload failed", e);
        }

        // delete old AFTER success
        if (publicID != null && !publicID.isBlank()) {
            cloudinary.uploader().destroy(publicID, Map.of("resource_type", "raw"));
        }

        return uploadResult;
    }

    private boolean isValidResourceType(String type) {
        return type.equals("application/pdf") ||
                type.equals("application/zip") ||
                type.startsWith("image/");
    }

}
