package com.mybackyard.backend.validation.implementation;

import com.mybackyard.backend.dto.model.*;
import com.mybackyard.backend.model.enums.*;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DtoValidatorImpl implements DtoValidator {

    private static final Logger logger = LoggerFactory.getLogger(DtoValidatorImpl.class);

    @Override
    public boolean isWellFormed(Object dtoObject) {
        return switch (dtoObject.getClass().getSimpleName()) {
            case "AnimalDto" -> isWellFormedAnimalDto((AnimalDto) dtoObject);
            case "ImageDto" -> isWellFormedImageDto((ImageDto) dtoObject);
            case "NoteDto" -> isWellFormedNoteDto((NoteDto) dtoObject);
            case "PlantDto" -> isWellFormedPlantDto((PlantDto) dtoObject);
            case "UserDto" -> isWellFormedUserDto((UserDto) dtoObject);
            case "YardDto" -> isWellFormedYardDto((YardDto) dtoObject);
            default -> false;
        };
    }

    private boolean isWellFormedAnimalDto(AnimalDto animalDto) {
        boolean isIdGood = idCheck(animalDto.id());
        boolean isNameGood = stringCheck(animalDto.name());
        boolean isEnumSubTypeGood = enumCheck("AnimalSubType", animalDto.subType());
        boolean isEnumDietTypeGood = enumCheck("DietType", animalDto.dietType());
        boolean isNativeAreaTypeGood = enumCheck("NativeAreaType", animalDto.nativeAreaType());
        boolean isNoteIdsGood = idCheck(animalDto.noteIds());
        boolean isImageIdsGood = idCheck(animalDto.imageIds());
        boolean isYardIdGood = idCheck(animalDto.yardId());

        logger.info("DTOValidator: {} " + isIdGood + isNameGood + isEnumSubTypeGood + isEnumDietTypeGood + isNativeAreaTypeGood +
                isNoteIdsGood + isImageIdsGood + isYardIdGood, Thread.currentThread().getStackTrace()[1].getMethodName());

        return isIdGood && isNameGood && isEnumSubTypeGood && isEnumDietTypeGood && isNativeAreaTypeGood &&
                isNoteIdsGood && isImageIdsGood && isYardIdGood;
    }

    private boolean isWellFormedImageDto(ImageDto imageDto) {
        boolean isIdGood = idCheck(imageDto.id());
        boolean isLocationGood = urlCheck(imageDto.location());
        boolean isYardIdGood = idCheck(imageDto.yardId());
        boolean isPlantIdGood = idCheck(imageDto.plantId());
        boolean isAnimalIdGood = idCheck(imageDto.animalId());

        logger.info("DTOValidator: {} " + isIdGood + isLocationGood + isYardIdGood + isPlantIdGood + isAnimalIdGood,
                Thread.currentThread().getStackTrace()[1].getMethodName());

        return (isIdGood && isLocationGood && isYardIdGood && isPlantIdGood && isAnimalIdGood);
    }

    private boolean isWellFormedNoteDto(NoteDto noteDto) {
        boolean isIdGood = idCheck(noteDto.id());
        boolean isCommentGood = stringCheck(noteDto.comment());
        boolean isAnimalIdGood = idCheck(noteDto.animalId());
        boolean isYardIdGood = idCheck(noteDto.yardId());
        boolean isPlantIdGood = idCheck(noteDto.plantId());

        logger.info("DTOValidator: {} " + isIdGood + isCommentGood + isAnimalIdGood + isYardIdGood + isPlantIdGood,
                Thread.currentThread().getStackTrace()[1].getMethodName());

        return (isIdGood && isCommentGood && isAnimalIdGood && isYardIdGood && isPlantIdGood);
    }

    private boolean isWellFormedPlantDto(PlantDto plantDto) {
        boolean isIdGood = idCheck(plantDto.id());
        boolean isNameGood = stringCheck(plantDto.name());
        boolean isHardinessZoneGood = enumCheck("HardinessZone", plantDto.hardinessZone());
        boolean isNativeAreaTypeGood = enumCheck("NativeAreaType", plantDto.nativeAreaType());
        boolean isPlantSubTypeGood = enumCheck("PlantSubType", plantDto.plantSubType());
        boolean isSoilTypeGood = enumCheck("SoilType", plantDto.soilType());
        boolean isSunExposureGood = enumCheck("SunExposure", plantDto.sunExposure());
        boolean isWateringFrequencyGood = enumCheck("WateringFrequency", plantDto.wateringFrequency());
        boolean isNoteIdsGood = idCheck(plantDto.noteIds());
        boolean isImageIdsGood = idCheck(plantDto.imageIds());
        boolean isYardIdGood = idCheck(plantDto.yardId());

        logger.info("DTOValidator: {} " + isIdGood + isNameGood + isHardinessZoneGood + isNativeAreaTypeGood +
                isPlantSubTypeGood + isSoilTypeGood + isSunExposureGood + isWateringFrequencyGood + isNoteIdsGood +
                isImageIdsGood + isYardIdGood, Thread.currentThread().getStackTrace()[1].getMethodName());

        return (isIdGood && isNameGood && isHardinessZoneGood && isNativeAreaTypeGood && isPlantSubTypeGood &&
                isSoilTypeGood && isSunExposureGood && isWateringFrequencyGood && isNoteIdsGood && isImageIdsGood &&
                isYardIdGood);
    }

    private boolean isWellFormedUserDto(UserDto userDto) {
        boolean isIdGood = idCheck(userDto.id());
        boolean isFirstGood = stringCheck(userDto.first());
        boolean isLastGood = stringCheck(userDto.last());
        boolean isEmailGood = emailCheck(userDto.email());
        boolean isPasswordGood = stringCheck(userDto.apiKey());
        boolean isYardIdsGood = idCheck(userDto.yardIds());

        logger.info("DTOValidator: {} " + isIdGood + isFirstGood + isLastGood + isEmailGood + isPasswordGood +
                isYardIdsGood, Thread.currentThread().getStackTrace()[1].getMethodName());

        return (isIdGood && isFirstGood && isLastGood && isEmailGood && isPasswordGood && isYardIdsGood);
    }

    private boolean isWellFormedYardDto(YardDto yardDto) {
        boolean isIdGood = idCheck(yardDto.id());
        boolean isNameGood = stringCheck(yardDto.name());
        boolean isHardinessZoneGood = enumCheck("HardinessZone", yardDto.hardinessZone());
        boolean isSoilTypeGood = enumCheck("SoilType", yardDto.soilType());
        boolean isSunExposureGood = enumCheck("SunExposure", yardDto.sunExposure());
        boolean isYardSubTypeGood = enumCheck("YardSubType", yardDto.yardSubType());
        boolean isNoteIdsGood = idCheck(yardDto.noteIds());
        boolean isAnimalIdsGood = idCheck(yardDto.animalIds());
        boolean isPlantIdsGood = idCheck(yardDto.plantIds());
        boolean isUserIdGood = idCheck(yardDto.userId());

        logger.info("DTOValidator: {} " + isIdGood + isNameGood + isHardinessZoneGood + isSoilTypeGood +
                isSunExposureGood + isYardSubTypeGood + isNoteIdsGood + isAnimalIdsGood + isPlantIdsGood +
                isUserIdGood, Thread.currentThread().getStackTrace()[1].getMethodName());

        return (isIdGood && isNameGood && isHardinessZoneGood && isSoilTypeGood && isSunExposureGood &&
                isYardSubTypeGood && isNoteIdsGood && isAnimalIdsGood && isPlantIdsGood && isUserIdGood);
    }

    private boolean idCheck(Object longOrLongList) {
        // check if long or List<long>, the make sure >= 0. 0 is the default long for a record.
        if (longOrLongList == null) {
            return true;
        }
        if (longOrLongList instanceof Long) {
            return (long) longOrLongList >= 0;
        }
        if (longOrLongList instanceof ArrayList<?>) {
            boolean allLongsGood = true;
            for (Object id : (ArrayList<?>)longOrLongList) {
                if (!((long) id >= 0)) {
                    allLongsGood = false;
                    break;
                }
            }
            return allLongsGood;
        }
        return false;
    }
    private boolean stringCheck(String string) {
        // if the field is not empty, check that alphanumerical with spaces and select special chars
        if (string == null) {
            return true;
        }
        return string.matches("^[a-zA-Z0-9 ]+( ?[a-zA-Z0-9 -./?!$%&*(),;:@#_]+)*$");
    }
    private boolean enumCheck(String enumName, String value) {
        // if the field is empty let it pass otherwise makes sure the enumName is valid,
        // then make sure the value for that enum is valid
        // TODO: Logging is too verbose for end user - v.N
        if (value == null) {
            return true;
        }
        boolean goodEnumValue = false;

        if (EnumUtils.isValidEnum(AnimalSubType.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(DietType.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(HardinessZone.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(NativeAreaType.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(PlantSubType.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(SoilType.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(SunExposure.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(WateringFrequency.class, value)) { goodEnumValue = true; }
        else if (EnumUtils.isValidEnumIgnoreCase(YardSubType.class, value)) { goodEnumValue = true; }

        return goodEnumValue;
    }
    private boolean urlCheck(String url) {
        // TODO: This will have to wait until I know the format of the storage URLs - PROD
        return true;
    }
    private boolean emailCheck(String email) {
        // if this is empty let it through otherwise use regex to check for xx@xx format
        // . - _ are allowed in the alias and domain. The TLD is not checked
        if (email == null) {
            return true;
        }
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }
}
