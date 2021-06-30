package com.cryptonite.cryptonite.domain.markethistory;

import com.cryptonite.cryptonite.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MARKET_HISTORY")
public class MarketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_HISTORY_ID", unique = true)
    private Long id;

    @CreatedDate
    private LocalDateTime operationDate;

    @ManyToOne(
            targetEntity = User.class,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name="PURCHASING_USER_ID")
    private User purchasingUser;

    @NotNull
    private String purchasingCoinSymbol;

    @NotNull
    private String purchasingQuantity;

    @ManyToOne(
            targetEntity = User.class,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name="SELLING_USER_ID")
    private User sellingUser;

    @NotNull
    private String sellingCoinSymbol;

    @NotNull
    private String sellingQuantity;
}
