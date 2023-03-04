package sg.edu.ntu.cart_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    ProductRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = (List<Product>) repo.findAll();
        return ResponseEntity.ok().body(products);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        Optional<Product> optional = (Optional<Product>) repo.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok().body(repo.save(product));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable int id) {
        Optional<Product> optional = (Optional<Product>) repo.findById(id);
        if (optional.isPresent()) {
            product.setId(id);
            return ResponseEntity.ok().body(repo.save(product));
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> delete(@PathVariable int id) {
        Optional<Product> optional = (Optional<Product>) repo.findById(id);
        if(optional.isPresent()){
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}