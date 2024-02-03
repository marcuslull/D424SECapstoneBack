package com.mybackyard.backend.controller;

import com.mybackyard.backend.dto.model.ImageDto;
import com.mybackyard.backend.dto.service.interfaces.ImageDtoService;
import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.service.interfaces.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;
    private final ImageDtoService imageDtoService;

    public ImageController(ImageService imageService, ImageDtoService imageDtoService) {
        this.imageService = imageService;
        this.imageDtoService = imageDtoService;
    }

    @GetMapping("/image")
    public List<ImageDto> getImages() {
        List<Image> imageList = imageService.getAllImages();
        return imageDtoService.processOutgoingImageList(imageList);
    }

    @DeleteMapping("/image")
    public ResponseEntity<String> deleteImages() {
        // TODO: do something with this - v.N
        return ResponseEntity.ok("Doesn't do anything yet - May not need this");
    }

    @GetMapping("/image/{id}")
    public ImageDto getImage(@PathVariable String id) {
        Optional<Image> optionalImage = imageService.getImageById(Long.parseLong(id));
        if (optionalImage.isPresent()) {
            return imageDtoService.processOutgoingImage(optionalImage.get());
        }
        else { throw new NoSuchElementException("No such element found"); }
    }

    @PostMapping("/image")
    public ResponseEntity<ImageDto> postImage(@RequestBody ImageDto imageDto) {
        Image image = imageDtoService.processIncomingImageDto(imageDto, false);
        long imageId = imageService.saveImage(image);
        ImageDto recreatedImageDto = imageDtoService.addId(imageDto, imageId);
        return ResponseEntity.created(URI.create("/api/image/" + imageId)).body(recreatedImageDto);
    }

    @PatchMapping("/image/{id}")
    public ResponseEntity<ImageDto> patchImage(@PathVariable String id, @RequestBody ImageDto imageDto) {
        Image image = imageDtoService.processIncomingImageDto(imageDto, true);
        Image returnedImage = imageService.updateImage(id, image);
        ImageDto returnedImageDto = imageDtoService.processOutgoingImage(returnedImage);
        return ResponseEntity.ok(returnedImageDto);
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable String id) {
        imageService.deleteImageById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
