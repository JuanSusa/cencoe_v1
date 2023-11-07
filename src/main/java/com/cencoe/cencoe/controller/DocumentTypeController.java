package com.cencoe.cencoe.controller;

import com.cencoe.cencoe.models.entity.DocumentType;
import com.cencoe.cencoe.service.IDocumentTypeService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/cencoe")
public class DocumentTypeController {

    private final IDocumentTypeService documentTypeService;

    @Autowired
    public  DocumentTypeController(IDocumentTypeService documentTypeService) {

        this.documentTypeService = documentTypeService;
    }

    @GetMapping("/tipos-documento")
    public ResponseEntity<Object> listDocTypes(){
        MensajeResponse responseListDocType = documentTypeService.ListDocType();
        return new ResponseEntity<>(responseListDocType, HttpStatus.OK);
    }

    @GetMapping("/tipo-documento/{docTypeId}")
    public ResponseEntity<Object> showDocTypeById(@PathVariable Long docTypeId){
        MensajeResponse responseFindDoctypeById = documentTypeService.findDocType(docTypeId);
        return new ResponseEntity<>(responseFindDoctypeById, HttpStatus.OK);
    }

    @PostMapping("/tipo-documento")
    public ResponseEntity<Object> saveDocType(@RequestBody DocumentType documentTypeToSave){
        MensajeResponse responseSaveDocType = documentTypeService.saveDocType(documentTypeToSave);
        return new ResponseEntity<>(responseSaveDocType, HttpStatus.OK);
    }

    @PutMapping("/tipo-documento/{docTypeId}")
    public ResponseEntity<Object> UpdateDocType(@PathVariable Long docTypeId,
                                                @RequestBody DocumentType documentTypeToUpdate){
        MensajeResponse sarchDocTypeToUpdate = documentTypeService.findDocType(docTypeId);
        if (sarchDocTypeToUpdate != null){

            MensajeResponse responseDocTypeToUpdate = documentTypeService.updateDocType(documentTypeToUpdate);
            return new ResponseEntity<>(responseDocTypeToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/tipo-documento/{docTypeId}")
    public ResponseEntity<Object> DeleteDocType(@PathVariable Long docTypeId){
        MensajeResponse searchDocTypeToDelete = documentTypeService.findDocType(docTypeId);
        if (searchDocTypeToDelete != null){
            MensajeResponse responseDocTypeToDelete = documentTypeService.deleteDocType(docTypeId);
            return new ResponseEntity<>(responseDocTypeToDelete, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
