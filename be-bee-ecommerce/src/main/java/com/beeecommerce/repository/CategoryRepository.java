package com.beeecommerce.repository;

import com.beeecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "EXEC sp_getParentByChildrenId @childrenId = :childrenId", nativeQuery = true)
    List<Category> getParentCategories(@Param("childrenId") Long childrenId);


    @Query(value = """
            SELECT top ?1 c.id, c.name FROM Category c
            JOIN Product p ON c.id = p.category_id
            JOIN classtify_group cg ON p.id = cg.product_id
            JOIN Classify cl ON cg.id = cl.group_id
            JOIN order_details od ON cl.id = od.classify_id
            JOIN [orders] o ON od.order_id = o.id
            GROUP BY c.id,c.name
            ORDER BY Sum(od.quantity) DESC
            """, nativeQuery = true)
    List<Category> getTopCategory(int top);

    List<Category> getCategoryByLevel(Integer level);

    List<Category> findByParentId(Long id);

    @Query(value = """
                        
            select c.id as id, (count(child.id)) as childrenCount
             from Category c
             inner join Category child on c.id = child.parentId
             where c.id in :categoryId
             group by c.id
             """)
    List<Map<String, Long>> fillHasChildren(List<Long> categoryId);

    @Query(value = "SELECT c.* FROM category c WHERE c.id IN (SELECT c.parent_id FROM category c JOIN product p ON c.id = p.category_id WHERE c.id = p.category_id)", nativeQuery = true)
    List<Category> findCategoriesWithParentIdInProduct();

    @Query("SELECT c FROM Category c JOIN c.products p WHERE c.parentId = :parentId")
    List<Category> findCategoriesWithParentId(@Param("parentId") Long parentId);
}