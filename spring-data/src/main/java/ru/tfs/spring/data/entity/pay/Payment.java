package ru.tfs.spring.data.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.Currency;
import ru.tfs.spring.data.entity.orders.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(of = {"id", "cardPan"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    @JsonView(Views.Id.class)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @JsonView(Views.FullPayment.class)
    private String cardPan;

    @NotNull
    @Column(nullable = false)
    @JsonView(Views.FullPayment.class)
    private Float totalCost;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonView(Views.FullPayment.class)
    private Order order;

    @OneToOne
    @JoinColumn(name = "currency_id")
    @JsonView(Views.FullPayment.class)
    private Currency currency;

    @Version
    private Integer version;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;
}
