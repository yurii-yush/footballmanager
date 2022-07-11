package com.codeseek.repository;

import com.codeseek.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {

    // @Modifying
    // @Query("update Order o set o.status = :newOrderStatus where o.id = :orderId")
    // void updateOrderStatus(OrderStatus newOrderStatus, Long orderId);
}
