package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.Campaign;
import com.cencoe.cencoe.util.MensajeResponse;

public interface ICampaignService {

    MensajeResponse listCampaigns(int page, int size);

    MensajeResponse findCampaign(Long campaignId);

    MensajeResponse saveCampaign(Campaign campaign);

    MensajeResponse updateCampaign(Campaign campaignUpdate);

    MensajeResponse deleteCampaign(Long campaignId);
}
