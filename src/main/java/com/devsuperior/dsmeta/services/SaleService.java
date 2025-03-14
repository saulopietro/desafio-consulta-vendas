package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDto;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.projections.ReportProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleReportDto> findAll(String name, String iDate, String fDate, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate startDate = null;
		LocalDate endDate = null;

		if (fDate == null) {
			endDate = today;
		} else {
			endDate = LocalDate.parse(fDate);
		}
		if (iDate == null) {
			startDate = endDate.minusYears(1L);
		} else {
			startDate = LocalDate.parse(iDate);
		}

		Page<ReportProjection> result = repository.search1(name, startDate, endDate, pageable);

		return result.map(x -> new SaleReportDto(
				x.getId(),
				x.getName(),
				x.getDate(),
				x.getAmount()
		));




	}

	@Transactional(readOnly = true)
	public List<SaleSummaryDTO> findSummary(String iDate, String fDate) {

		// Converte as Strings para LocalDate
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate startDate = null;
		LocalDate endDate = null;

		if (fDate == null) {
			// Se fDate for null, usa a data de hoje
			endDate = today;
		} else {
			// Caso contrário, converte a String para LocalDate
			endDate = LocalDate.parse(fDate);
		}

		if (iDate == null) {
			// Se iDate for null, usa a data de 1 ano atrás
			startDate = endDate.minusYears(1L);
		} else {
			// Caso contrário, converte a String para LocalDate
			startDate = LocalDate.parse(iDate);
		}

		List<SummaryProjection> result = repository.search2(startDate, endDate);

		return result.stream().map(x -> new SaleSummaryDTO(
				x.getName(),
				x.getAmount()
		)).collect(Collectors.toList());
	}
}
