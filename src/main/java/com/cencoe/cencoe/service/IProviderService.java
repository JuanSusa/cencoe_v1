package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Provider;
import com.cencoe.cencoe.util.MensajeResponse;

public interface IProviderService {

    MensajeResponse listProvider();

    MensajeResponse findProvider(Long providerId);

    MensajeResponse saveProvider(Provider provider);

    MensajeResponse updateProvider(Provider providerUpdate);

    MensajeResponse deleteProvider(Long providerId);
}