package com.cryptonite.cryptonite.domain.localmarket;

import com.cryptonite.cryptonite.domain.user.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MARKET")
public class LocalMarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_ID", unique = true)
    private Long id;

    @ManyToOne(
            targetEntity = User.class,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private User user;

    @NotNull
    private String coinSymbol;

    @NotNull
    private String quantity;

    @NotNull
    private String coinSymbolFor;

    @NotNull
    private String quantityFor;
}
