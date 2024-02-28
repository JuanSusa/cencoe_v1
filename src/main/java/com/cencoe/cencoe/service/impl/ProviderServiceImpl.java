package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.Provider;
import com.cencoe.cencoe.models.repository.IProviderRepository;
import com.cencoe.cencoe.service.IProviderService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements IProviderService {

    private final IProviderRepository providerRepository;

    public ProviderServiceImpl(IProviderRepository providerRepository) {

        this.providerRepository = providerRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Provider> listProvidersPageable(Pageable pageable) {
        return providerRepository.findAll(pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listProvider() {
        List<Provider> getListProviders;

        try {
            getListProviders = providerRepository.findAll();
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListProviders.isEmpty()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "No Hay registros en la base de datos",
                    true,
                    null);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta exitosa",
                    true,
                    getListProviders);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse findProvider(Long providerId) {
        Optional<Provider> searchProvider;
        try {
            searchProvider = providerRepository.findById(providerId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchProvider.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta Exitosa",
                    true,
                    searchProvider);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El proveedor que busca no se encuentra el registro en la base de datos",
                    true,
                    null);
        }
    }
    @Override
    @Transactional
    public MensajeResponse saveProvider(Provider provider) {
        Provider providerToSave;
        try {
            providerToSave = providerRepository.save(provider);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.CREATED,
                "Registro creado con éxito",
                true,
                providerToSave);
    }

    @Override
    @Transactional
    public MensajeResponse updateProvider(Provider providerUpdate) {
        Optional<Provider> providerToUpdate;
        try {
            providerToUpdate = providerRepository.findById(providerUpdate.getProviderId());

        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (providerToUpdate.isPresent()) {

            Provider providerExisting = providerToUpdate.get();
            providerExisting.setProviderId(providerUpdate.getProviderId());
            providerExisting.setProviderName(providerUpdate.getProviderName());
            providerExisting.setProviderAddress(providerUpdate.getProviderAddress());
            providerExisting.setProviderDocType(providerUpdate.getProviderDocType());
           // providerExisting.setProviderState(providerUpdate.getProviderState());

            Provider providerUpdated = providerRepository.save(providerExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Proveedor actualizado con éxito",
                    true,
                    providerUpdated);

        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El proveedor que desea actualizar no existe",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse deleteProvider(Long providerId) {
        Optional<Provider> providerToDelete = providerRepository.findById(providerId);
        try {
            if (providerToDelete.isPresent()) {
                providerRepository.deleteById(providerId);
            } else {
                return MensajeResponse.buildMensajeGeneral(
                        HttpStatus.NOT_FOUND,
                        "El proveedor que desea eliminar no existe",
                        true,
                        null);
            }
        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        return MensajeResponse.buildMensajeGeneral(
                HttpStatus.NO_CONTENT,
                "Proveedor eliminado con éxito",
                true,
                null);
    }


}
