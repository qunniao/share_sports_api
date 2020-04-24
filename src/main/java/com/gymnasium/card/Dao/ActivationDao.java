package com.gymnasium.card.Dao;

        import com.gymnasium.card.PO.ActivationPO;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 王志鹏
 * @title: ActivationDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 10:37
 */
public interface ActivationDao extends JpaRepository<ActivationPO,Integer>, JpaSpecificationExecutor<ActivationPO> {

    ActivationPO queryByCardNum(String cardNum);

    ActivationPO findByCardNumAndCardPassWord(String cardNum, String password);

    ActivationPO queryTop1ByTypeAndStatus(int type,int status);

    int countByTypeAndStatus(int type,int status);

    Page<ActivationPO> findAllByStatus(Pageable pageable, Integer status);
}
