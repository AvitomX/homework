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
import ru.tfs.spring.data.entity.dict.Language;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "title"})
@ToString(of = {"id", "title", "description"})
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    @JsonView(Views.Id.class)
    private Long id;

    @NotBlank(message = "Info title is mandatory")
    @Column(nullable = false)
    @JsonView(Views.IdName.class)
    private String title;

    @Column(length = 500)
    @JsonView(Views.FullInfo.class)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonView(Views.Id.class)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    @JsonView(Views.IdName.class)
    private Language language;

    @Version
    private Integer version;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDateTime;
}
