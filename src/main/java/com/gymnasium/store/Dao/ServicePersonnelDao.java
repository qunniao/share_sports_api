package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ServicePersonnelPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:20
 * @Description:
 */
public interface ServicePersonnelDao extends JpaRepository<ServicePersonnelPO, Integer> {

    ServicePersonnelPO findByWorkNumber(String wokeNumber);

    ServicePersonnelPO findServicePersonnelPOById(Integer id);

    /**
     * 查询一条客服人员的数据
     * @return
     */
    @Query(value = "SELECT * FROM service_personnel  AS sp JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) " +
            "FROM service_personnel)-(SELECT MIN(id) FROM service_personnel))+(SELECT MIN(id) FROM service_personnel)) AS id) AS sp2 " +
            "WHERE sp.id >= sp2.id AND service_number < 6 AND status = 1 ORDER BY sp.id LIMIT 1" , nativeQuery = true)
    ServicePersonnelPO findByServiceNumber();

    @Query(value = "SELECT * FROM service_personnel WHERE status = 1 ORDER BY " +
            "service_number ASC LIMIT 1", nativeQuery = true)
    ServicePersonnelPO queryMinNumber();

    ServicePersonnelPO findByManagerId(Integer managerId);

}
