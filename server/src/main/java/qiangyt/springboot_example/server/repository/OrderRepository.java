package qiangyt.springboot_example.server.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import qiangyt.springboot_example.server.entity.OrderEO;


/**
 * @author
 *
 */
@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEO, UUID>{

    List<OrderEO> findByCustomerAccountId(UUID customerAccountId);

}
