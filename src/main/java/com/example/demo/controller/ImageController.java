package com.example.demo.controller;


import com.example.demo.dto.ImageResponse;
import com.example.demo.entities.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageStorageService imagenService;
    private final CloudinaryService cloudinaryService;


    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> createFlight(@RequestParam("price") Double price,
                                                      @RequestParam("capacity") int capacity,
                                                      @RequestParam("duration") String duration,
                                                      @RequestParam("image") MultipartFile image,
                                                      @RequestParam("departureTime") String departureTime) {
        try {
            ImageResponse response = imagenService.createFlight(price, capacity, duration, image, departureTime);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // Manejo de excepciones en caso de error al cargar la imagen
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/list")
    public ResponseEntity<List<Image>> list(){
        List<Image> list = imagenService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

 /*   @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile)throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null){
            return new ResponseEntity<>(new Mensaje("imagen no válida"), HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Image imagen =
                new Image((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("public_id"));
        imagenService.save(imagen);
        return new ResponseEntity<>(new Mensaje("imagen subida"), HttpStatus.OK);
    }*/



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)throws IOException {
        if(!imagenService.exists(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

        imagenService.delete(id);
        return new ResponseEntity<>(new Mensaje("imagen eliminada"), HttpStatus.OK);
    }

}



