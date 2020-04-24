package com.gymnasium.store.Dao;


import com.gymnasium.store.PO.ChartPO;
import com.gymnasium.store.PO.ProductPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 88
 */
public interface ProductDao extends JpaRepository<ProductPO, Integer>, JpaSpecificationExecutor<ProductPO>{

    ProductPO findProductPOById(Integer id);


    @Query(value = "SELECT COUNT( id ) FROM product WHERE product_type_id = :productTypeId AND putaway_status = :putawayStatus And" +
            " create_time LIKE CONCAT(:times,'%');",nativeQuery = true)
    Integer countProduct(Integer productTypeId,Integer putawayStatus, String times);

    @Query(value = "select COUNT( id )AS result, QUARTER(create_time) AS season from product where if(?1!=''," +
            "product_type_id = ?1,1=1) and if (?2!='',putaway_status = ?2,1=1) " +
            "GROUP BY QUARTER(create_time)",nativeQuery = true)
    List<ChartPO> quarterProduct(Integer productTypeId, Integer putawayStatus, String times);


    /**
     * 查询当月有多少天
     * @param times
     * @return
     */
    @Query(value = "SELECT DAYOFMONTH(LAST_DAY(:times))", nativeQuery = true)
    Integer queryDay(String times);

    @Query(value = "SELECT COUNT(id) FROM product WHERE IF(?1 != '',product_type_id =?1,1=1) " +
            "AND IF(?2 !='',putaway_status = ?2, 1=1) AND create_time BETWEEN ?3 AND ?4",
            nativeQuery = true)
    Integer quarterProductNum(Integer productTypeId, Integer putawayStatus, String startTime, String endTime);

}
