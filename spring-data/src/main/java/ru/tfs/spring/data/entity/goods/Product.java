package ru.tfs.spring.data.entity.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.tfs.spring.data.entity.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(
        name = "graph.Product-Parameters-Infos",
        attributeNodes = {
                @NamedAttributeNode(value = "parameters", subgraph = "subgraph.type"),
                @NamedAttributeNode(value = "infos", subgraph = "subgraph.language")
        },
        subgraphs = {
                @NamedSubgraph(name = "subgraph.type",
                        attributeNodes = @NamedAttributeNode(value = "type")),
                @NamedSubgraph(name = "subgraph.language",
                        attributeNodes = @NamedAttributeNode(value = "language"))
        })
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "uePrice"})
@ToString(of = {"id", "uePrice"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    @JsonView(Views.Id.class)
    private Long id;

    @NotNull(message = "Product uePrice is mandatory")
    @Column(nullable = false)
    @JsonView(Views.FullProduct.class)
    private Long uePrice;

    @NotNull(message = "Product must have at least one parameter!")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.FullProduct.class)
    private Set<Parameter> parameters = new HashSet<>();

    @NotNull(message = "Product must have at least one info!")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.FullProduct.class)
    private Set<Info> infos = new HashSet<>();

    @Version
    private Integer version;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;

    public void addParameter(Parameter parameter){
        parameters.add(parameter);
        parameter.setProduct(this);
    }

    public void addInfo(Info info){
        infos.add(info);
        info.setProduct(this);
    }
}
