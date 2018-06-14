package gr.iot.iot.util;


import gr.iot.iot.domain.dto.WebSocketDto;

import java.text.ParseException;
import java.util.HashMap;

/**
 * Created by smyrgeorge on 10/14/16.
 */
public class WebsocketUtils {

	private WebsocketUtils() {

	}

	public static WebSocketDto constructWebsocketDto(String status, String payloadType, Object payload) throws ParseException {
		WebSocketDto webSocketDto = new WebSocketDto();
		webSocketDto.setStatus(status);
		webSocketDto.setPayloadType(payloadType);
		webSocketDto.setPayload(payload);
		webSocketDto.setTimestamp(DateUtils.currentTimestampUtc());
		return webSocketDto;
	}

	public static WebSocketDto constructWebsocketDto(String status, String payloadType, Object payload, Object... extras) throws ParseException {
		WebSocketDto webSocketDto = new WebSocketDto();
		webSocketDto.setStatus(status);
		webSocketDto.setPayloadType(payloadType);
		webSocketDto.setPayload(payload);

		HashMap<String, Object> hashMap = new HashMap<>();
		for (Object object : extras) {
			hashMap.put(StringUtils.camelCaseToSnakeCase(object.getClass().getSimpleName()).toLowerCase(), object);
		}
		webSocketDto.setExtras(hashMap);

		webSocketDto.setTimestamp(DateUtils.currentTimestampUtc());
		return webSocketDto;
	}
}
