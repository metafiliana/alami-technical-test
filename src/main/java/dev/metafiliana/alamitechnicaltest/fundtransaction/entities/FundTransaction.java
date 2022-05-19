package dev.metafiliana.alamitechnicaltest.fundtransaction.entities;

import dev.metafiliana.alamitechnicaltest.fundtransaction.repositories.FundTransactionRepository;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class FundTransaction {
    public static final Integer FundTransactionState_Saving = 0;
    public static final Integer FundTransactionState_Lending = 1;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "state")
    private Integer state;
    @Column(name = "value")
    private BigInteger value;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private String memberName;
}
