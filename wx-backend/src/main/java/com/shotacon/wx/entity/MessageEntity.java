package com.shotacon.wx.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XStreamAlias("xml")
public class MessageEntity {

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
	 * 消息id
	 */
	@XStreamAlias("MsgId")
	private Long msgId;
}
