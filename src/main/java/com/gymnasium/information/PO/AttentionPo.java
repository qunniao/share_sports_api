package com.gymnasium.information.PO;

import com.gymnasium.personnel.PO.UserPO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "information_attention", catalog = "gymnasium")
public class AttentionPo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "follow_user_id")
    private String followUserId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId",insertable = false, updatable = false)
    private UserPO userPO;
}
