package com.ghostman.movie_streaming_service.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/video")
public class MovieStreamController {

    // Base directory where all videos are stored
    private final String BASE_VIDEO_PATH = "D:/G H O S T 3/Gospel/";

    @GetMapping("/stream/{filename}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String filename) {
        try {
            // Construct the full file path
            String fullPath = BASE_VIDEO_PATH + filename;
            File videoFile = new File(fullPath);

            System.out.println("Looking for video at: " + fullPath);
            System.out.println("File exists: " + videoFile.exists());
            System.out.println("File size: " + (videoFile.exists() ? videoFile.length() : "N/A"));

            if (!videoFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(videoFile));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(videoFile.length()))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Alternative endpoint that lists available videos
    @GetMapping("/list")
    public ResponseEntity<String[]> listVideos() {
        try {
            File videoDir = new File(BASE_VIDEO_PATH);
            if (!videoDir.exists() || !videoDir.isDirectory()) {
                return ResponseEntity.notFound().build();
            }

            // Filter for video files
            File[] videoFiles = videoDir.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".mp4") ||
                            name.toLowerCase().endsWith(".avi") ||
                            name.toLowerCase().endsWith(".mkv") ||
                            name.toLowerCase().endsWith(".mov")
            );

            if (videoFiles == null || videoFiles.length == 0) {
                return ResponseEntity.ok(new String[]{"No videos found"});
            }

            String[] videoNames = new String[videoFiles.length];
            for (int i = 0; i < videoFiles.length; i++) {
                videoNames[i] = videoFiles[i].getName();
            }

            return ResponseEntity.ok(videoNames);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}