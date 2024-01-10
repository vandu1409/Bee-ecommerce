package com.beeecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "static_page")
public class StaticPage {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", unique = true)
    private String path;

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "title", columnDefinition = "NVARCHAR(500)")
    private String title;
}
