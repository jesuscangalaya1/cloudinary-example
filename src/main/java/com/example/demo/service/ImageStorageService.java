package com.example.demo.service;

import com.example.demo.dto.ImageResponse;
import com.example.demo.entities.Image;
import com.example.demo.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    private final ImageRepository imagenRepository;
    private final CloudinaryService cloudinaryService;

    public List<Image> list(){
        return imagenRepository.findByOrderById();
    }

    public Optional<Image> getOne(Long id){
        return imagenRepository.findById(id);
    }

    public ImageResponse createFlight(Double price,int capacity,String duration,
                                      MultipartFile name, String departureTime) throws IOException {

        Map uploadResult = cloudinaryService.upload(name);
        String imageUrl = (String) uploadResult.get("url");

        // Aquí puedes hacer lo que necesites con la URL de la imagen en Cloudinary, como guardarla en tu repositorio

        Image flightImage = new Image();
        flightImage.setPrice(price);
        flightImage.setCapacity(capacity);
        flightImage.setDuration(duration);
        flightImage.setName(imageUrl);
        // Establecer otros detalles del vuelo según sea necesario

        // Guardar la instancia de Image en la base de datos
        Image savedImage = imagenRepository.save(flightImage);

        // Crear la respuesta con los detalles del vuelo guardado
        ImageResponse response = new ImageResponse();
        response.setId(savedImage.getId());
        response.setPrice(savedImage.getPrice());
        response.setCapacity(savedImage.getCapacity());
        response.setDuration(savedImage.getDuration());
        response.setName(savedImage.getName());
        return response;
    }


    public void delete(Long id){
        imagenRepository.deleteById(id);
    }

    public boolean exists(Long id){
        return imagenRepository.existsById(id);
    }

}

