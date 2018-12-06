package com.shotacon.wx.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XStreamAlias("xml")
public class MessageEntity implements Cloneable {

	public enum MessageType {
		TEXT, IMAGE, VOICE, VIDEO, SHORTVIDEO, LOCATION, LINK, event
	}

	/**
	 * 开发者微信号
	 */
	@XStreamAlias("ToUserName")
	private String toUserName;

	/**
	 * 发送方帐号（一个OpenID）
	 */
	@XStreamAlias("FromUserName")
	private String fromUserName;

	/**
	 * 消息创建时间（整型）
	 */
	@XStreamAlias("CreateTime")
	private Long createTime;

	/**
	 * 消息类型
	 */
	@XStreamAlias("MsgType")
	private String msgType;

	/**
	 * 文本消息内容
	 */
	@XStreamAlias("Content")
	private String content;

	/**
	 * 图片链接（由系统生成）
	 */
	@XStreamAlias("PicUrl")
	private String picUrl;

	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	@XStreamAlias("MediaId")
	private String mediaId;
	/**
	 * 语音格式，如amr，speex等
	 */
	@XStreamAlias("Format")
	private String format;

	/**
	 * 语音识别结果，UTF8编码
	 */
	@XStreamAlias("Recognition")
	private String recognition;

	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	@XStreamAlias("ThumbMediaId")
	private String thumbMediaId;

	/**
	 * 地理位置维度
	 */
	@XStreamAlias("Location_X")
	private String location_X;

	/**
	 * 地理位置经度
	 */
	@XStreamAlias("Location_Y")
	private String location_Y;

	/**
	 * 地图缩放大小
	 */
	@XStreamAlias("Scale")
	private String scale;

	/**
	 * 地理位置信息
	 */
	@XStreamAlias("Label")
	private String label;

	/**
	 * 消息标题
	 */
	@XStreamAlias("Title")
	private String title;

	/**
	 * 消息描述
	 */
	@XStreamAlias("Description")
	private String description;

	/**
	 * 消息链接
	 */
	@XStreamAlias("Url")
	private String url;

	/**
	 * 事件类型, subscribe(订阅)、unsubscribe(取消订阅)
	 */
	@XStreamAlias("Event")
	private String event;

	/**
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 */
	@XStreamAlias("EventKey")
	private String eventKey;

	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	@XStreamAlias("Ticket")
	private String ticket;

	/**
	 * 事件推送的状态
	 */
	@XStreamAlias("Status")
	private String status;

	/**
	 * 消息id
	 */
	@XStreamAlias("MsgId")
	private Long msgId;

	/**
	 * 消息id
	 */
	@XStreamAlias("MsgID")
	private Long msgID;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getLocation_X() {
		return location_X;
	}

	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}

	public String getLocation_Y() {
		return location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
		this.msgID = msgId;
	}

	public Long getMsgID() {
		return msgID;
	}

	public void setMsgID(Long msgID) {
		this.msgID = msgID;
		this.msgId = msgID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
