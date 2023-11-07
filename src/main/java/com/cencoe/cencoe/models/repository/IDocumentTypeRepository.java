package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocumentTypeRepository extends JpaRepository<DocumentType, Long> {
}
