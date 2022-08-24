package com.evo.qualitanceProject.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "reviews")
public class Review extends BaseEntity<Long> {
    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String comment;

    @Column(nullable = true)
    private Integer rating;

    @Column(nullable = false)
    private LocalDate dateCreated;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Review(String title, String comment, Integer rating, Product product) {
        this.title = title;
        this.comment = comment;
        this.rating = rating;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Review review = (Review) o;
        return Objects.equals(title, review.title) && Objects.equals(comment, review.comment) && Objects.equals(rating, review.rating) && Objects.equals(dateCreated, review.dateCreated) && Objects.equals(product, review.product) && Objects.equals(user, review.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, comment, rating, dateCreated, product, user);
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", dateCreated=" + dateCreated +
                ", product=" + product +
                ", user=" + user +
                '}';
    }
}
