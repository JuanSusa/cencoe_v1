package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Campaign;
import com.cencoe.cencoe.util.MensajeResponse;

public interface ICampaignService {

    MensajeResponse listCampaigns();

    MensajeResponse findCampaign(Long campaignId);

    MensajeResponse saveCampaign(Campaign campaign);

    MensajeResponse updateCampaign(Campaign campaignUpdate);

    MensajeResponse deleteCampaign(Long campaignId);
}
