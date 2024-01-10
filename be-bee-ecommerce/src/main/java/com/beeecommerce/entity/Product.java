package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendors_id")
    private Vendor vendors;

    @Nationalized
    @Column(name = "name")
    private String name;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @Column(name = "view_count")
    private Long viewCount;

    private Boolean isHidden;

    private Long weight;

    private Long height;

    private Long length;

    private Long width;

    @Column(columnDefinition = "nvarchar(4000)")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ClassifyGroup> classifyGroups;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductAttribute> productAttributes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductTag> productTags;

    public List<Attribute> getAllAttributes() {
        return this.getProductAttributes()
                .stream()
                .map(ProductAttribute::getAttributes)
                .toList();
    }

    public List<Hashtag> getAllHashTag() {
        return this.getProductTags()
                .stream()
                .map(ProductTag::getTag)
                .toList();

    }

    public Long getQuantitySold() {
        return this.getClassifyGroups()
                .stream()
                .mapToLong(ClassifyGroup::getQuantitySold)
                .sum();
    }

    public String getPoster() {
        return this.productImages.isEmpty()
                ? "Link ảnh mặc định"
                : this.productImages.get(0).getUrl();
    }

    public List<Classify> getClassifies() {
        return this.getClassifyGroups()
                .stream()
                .flatMap(group -> group.getClassifies().stream())
                .toList();
    }

    public String getClassifyName() {
        return getClassifies().isEmpty()
                ? null
                : getClassifies().get(0).getClassifyName().getName();
    }

    public String getClassifyGroupName() {
        return getClassifyGroups().isEmpty()
                ? null
                : getClassifyGroups().get(0).getClassifyName().getName();
    }

    public List<Feedback> getFeedbacks() {
        return this.getClassifies()
                .stream()
                .flatMap(classify -> classify.getOrderDetails().stream()
                        .flatMap(orderDetail -> orderDetail.getFeedbacks().stream()))
                .toList();
    }
}