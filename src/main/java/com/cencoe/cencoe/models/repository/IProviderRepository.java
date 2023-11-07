package com.cencoe.cencoe.models.repository;

import com.cencoe.cencoe.models.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProviderRepository extends JpaRepository<Provider, Long> {
}
