package nl.hsleiden.iprwc.s1132776.DAO;

import nl.hsleiden.iprwc.s1132776.model.database.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
