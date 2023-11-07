package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.Provider;
import com.cencoe.cencoe.service.IProviderService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/cencoe")
public class ProviderController {

    private final IProviderService providerService;

    public ProviderController(IProviderService providerService){

        this.providerService = providerService;
    }

    @GetMapping("/proveedores")
    public ResponseEntity<Object> listProviders() {

        MensajeResponse responseListProviders = providerService.listProvider();
        return new ResponseEntity<>(responseListProviders, HttpStatus.OK);
    }

    @GetMapping("proveedor/{id}")
    public ResponseEntity<Object> findProviderById(@PathVariable Long id) {

        MensajeResponse responseFindProvider = providerService.findProvider(id);
        return new ResponseEntity<>(responseFindProvider, HttpStatus.OK);
    }

    @PostMapping("/proveedor")
    public ResponseEntity<Object> saveProvider(@RequestBody Provider provider) {

        MensajeResponse responseSaveProvider = providerService.saveProvider(provider);
        return new ResponseEntity<>(responseSaveProvider, HttpStatus.OK);
    }

    @PutMapping("/proveedor/{id}")
    public ResponseEntity<Object> updateProvider(@RequestBody Provider providerToUpdate, @PathVariable Long id) {

        MensajeResponse findProviderToUpdate = providerService.findProvider(id);
        if (findProviderToUpdate != null) {

            MensajeResponse responseProviderToUpdate = providerService.updateProvider(providerToUpdate);
            return new ResponseEntity<>(responseProviderToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/proveedor/{id}")
    public ResponseEntity<Object> deleteProvider(@PathVariable Long id) {

        MensajeResponse responseProviderToDelete = providerService.deleteProvider(id);
        return new ResponseEntity<>(responseProviderToDelete, HttpStatus.OK);
    }
}
