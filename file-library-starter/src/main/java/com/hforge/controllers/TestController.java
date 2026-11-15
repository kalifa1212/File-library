//package com.hforge.controllers;
//
//import com.hforge.model.FileInfo;
//import com.hforge.services.FileService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.concurrent.TimeUnit;
//
//@RestController
//@RequiredArgsConstructor
//public class TestController {
//
//    private final FileService fileService;
//
//    @PostMapping("/upload")
//    public FileInfo upload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
//        return fileService.upload(file);
//    }
//
//    @GetMapping("/stream/{videoId}/{filename:.+}")
//    public ResponseEntity<Resource> serveHlsFile(
//            @PathVariable("filename") String filename,
//            @PathVariable("videoId") String videoId) throws IOException {
//        System.out.println("--start---");
//        System.out.println("video path "+videoId);
//        System.out.println("Original file name "+filename);
//
//
//        // 1) Nettoyage : {*filename} capture le chemin avec un "/" en préfixe
//        String cleanFilename = filename.startsWith("/") ? filename.substring(1) : filename;
//       // String cleanPath = videoId.startsWith("/") ? videoId.substring(1) : videoId;
//
//        // 3) Résolution du chemin réel sur disque
//        Path videoDir = fileService.getPath("videos/2026/07/de48ba53-3e32-4241-9e4d-6b40efb6f871/hls");
//        //Path videoDir = fileService.getPath(cleanPath);
//        System.out.println("clean filename "+cleanFilename);
//        Path filePath = videoDir.resolve(cleanFilename);
//        //Path filePath = Path.of(videoId).resolve(cleanFilename);
//        //System.out.println(videoDir);
//        System.out.println("File path with name "+filePath);
//        // 4) SÉCURITÉ CRITIQUE : anti path-traversal
//        // Empêche un appel du type /api/videos/abc123/../../../etc/passwd
//
//        // 5) Vérifier que le fichier existe et n'est pas un dossier
//        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Resource resource = new FileSystemResource(filePath);
//        MediaType contentType = resolveContentType(cleanFilename);
//        System.out.println(contentType);
//        System.out.println("---------End--------");
//        boolean isPlaylist = cleanFilename.endsWith(".m3u8");
//
//        return ResponseEntity.ok()
//                .contentType(contentType)
//                // Le playlist peut être régénéré (live) -> pas de cache long
//                // Les segments, une fois écrits, ne changent plus -> cache long
//                .cacheControl(isPlaylist
//                        ? CacheControl.noCache()
//                        : CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic())
//                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*") // à restreindre en prod
//                .body(resource);
//    }
//
//    private MediaType resolveContentType(String filename) {
//        if (filename.endsWith(".m3u8")) {
//            return MediaType.valueOf("application/vnd.apple.mpegurl");
//        }
//        if (filename.endsWith(".ts")) {
//            return MediaType.valueOf("video/mp2t");
//        }
//        if (filename.endsWith(".m4s")) {
//            return MediaType.valueOf("video/iso.segment");
//        }
//        if (filename.endsWith(".mp4")) {
//            return MediaType.valueOf("video/mp4"); // pour un éventuel init segment fMP4
//        }
//        if (filename.endsWith(".key")) {
//            return MediaType.APPLICATION_OCTET_STREAM; // clé de chiffrement HLS (AES-128)
//        }
//        return MediaType.APPLICATION_OCTET_STREAM;
//    }
//
//}