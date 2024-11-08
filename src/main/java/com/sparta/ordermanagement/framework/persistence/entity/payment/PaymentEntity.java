package com.sparta.ordermanagement.framework.persistence.entity.payment;

import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_payment")
public class PaymentEntity extends BaseEntity {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "INTEGER")
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private PaymentState paymentState;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String pgProvider;

    @JoinColumn(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private OrderEntity orderEntity;

}
