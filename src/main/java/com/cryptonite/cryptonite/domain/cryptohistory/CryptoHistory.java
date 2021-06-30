package com.cryptonite.cryptonite.domain.cryptohistory;

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
@Table(name = "CRYPTO_HISTORY")
public class CryptoHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRYPTO_HISTORY_ID", unique = true)
    private Long id;

    @CreatedDate
    private LocalDateTime operationDate;

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

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private OperationStatus operationStatus = OperationStatus.NEW;
}
