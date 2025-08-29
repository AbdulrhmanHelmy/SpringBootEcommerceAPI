package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
