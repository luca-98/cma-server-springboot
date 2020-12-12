package com.github.cmateam.cmaserver.service;

import com.github.cmateam.cmaserver.dto.ReceivePatientDTO;
import com.github.cmateam.cmaserver.dto.RoomServiceDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private static final String SUBSCRIBE_TOPIC_UPDATE_RECEVIE = "/topic/receive-patient";
    private static final String SUBSCRIBE_TOPIC_UPDATE_ROOM_SERVICE = "/topic/room-service";
    private static final String SUBSCRIBE_TOPIC_UPDATE_ORDINAL_NUMBER = "/topic/ordinal-number";
    private static final String SUBSCRIBE_TOPIC_UPDATE_PERMISSION = "/topic/permission";

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void updateReceivePatient(ReceivePatientDTO receivePatientDTO) {
        simpMessagingTemplate.convertAndSend(SUBSCRIBE_TOPIC_UPDATE_RECEVIE, receivePatientDTO);
    }

    public void updateRoomService(RoomServiceDTO roomServiceDTO) {
        simpMessagingTemplate.convertAndSend(SUBSCRIBE_TOPIC_UPDATE_ROOM_SERVICE, roomServiceDTO);
    }

    public void updateOrdinalNumber(ReceivePatientDTO receivePatientDTO) {
        simpMessagingTemplate.convertAndSend(SUBSCRIBE_TOPIC_UPDATE_ORDINAL_NUMBER, receivePatientDTO);
    }

    public void updatePermission() {
        simpMessagingTemplate.convertAndSend(SUBSCRIBE_TOPIC_UPDATE_PERMISSION, true);
    }
}
