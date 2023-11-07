package com.cencoe.cencoe.service;

import com.cencoe.cencoe.models.entity.DocumentType;
import com.cencoe.cencoe.util.MensajeResponse;

public interface IDocumentTypeService {

    MensajeResponse ListDocType();

    MensajeResponse findDocType(Long docTypeId);

    MensajeResponse saveDocType(DocumentType DocumentType);

    MensajeResponse updateDocType(DocumentType docTypeToUpdate);

    MensajeResponse deleteDocType (Long docTypeId);
}
