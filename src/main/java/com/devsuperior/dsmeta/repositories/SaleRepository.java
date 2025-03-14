package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.projections.ReportProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = """
            SELECT tb_sales.id, tb_sales.amount, tb_sales.date, tb_seller.name
            FROM tb_sales
            INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id
            WHERE (:name = '' OR UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%')))
            AND tb_sales.date <= :fDate AND tb_sales.date >= :iDate
        """,
                countQuery = "SELECT COUNT(*) FROM tb_sales JOIN tb_seller")
        Page<ReportProjection> search1(@Param("name") String name,
                                   @Param("iDate") LocalDate iDate,
                                   @Param("fDate") LocalDate fDate,
                                   Pageable pageable);


    @Query(nativeQuery = true, value = """
             SELECT SUM(tb_sales.amount) AS amount, tb_seller.name AS name
             FROM tb_sales
             INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id
             WHERE tb_sales.date >= :iDate AND tb_sales.date <= :fDate
             GROUP BY tb_seller.name
        """)
    List<SummaryProjection> search2(@Param("iDate") LocalDate minDate,
                                    @Param("fDate") LocalDate maxDate
                                    );
}
