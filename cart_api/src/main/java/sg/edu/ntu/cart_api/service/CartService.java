package sg.edu.ntu.cart_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.exception.NotFoundException;
import sg.edu.ntu.cart_api.repository.CartRepository;
import sg.edu.ntu.cart_api.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepo;

    @Autowired
    ProductRepository productRepo;

    public void add(int productId, Optional<Integer> quantity, int userId) throws NotFoundException {
        Optional<Cart> optionalCartItem = cartRepo.findByProductIdAndUserId(productId, userId);

        if (optionalCartItem.isPresent()) {

            // Product found in cart
            Cart cartItem = optionalCartItem.get();
            int currentQuantity = cartItem.getQuantity();
            cartItem.setQuantity(quantity.orElseGet(() -> currentQuantity + 1)); // If quantity param not exist, just
                                                                                 // increment by 1
            cartRepo.save(cartItem);
        } else {

            // Product not found in cart
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                Cart cartItem = new Cart();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity.orElseGet(() -> 1));
                cartRepo.save(cartItem);
            } else {
                throw new NotFoundException("UserID/ProductID is not found");
            }
        }
    }

    public void decrement(int productId, int userId) throws NotFoundException {
        Optional<Cart> optionalCart = cartRepo.findByProductIdAndUserId(productId, userId);

        if (optionalCart.isPresent()) {
            Cart cartItem = optionalCart.get();
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity <= 1) {
                cartRepo.delete(cartItem);
            } else {
                cartItem.setQuantity(currentQuantity - 1);
                cartRepo.save(cartItem);
            }
        } else {
            throw new NotFoundException("UserID/ProductId is not found");
        }
    }

}
