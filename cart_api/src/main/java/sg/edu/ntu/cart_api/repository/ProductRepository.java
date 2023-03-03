package sg.edu.ntu.cart_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edu.ntu.cart_api.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{
    
}
