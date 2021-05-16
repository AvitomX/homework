package ru.tfs.spring.data.entity.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.clients.Client;
import ru.tfs.spring.data.entity.dict.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ords")
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    @JsonView(Views.Id.class)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.FullOrder.class)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonView(Views.FullOrder.class)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    @JsonView(Views.FullOrder.class)
    private Shipment shipment;

    @NotNull(message = "Currency is mandatory")
    @ManyToOne
    @JoinColumn(name = "currency_id")
    @JsonView(Views.FullOrder.class)
    private Currency currency;

    @Version
    private Integer version;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;

    public void addItem(OrderItem item) {
        items.add(item);
    }
}
