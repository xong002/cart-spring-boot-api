package sg.edu.ntu.cart_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edu.ntu.cart_api.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer>{
    Optional<Cart> findByProductId(Integer id);
}
