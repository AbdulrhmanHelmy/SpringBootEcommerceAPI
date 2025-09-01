package com.helmy.ecommerce.Controller;

import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.FavList;
import com.helmy.ecommerce.Model.Product;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.ProductRepository;
import com.helmy.ecommerce.Response.API_Response;
import com.helmy.ecommerce.Service.Favlist.FavListServiceIMPL;
import com.helmy.ecommerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite")
public class FavListController {
    private final FavListServiceIMPL favListServiceIMPL;
    private final UserService userService;
    private final ProductRepository productRepository;

    public FavListController(FavListServiceIMPL favListServiceIMPL, UserService userService, ProductRepository productRepository) {
        this.favListServiceIMPL = favListServiceIMPL;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<API_Response> get() {
        User u = userService.getCurrentUser();
        FavList favList=favListServiceIMPL.getFavList(u);
        API_Response apiResponse = new API_Response(
                "Founded",favList
        );
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/add")
    public ResponseEntity<API_Response> addItem(@RequestParam("productId") Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFound("Product Not Found"));
        User u = userService.getCurrentUser();
        API_Response apiResponse = new API_Response(
                "All items  from favourites", favListServiceIMPL.addItem(u, product)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/remove")
    public ResponseEntity<API_Response> removeItem(@RequestParam("productId") Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFound("Product Not Found"));
        User u = userService.getCurrentUser();
        API_Response apiResponse = new API_Response(
                "Item removed from favourites", favListServiceIMPL.deleteItem(u, product)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<API_Response> deleteAll() {
        User u = userService.getCurrentUser();
        favListServiceIMPL.clearAll(u);
        API_Response apiResponse = new API_Response("All items cleared from favourites", null);
        return ResponseEntity.ok(apiResponse);
    }


}
