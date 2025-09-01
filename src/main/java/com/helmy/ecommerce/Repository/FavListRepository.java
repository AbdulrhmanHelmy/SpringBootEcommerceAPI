package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.FavList;
import com.helmy.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavListRepository extends JpaRepository<FavList,Long> {
    FavList findByUser(User user);
}
