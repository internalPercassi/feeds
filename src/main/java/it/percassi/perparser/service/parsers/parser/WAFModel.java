package it.percassi.perparser.service.parsers.parser;

/**
 *
 * @author Daniele Sperto
 */
public class WAFModel extends BaseModel {

	private String remote_addr;
	private String time_iso8601;
	private String remote_user;
	private String status;
	private String bytes_sent;
	private String method;
	private String request;
	private String proto_version;
	private String http_user_agent;
	private String blocked;
	private String is_human;
	private String block_reason;
	private String geoip_city_country_name;
	private String geoip_city;
	private String geoip_longitude;
	private String geoip_latitude;
	private String request_id;
	private String matched_asnum;
	private String captured_vector;
	private String request_time;
	private String upstream_addr;
	private String upstream_response_time;
	private String domain_name;
	private String host;
	private String referer;
	private String request_headers;
	private String organization;
	private String upstream_status;
	private String uri;
	private String hostname;
	private String is_cloud;
	private String is_tor;
	private String is_vpn;
	private String is_anonymizer;
	private String is_proxy;
	private String rbzsessionid;
	private String request_length;
	private String sent_http_cache_control;
	private String sent_http_expires;
	private String cookie_rbzid;
	private String sent_http_content_type;
	private String anything_else;

	public String getRemote_addr() {
		return remote_addr;
	}

	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}

	public String getTime_iso8601() {
		return time_iso8601;
	}

	public void setTime_iso8601(String time_iso8601) {
		this.time_iso8601 = time_iso8601;
	}

	public String getRemote_user() {
		return remote_user;
	}

	public void setRemote_user(String remote_user) {
		this.remote_user = remote_user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBytes_sent() {
		return bytes_sent;
	}

	public void setBytes_sent(String bytes_sent) {
		this.bytes_sent = bytes_sent;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getProto_version() {
		return proto_version;
	}

	public void setProto_version(String proto_version) {
		this.proto_version = proto_version;
	}

	public String getHttp_user_agent() {
		return http_user_agent;
	}

	public void setHttp_user_agent(String http_user_agent) {
		this.http_user_agent = http_user_agent;
	}

	public String getBlocked() {
		return blocked;
	}

	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}

	public String getIs_human() {
		return is_human;
	}

	public void setIs_human(String is_human) {
		this.is_human = is_human;
	}

	public String getBlock_reason() {
		return block_reason;
	}

	public void setBlock_reason(String block_reason) {
		this.block_reason = block_reason;
	}

	public String getGeoip_city_country_name() {
		return geoip_city_country_name;
	}

	public void setGeoip_city_country_name(String geoip_city_country_name) {
		this.geoip_city_country_name = geoip_city_country_name;
	}

	public String getGeoip_city() {
		return geoip_city;
	}

	public void setGeoip_city(String geoip_city) {
		this.geoip_city = geoip_city;
	}

	public String getGeoip_longitude() {
		return geoip_longitude;
	}

	public void setGeoip_longitude(String geoip_longitude) {
		this.geoip_longitude = geoip_longitude;
	}

	public String getGeoip_latitude() {
		return geoip_latitude;
	}

	public void setGeoip_latitude(String geoip_latitude) {
		this.geoip_latitude = geoip_latitude;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getMatched_asnum() {
		return matched_asnum;
	}

	public void setMatched_asnum(String matched_asnum) {
		this.matched_asnum = matched_asnum;
	}

	public String getCaptured_vector() {
		return captured_vector;
	}

	public void setCaptured_vector(String captured_vector) {
		this.captured_vector = captured_vector;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getUpstream_addr() {
		return upstream_addr;
	}

	public void setUpstream_addr(String upstream_addr) {
		this.upstream_addr = upstream_addr;
	}

	public String getUpstream_response_time() {
		return upstream_response_time;
	}

	public void setUpstream_response_time(String upstream_response_time) {
		this.upstream_response_time = upstream_response_time;
	}

	public String getDomain_name() {
		return domain_name;
	}

	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getRequest_headers() {
		return request_headers;
	}

	public void setRequest_headers(String request_headers) {
		this.request_headers = request_headers;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getUpstream_status() {
		return upstream_status;
	}

	public void setUpstream_status(String upstream_status) {
		this.upstream_status = upstream_status;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIs_cloud() {
		return is_cloud;
	}

	public void setIs_cloud(String is_cloud) {
		this.is_cloud = is_cloud;
	}

	public String getIs_tor() {
		return is_tor;
	}

	public void setIs_tor(String is_tor) {
		this.is_tor = is_tor;
	}

	public String getIs_vpn() {
		return is_vpn;
	}

	public void setIs_vpn(String is_vpn) {
		this.is_vpn = is_vpn;
	}

	public String getIs_anonymizer() {
		return is_anonymizer;
	}

	public void setIs_anonymizer(String is_anonymizer) {
		this.is_anonymizer = is_anonymizer;
	}

	public String getIs_proxy() {
		return is_proxy;
	}

	public void setIs_proxy(String is_proxy) {
		this.is_proxy = is_proxy;
	}

	public String getRbzsessionid() {
		return rbzsessionid;
	}

	public void setRbzsessionid(String rbzsessionid) {
		this.rbzsessionid = rbzsessionid;
	}

	public String getRequest_length() {
		return request_length;
	}

	public void setRequest_length(String request_length) {
		this.request_length = request_length;
	}

	public String getSent_http_cache_control() {
		return sent_http_cache_control;
	}

	public void setSent_http_cache_control(String sent_http_cache_control) {
		this.sent_http_cache_control = sent_http_cache_control;
	}

	public String getSent_http_expires() {
		return sent_http_expires;
	}

	public void setSent_http_expires(String sent_http_expires) {
		this.sent_http_expires = sent_http_expires;
	}

	public String getCookie_rbzid() {
		return cookie_rbzid;
	}

	public void setCookie_rbzid(String cookie_rbzid) {
		this.cookie_rbzid = cookie_rbzid;
	}

	public String getSent_http_content_type() {
		return sent_http_content_type;
	}

	public void setSent_http_content_type(String sent_http_content_type) {
		this.sent_http_content_type = sent_http_content_type;
	}

	public String getAnything_else() {
		return anything_else;
	}

	public void setAnything_else(String anything_else) {
		this.anything_else = anything_else;
	}
	
	
}
