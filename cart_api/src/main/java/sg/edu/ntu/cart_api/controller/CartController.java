package sg.edu.ntu.cart_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.CartRepository;
import sg.edu.ntu.cart_api.repository.ProductRepository;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CartRepository cartRepo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> findAll() {
        List<Cart> cart = (List<Cart>) cartRepo.findAll();
        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(cart);
    }

    // add item /POST /cart/add
    @RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
    public ResponseEntity add(@RequestParam Optional<Integer> quantity, @PathVariable int productId) {

        Optional<Product> optionalProduct = (Optional<Product>) productRepo.findById(productId);
        if (optionalProduct.isPresent()) {

            Optional<Cart> optionalCart = (Optional<Cart>) cartRepo.findByProductId(productId);
            if (optionalCart.isPresent()) {
                Cart cartItem = optionalCart.get();
                int currentQuantity = cartItem.getQuantity();
                cartItem.setQuantity(quantity.orElseGet(() -> currentQuantity + 1)); // if quantity does not exist, take
                                                                                     // currentQuantity + 1
                cartRepo.save(cartItem);
            } else {
                Cart newCartItem = new Cart();
                newCartItem.setQuantity(1);
                newCartItem.setProduct(optionalProduct.get());
                cartRepo.save(newCartItem);
            }
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    // decrease item /POST /cart/decrement
    @RequestMapping(value = "/decrement/{productId}", method = RequestMethod.POST)
    public ResponseEntity decrement(@PathVariable int productId) {
        Optional<Cart> optionalCart = (Optional<Cart>) cartRepo.findByProductId(productId);
        if (optionalCart.isPresent()) {
            Cart cartItem = optionalCart.get();
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity <= 1) {
                cartRepo.delete(cartItem);
                return ResponseEntity.ok().build();
            } else {
                cartItem.setQuantity(currentQuantity - 1);
                cartRepo.save(cartItem);
                return ResponseEntity.ok().body(cartItem);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    // empty cart /POST /cart/clear
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public ResponseEntity<List<Cart>> clearCart() {
        List<Cart> cart = (List<Cart>) cartRepo.findAll();
        if (cart.isEmpty()) {
            return ResponseEntity.status(304).build();
        }
        cartRepo.deleteAll();
        return ResponseEntity.ok().build();
    }
}
