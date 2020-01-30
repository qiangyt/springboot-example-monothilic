package qiangyt.springboot_example.server.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import qiangyt.springboot_example.server.entity.ProductEO;


/**
 * @author
 *
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEO, UUID>{


}
