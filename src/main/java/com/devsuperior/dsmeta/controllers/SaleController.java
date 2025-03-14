package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDto;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDto>> getReport(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "minDate", required = false) String iDate,
			@RequestParam(name = "maxDate", required = false) String fDate,
			Pageable pageable) {


		Page<SaleReportDto> dto = service.findAll(name, iDate, fDate, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
			@RequestParam(name = "minDate", required = false) String minDate,
			@RequestParam(name = "maxDate", required = false) String maxDate,
			Pageable pageable
	) {
		List<SaleSummaryDTO> dto = service.findSummary(minDate, maxDate);
		return ResponseEntity.ok(dto);
	}
}
