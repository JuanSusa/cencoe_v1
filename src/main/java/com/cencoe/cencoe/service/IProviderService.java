package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Provider;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProviderService {

    MensajeResponse listProvider();

    MensajeResponse findProvider(Long providerId);

    MensajeResponse saveProvider(Provider provider);

    MensajeResponse updateProvider(Provider providerUpdate);

    MensajeResponse deleteProvider(Long providerId);

    Page<Provider> listProvidersPageable(Pageable pageable);
}
