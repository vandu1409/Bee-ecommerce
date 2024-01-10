package com.beeecommerce.repository.elastic;

import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument,Long> {

    @Query("""
            {
              "multi_match": {
                "query": "?0",
                "fields": ["name", "tags", "brandname", "categoryname"]
              }
            }
            """)
    Page<ProductDocument> findByKeyword(String keyword, Pageable pageable);



}
