package com.mybackyard.backend.dto.service.interfaces;

import com.mybackyard.backend.dto.model.ImageDto;
import com.mybackyard.backend.model.Image;

import java.util.List;

public interface ImageDtoService {

    List<ImageDto> processOutgoingImageList(List<Image> imageList);

    ImageDto processOutgoingImage(Image image);

    Image processIncomingImageDto(ImageDto imageDto, boolean isPatch);

    ImageDto addId(ImageDto imageDto, long imageId);
}
