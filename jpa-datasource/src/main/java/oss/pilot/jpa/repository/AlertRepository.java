package oss.pilot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import oss.pilot.jpa.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
