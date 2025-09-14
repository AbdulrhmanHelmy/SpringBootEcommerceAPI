package com.helmy.ecommerce.Service.Favlist;

import com.helmy.ecommerce.Model.FavList;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.FavListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FavListServiceIMPL implements FavListService {

    private final FavListRepository favListRepository;

    public FavListServiceIMPL(FavListRepository favListRepository) {
        this.favListRepository = favListRepository;
    }

    @Override
    public FavList getFavList(User user) {
        FavList list = favListRepository.findByUser(user);
        if (list == null) {
            list = new FavList();
            list.setUser(user);
            list.setProducts(new ArrayList<>());
            favListRepository.save(list);
        }
        return list;
    }

    @Override
    @Transactional
    public FavList addItem(User user, Product product) {
        FavList favList = favListRepository.findByUser(user);
        favList.addItem(product);
        return favListRepository.save(favList);
    }

    @Override
    @Transactional
    public FavList deleteItem(User user, Product product) {
        FavList favList = favListRepository.findByUser(user);
        favList.removeItem(product);
        return favListRepository.save(favList);
    }

    @Override
    @Transactional
    public void clearAll(User user) {
        FavList favList = favListRepository.findByUser(user);
        favList.deleteAll();
        favListRepository.save(favList);
    }
}
