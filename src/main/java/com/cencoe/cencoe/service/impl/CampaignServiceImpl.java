package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.Campaign;
import com.cencoe.cencoe.models.repository.ICampaignRepository;
import com.cencoe.cencoe.service.ICampaignService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements ICampaignService {

    private final ICampaignRepository campaignRepository;
    @Autowired
    public CampaignServiceImpl(ICampaignRepository campaignRepository) {

        this.campaignRepository = campaignRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse listCampaigns(int page, int size) {
        Page<Campaign> getListCampaign = null;

        try {
            Pageable pageable = PageRequest.of(page, size);
            getListCampaign = campaignRepository.findAll(pageable);
        } catch (DataAccessException dtEx) {

            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListCampaign.isEmpty()) {
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
                    getListCampaign);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse findCampaign(Long campaignId) {
        Optional<Campaign> searchCampaign;
        try {
            searchCampaign = campaignRepository.findById(campaignId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchCampaign.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta Exitosa",
                    true,
                    searchCampaign);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "La campaña que busca no se encuentra el registro en la base de datos",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse saveCampaign(Campaign campaign) {
        Campaign campaignToSave;
        try {
            campaignToSave = campaignRepository.save(campaign);
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
                campaignToSave);
    }

    @Override
    @Transactional
    public MensajeResponse updateCampaign(Campaign campaignUpdate) {
        Optional<Campaign> campaignToUpdate;
        try {
            campaignToUpdate = campaignRepository.findById(campaignUpdate.getCampaignId());

        } catch (DataAccessException dataEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dataEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (campaignToUpdate.isPresent()) {

            Campaign campaignExisting = campaignToUpdate.get();
            campaignExisting.setCampaignId(campaignUpdate.getCampaignId());
            campaignExisting.setCampaignName(campaignUpdate.getCampaignName());
            campaignExisting.setCampaignCapacity(campaignUpdate.getCampaignCapacity());
            campaignExisting.setCampaignStartDate(campaignUpdate.getCampaignStartDate());
            campaignExisting.setCampaignEndDate(campaignUpdate.getCampaignEndDate());
            campaignExisting.setCampaignObservations(campaignUpdate.getCampaignObservations());
            /*campaignExisting.setCampaignTeam(campaignUpdate.getCampaignTeam());
            campaignExisting.setCampaignProvider(campaignUpdate.getCampaignProvider());*/
            campaignExisting.setCampaignState(campaignUpdate.getCampaignState());

            Campaign campaignUpdated = campaignRepository.save(campaignExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Campaña actualizada con éxito",
                    true,
                    campaignUpdated);

        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "La campaña que desea actualizar no existe",
                    true,
                    null);
        }
    }

    @Override
    @Transactional
    public MensajeResponse deleteCampaign(Long campaignId) {
        Optional<Campaign> campaignToDelete = campaignRepository.findById(campaignId);
        try {
            if (campaignToDelete.isPresent()) {
                campaignRepository.deleteById(campaignId);
            } else {
                return MensajeResponse.buildMensajeGeneral(
                        HttpStatus.NOT_FOUND,
                        "La campaña que desea eliminar no existe",
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
                "Campaña eliminada con éxito",
                true,
                null);
    }

}
