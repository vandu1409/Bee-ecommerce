package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.beeecommerce.constance.ConstraintName.FEEDBACK_ORDER_DETAILS_ID_UNIQUE;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "feedback", uniqueConstraints = {
                @UniqueConstraint(name = FEEDBACK_ORDER_DETAILS_ID_UNIQUE, columnNames = { "order_details_id" })
})
public class Feedback {
        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_details_id")
        private OrderDetail orderDetails;

        @Nationalized
        @Column(name = "comment", length = 4000)
        private String comment;

        @Column(name = "create_at")
        private LocalDate createAt;

        @Column(name = "rating")
        private Integer rating;

        @OneToMany(mappedBy = "feedback")
        private List<FeedbackImage> feedbackImages = new ArrayList<>();

        @Transient
        public List<String> getAllImageFeedback() {
                return this.getFeedbackImages().stream()
                                .map(FeedbackImage::getUrl)
                                .toList();
        }

        
}