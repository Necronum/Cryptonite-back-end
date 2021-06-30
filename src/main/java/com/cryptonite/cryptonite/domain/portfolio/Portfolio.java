package com.cryptonite.cryptonite.domain.portfolio;

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
@Table(name = "PORTFOLIOS")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PORTFOLIO_ID", unique = true)
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
}
