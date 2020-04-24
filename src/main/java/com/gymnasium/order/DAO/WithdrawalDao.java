package com.gymnasium.order.DAO;

import com.gymnasium.order.PO.WithdrawalPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/3 14:12
 * @Description:
 */
public interface WithdrawalDao extends JpaRepository<WithdrawalPO, Integer> {

    WithdrawalPO findByUserId(Integer userId);

    WithdrawalPO findWithdrawalPOById(Integer Id);

    Page<WithdrawalPO> findByStatus(Pageable pageable, Integer status);
}
