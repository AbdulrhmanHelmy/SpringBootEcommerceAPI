package com.helmy.ecommerce.Service.Favlist;

import com.helmy.ecommerce.Model.FavList;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;

public interface FavListService {
    FavList getFavList(User user);

    FavList addItem(User user, Product product);

    FavList deleteItem(User user, Product product);

    void clearAll(User user);

}