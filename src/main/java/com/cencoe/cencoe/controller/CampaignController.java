package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.Campaign;
import com.cencoe.cencoe.service.ICampaignService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v2/cencoe")
public class CampaignController {

    private final ICampaignService campaignService;

    @Autowired
    public CampaignController(ICampaignService campaignService){

        this.campaignService = campaignService;
    }

    @GetMapping("/campañas")
    public ResponseEntity<Object> listCampaigns() {

        MensajeResponse responseListCampaigns = campaignService.listCampaigns();
        return new ResponseEntity<>(responseListCampaigns, HttpStatus.OK);
    }

    @GetMapping("campaña/{id}")
    public ResponseEntity<Object> findCampaignById(@PathVariable Long id) {

        MensajeResponse responseFindCampaign = campaignService.findCampaign(id);
        return new ResponseEntity<>(responseFindCampaign, HttpStatus.OK);
    }

    @PostMapping("/campaña")
    public ResponseEntity<Object> saveCampaign(@RequestBody Campaign campaign) {

        MensajeResponse responseSaveCampaign = campaignService.saveCampaign(campaign);
        return new ResponseEntity<>(responseSaveCampaign, HttpStatus.OK);
    }

    @PutMapping("/campaña/{id}")
    public ResponseEntity<Object> updateCampaign(@RequestBody Campaign campaignToUpdate, @PathVariable Long id) {

        MensajeResponse findCampaignToUpdate = campaignService.findCampaign(id);
        if (findCampaignToUpdate != null) {

            MensajeResponse responseCampaignToUpdate = campaignService.updateCampaign(campaignToUpdate);
            return new ResponseEntity<>(responseCampaignToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/campaña/{id}")
    public ResponseEntity<Object> deleteCampaign(@PathVariable Long id) {

        MensajeResponse responseCampaignToDelete = campaignService.deleteCampaign(id);
        return new ResponseEntity<>(responseCampaignToDelete, HttpStatus.OK);
    }

}
