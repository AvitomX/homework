package ru.tfs.spring.data.entity.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.goods.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(of = {"id", "product", "quantity"})
@ToString(of = {"id", "quantity"})
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    @JsonView(Views.Id.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonView(Views.Id.class)
    private Order order;

    @NotNull(message = "product can't be NULL")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonView(Views.FullOrderItem.class)
    private Product product;

    @NotNull(message = "OrderItem quantity can't be NULL")
    @JsonView(Views.FullOrderItem.class)
    @Column(nullable = false)
    private Integer quantity;

    @Version
    private Integer version;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;
}
