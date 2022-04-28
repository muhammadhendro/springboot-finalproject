package com.alterra.finalproject.domain.dao;

import com.alterra.finalproject.domain.common.BaseEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "M_BOOK")
@SQLDelete(sql = "UPDATE M_BOOK SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class BookDao extends BaseEntity {



    private static final long serialVersionUID = -1266576651734156259L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;



    @Column(name = "description", nullable = false)
    private String description;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorDao author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryDao category;

    @Column(name = "publish_date", nullable = false)
    private String publishDate;

    @Column(name = "price", nullable = false)
    private Integer price;








}
