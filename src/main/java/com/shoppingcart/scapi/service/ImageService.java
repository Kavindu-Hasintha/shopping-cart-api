package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Image;
import com.shoppingcart.scapi.exception.ImageNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image getImageById(Long id) throws ImageNotFoundException;
    void deleteImageById(Long id);
    Image saveImage(MultipartFile file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
