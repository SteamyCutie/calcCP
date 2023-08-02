package com.ccp;

import static org.junit.Assert.*;

import com.ccp.dto.CPReqDtoAll;
import com.ccp.dto.CPReqDtoBetween;
import com.ccp.dto.CPReqDtoMonth;
import com.ccp.dto.RequestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TransactionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void getCPBetweenTest() throws Exception
    {
        String fromString = "2023-08-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(fromString, formatter);

        String toString = "2023-08-02";
        LocalDate toDate = LocalDate.parse(toString, formatter);

        HttpEntity<CPReqDtoBetween> request = new HttpEntity<>(CPReqDtoBetween.builder().customerId(1L).from(Date.valueOf(fromDate)).to(Date.valueOf(toDate)).build());
        ResponseEntity<RequestResponse> responseEntity = restTemplate.postForEntity("/api/transaction/getCPBetween", request, RequestResponse.class);
        System.out.println(responseEntity.getBody().getResult());

        Assertions.assertEquals(50, responseEntity.getBody().getResult());
    }

    @Test
    public void getCPAllTest() throws Exception
    {

        HttpEntity<CPReqDtoAll> request = new HttpEntity<>(CPReqDtoAll.builder().customerId(3L).build());
        ResponseEntity<RequestResponse> responseEntity = restTemplate.postForEntity("/api/transaction/getCPAll", request, RequestResponse.class);
        System.out.println(responseEntity.getBody().getResult());

        Assertions.assertEquals(900, responseEntity.getBody().getResult());
    }

    @Test
    public void getCPMonthTest() throws Exception
    {

        String dateString = "2023-08-02";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        HttpEntity<CPReqDtoMonth> request = new HttpEntity<>(CPReqDtoMonth.builder().customerId(3L).date(Date.valueOf(date)).build());
        ResponseEntity<RequestResponse> responseEntity = restTemplate.postForEntity("/api/transaction/getCPMonth", request, RequestResponse.class);
        System.out.println(responseEntity.getBody().getResult());

        Assertions.assertEquals(900, responseEntity.getBody().getResult());
    }

}
