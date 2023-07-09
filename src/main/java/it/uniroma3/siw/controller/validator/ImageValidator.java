package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Image;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class ImageValidator implements Validator {
    private final List<String> permittedTypes = List.of("png", "jpg", "jpeg");
    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile image = (MultipartFile) target;
        String imageType = image.getContentType();
        if(imageType != null && !permittedTypes.contains(imageType)){
            errors.rejectValue("image", "image.invalidFormat");
        }
    }
}
