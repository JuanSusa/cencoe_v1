package com.cencoe.cencoe.service.impl;

import com.cencoe.cencoe.models.entity.DocumentType;
import com.cencoe.cencoe.models.repository.IDocumentTypeRepository;
import com.cencoe.cencoe.service.IDocumentTypeService;
import com.cencoe.cencoe.util.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeServiceImpl implements IDocumentTypeService {

    private final IDocumentTypeRepository documentTypeRepository;

    @Autowired
    public DocumentTypeServiceImpl(IDocumentTypeRepository documentTypeRepository) {

        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeResponse ListDocType() {

        List<DocumentType> getListDocType;
        try {
            getListDocType = documentTypeRepository.findAll();

        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (getListDocType.isEmpty()) {

            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "No hay registros en la base de datos",
                    false,
                    null);

        } else {

            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta exitosa",
                    true,
                    getListDocType);
        }
    }

    @Override
    public MensajeResponse findDocType(Long docTypeId) {

        Optional<DocumentType> searchDoctType;
        try {
            searchDoctType = documentTypeRepository.findById(docTypeId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchDoctType.isPresent()) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Consulta exitosa",
                    true,
                    searchDoctType);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El registro" + searchDoctType + "que desea encontrar no se encuentra en la base de datos",
                    false,
                    null);
        }
    }

    @Override
    public MensajeResponse saveDocType(DocumentType documentType) {
        DocumentType docTypeTosave;
        try {
            docTypeTosave = documentTypeRepository.save(documentType);
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
                docTypeTosave);
    }

    @Override
    public MensajeResponse updateDocType(DocumentType docTypeToUpdate) {
        Optional<DocumentType> searchDocTypeToUpdate;
        try {
            searchDocTypeToUpdate = documentTypeRepository.findById(docTypeToUpdate.getDocTypeId());
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchDocTypeToUpdate.isPresent()) {
            DocumentType docTypeExisting = searchDocTypeToUpdate.get();
            docTypeExisting.setDocTypeId(docTypeToUpdate.getDocTypeId());
            docTypeExisting.setDocTypeName(docTypeToUpdate.getDocTypeName());

            DocumentType docTypeUpdated = documentTypeRepository.save(docTypeExisting);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.OK,
                    "Registro actualizado con éxito",
                    true,
                    docTypeUpdated);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El tipo de documnento que desea actualizar no se encuentra en la base de datos",
                    false,
                    null);
        }
    }

    @Override
    public MensajeResponse deleteDocType(Long docTypeId) {
        Optional<DocumentType> searchDocTypeToDelete;
        try {
            searchDocTypeToDelete = documentTypeRepository.findById(docTypeId);
        } catch (DataAccessException dtEx) {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.BAD_REQUEST,
                    dtEx.getMostSpecificCause().getMessage(),
                    false,
                    null);
        }
        if (searchDocTypeToDelete.isPresent()) {
            documentTypeRepository.deleteById(docTypeId);
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NO_CONTENT,
                    "Tipo de documento eliminado con éxito",
                    true,
                    null);
        } else {
            return MensajeResponse.buildMensajeGeneral(
                    HttpStatus.NOT_FOUND,
                    "El tipo de documento con id:" + docTypeId + " que desea eliminar no se encuentra en la base de datos",
                    false,
                    null);
        }
    }
}
