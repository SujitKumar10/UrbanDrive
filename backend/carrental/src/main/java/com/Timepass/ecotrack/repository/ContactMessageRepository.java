package com.Timepass.ecotrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Timepass.ecotrack.entities.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}

