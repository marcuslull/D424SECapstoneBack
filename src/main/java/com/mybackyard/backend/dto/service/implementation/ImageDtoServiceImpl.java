package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.ImageDtoMapper;
import com.mybackyard.backend.dto.model.ImageDto;
import com.mybackyard.backend.dto.service.interfaces.ImageDtoService;
import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageDtoServiceImpl implements ImageDtoService {

    private final ImageDtoMapper imageDtoMapper;
    private final DtoValidator dtoValidator;

    public ImageDtoServiceImpl(ImageDtoMapper imageDtoMapper, DtoValidator dtoValidator) {
        this.imageDtoMapper = imageDtoMapper;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public List<ImageDto> processOutgoingImageList(List<Image> imageList) {
        return imageDtoMapper.imagesToDtos(imageList);
    }

    @Override
    public ImageDto processOutgoingImage(Image image) {
        return imageDtoMapper.imageToDto(image);
    }

    @Override
    public Image processIncomingImageDto(ImageDto imageDto, boolean isPatch) {
        if (!dtoValidator.isWellFormed(imageDto)) {
            throw new RuntimeException("Malformed DTO");
        }
        return imageDtoMapper.dtoToImage(imageDto, isPatch);
    }

    @Override
    public ImageDto addId(ImageDto imageDto, long imageId) {
        return new ImageDto(imageId, imageDto.location(), imageDto.yardId(), imageDto.plantId(), imageDto.animalId());
    }
}
