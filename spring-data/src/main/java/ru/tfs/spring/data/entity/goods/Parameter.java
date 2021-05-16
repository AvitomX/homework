package ru.tfs.spring.data.entity.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.tfs.spring.data.entity.Views;
import ru.tfs.spring.data.entity.dict.ParameterType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "value"})
@ToString(of = {"id", "value"})
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    @JsonView(Views.Id.class)
    private Long id;

    @NotBlank(message = "Parameter value is mandatory")
    @Column(nullable = false)
    @JsonView(Views.FullParameter.class)
    private String value;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    @JsonView(Views.FullParameter.class)
    private ParameterType type;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonView(Views.Id.class)
    private Product product;

    @Version
    private Integer version;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;
}
