package com.trade.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TradeProcessor {
/**
* Handles incoming FIX messages.
* **
* FIX message object.
* 17 - execId
* 48 - securityId
* 22 - securityIdSource
* 1 - account
* 32 - lastShares (positive int)
* 6 - avgPx (positive double)
* 8 - side (BUY, SELL)
*/
	@Autowired
	KafkaTemplate<String,String> kafkaTrade;
	
	@Value("${kafkaTopic}")
	private String kafkatopic;
public void onMessage(Message msg) {
// Write code here
	try {
	Map<String,String> tradeMap =new HashMap<>();
	tradeMap.put("execId", "17");
	tradeMap.put("securityId", "48");
	tradeMap.put("securityIdSource", "22");
	tradeMap.put("account", "1");
	tradeMap.put("lastShares", "32");
	tradeMap.put("avgPx", "6");
	tradeMap.put("side", "8");
	ObjectMapper obj =new ObjectMapper();
	String message =obj.writeValueAsString(tradeMap);
	kafkaTrade.send(kafkatopic,message);
	/*kafkaTrade.sendDefault(kafkatopic,message);
	/*Need kafka server details,host and port in application.properties and kafa topic name*/
	
	}catch(Exception e) {
		System.out.println("Error "+e);
		System.exit(0);//to  stop localhost
	}
}
}