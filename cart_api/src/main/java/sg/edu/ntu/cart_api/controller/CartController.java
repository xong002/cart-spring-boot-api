package sg.edu.ntu.cart_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.repository.CartRepository;

@RestController
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    CartRepository cartrepo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> findAll() {
        List<Cart> cart = (List<Cart>) cartrepo.findAll();
        return ResponseEntity.ok().body(cart);
    }

    //add item /POST /cart/add
    //decrease item  /POST /cart/decrement
    //empty cart /POST /cart/clear
}
