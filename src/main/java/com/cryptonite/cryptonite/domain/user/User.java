package com.cryptonite.cryptonite.domain.user;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistoryDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    private BigDecimal wallet;

    @OneToMany(
            targetEntity = Portfolio.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(
            targetEntity = LocalMarket.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<LocalMarket> marketOffers = new ArrayList<>();

    @OneToMany(
            targetEntity = WalletHistory.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<WalletHistory> walletHistories = new ArrayList<>();

    @OneToMany(
            targetEntity = MarketHistory.class,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "purchasingUser"
    )
    private List<MarketHistory> purchaseMarketHistories = new ArrayList<>();

    @OneToMany(
            targetEntity = MarketHistory.class,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "sellingUser"
    )
    private List<MarketHistory> sellMarketHistories = new ArrayList<>();


    @OneToMany(
            targetEntity = CryptoHistory.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<CryptoHistory> cryptoHistories = new ArrayList<>();
}
