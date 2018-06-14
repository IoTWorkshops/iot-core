package gr.iot.iot.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by smyrgeorge on 10/14/16.
 */
public class WebSocketDto implements Serializable {

	@JsonProperty("status")
	private String status;

	@JsonProperty("payload_type")
	private String payloadType;

	@JsonProperty("payload")
	private Object payload;

	@JsonProperty("extras")
	private HashMap<String, Object> extras;

	@JsonProperty("timestamp")
	private Long timestamp;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public HashMap<String, Object> getExtras() {
		return extras;
	}

	public void setExtras(HashMap<String, Object> extras) {
		this.extras = extras;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "WebSocketDto{" +
			"status='" + status + '\'' +
			", payloadType='" + payloadType + '\'' +
			", payload=" + payload +
			", extras=" + extras +
			", timestamp=" + timestamp +
			'}';
	}
}
