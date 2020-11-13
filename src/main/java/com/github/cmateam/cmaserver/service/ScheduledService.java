package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.cmateam.cmaserver.configuration.CmaConfig;
import com.github.cmateam.cmaserver.entity.CountIdEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.repository.CountIdRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {

    private Logger logger = LoggerFactory.getLogger(ScheduledService.class);
    private CmaConfig cmaConfig;
    private CountIdRepository countIdRepository;
    private RoomServiceRepository roomServiceRepository;

    @Autowired
    public ScheduledService(CmaConfig cmaConfig, CountIdRepository countIdRepository,
            RoomServiceRepository roomServiceRepository) {
        this.cmaConfig = cmaConfig;
        this.countIdRepository = countIdRepository;
        this.roomServiceRepository = roomServiceRepository;
    }

    // Send public ip to gateway server
    // At second :00, at minutes :00, :10, :20, :30, :40 and :50, of every hour
    @Scheduled(cron = "0 0,10,20,30,40,50 * ? * *")
    public void sendIpPublic() {
        if (cmaConfig.getGatewayServerUrl() != null) {
            try {
                final CloseableHttpClient httpClient = HttpClients.createDefault();

                String ip = null;
                HttpGet request = new HttpGet("https://api.myip.com/");
                request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
                try (CloseableHttpResponse response = httpClient.execute(request)) {
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        String result = EntityUtils.toString(entity);
                        JSONObject obj = new JSONObject(result);
                        ip = obj.getString("ip");
                        logger.info(ip);
                    }
                }

                HttpPost post = new HttpPost(cmaConfig.getGatewayServerUrl());
                List<NameValuePair> urlParameters = new ArrayList<>();
                urlParameters.add(new BasicNameValuePair("ip", ip));
                post.setEntity(new UrlEncodedFormEntity(urlParameters));
                httpClient.execute(post);
            } catch (Exception e) {
                logger.error("Send Ip Public error: {}", e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void resetCounting() {
        System.out.println("Run cronjob");
        List<CountIdEntity> listCountId = countIdRepository.findAll();
        for (CountIdEntity e : listCountId) {
            e.setCountValue(0);
            e.setUpdatedAt(new Date());
            countIdRepository.save(e);
        }

        List<RoomServiceEntity> listRoomService = roomServiceRepository.findAll();
        for (RoomServiceEntity e : listRoomService) {
            e.setTotalDone((short) 0);
            e.setTotalReceive((short) 0);
            e.setUpdatedAt(new Date());
            roomServiceRepository.save(e);
        }
    }
}
