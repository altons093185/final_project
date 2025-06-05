package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.PriceSnapshot;

public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Integer> {

	// Custom query methods can be defined here if needed
	// For example, to find snapshots by product ID or date range

}
