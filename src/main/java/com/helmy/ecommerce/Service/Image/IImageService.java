package com.helmy.ecommerce.Service.Image;

import com.helmy.ecommerce.Model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {

     Image upload(MultipartFile file)throws IOException ;
     void delete(Long id) throws IOException;

}
