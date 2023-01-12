package nl.hsleiden.iprwc.s1132776.DAO;

import nl.hsleiden.iprwc.s1132776.model.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
