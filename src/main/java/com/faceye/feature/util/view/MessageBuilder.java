package com.faceye.feature.util.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.ui.Model;
//import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;


/**
 * 消息构造器
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年6月19日
 */
public class MessageBuilder {
	private static final String MESSAGE_KEY = "FACEYE_MESSAGE";

	private static class MessageBuilderHolder {
		static MessageBuilder INSTANCE = new MessageBuilder();
	}

	public static MessageBuilder getInstance() {
		return MessageBuilderHolder.INSTANCE;
	}

	/**
	 * 设置消息
	 * @todo
	 * @param model
	 * @param message
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年6月19日
	 */
	public void setMessage(Model model, String message) {
		boolean isContainsKey = model.containsAttribute(MESSAGE_KEY);
		List<ViewMessage> viewMessages = null;
		if (!isContainsKey) {
			viewMessages = new ArrayList<ViewMessage>(0);
//			if (model instanceof RedirectAttributesModelMap) {
//				((RedirectAttributesModelMap) model).addFlashAttribute(MESSAGE_KEY, viewMessages);
//			} else {
//				model.addAttribute(MESSAGE_KEY, viewMessages);
//			}
			model.addAttribute(MESSAGE_KEY, viewMessages);
		} else {
			viewMessages = (List<ViewMessage>) model.asMap().get(MESSAGE_KEY);
		}
		if (viewMessages == null) {
			viewMessages = new ArrayList<ViewMessage>(0);
//			if (model instanceof RedirectAttributesModelMap) {
//				((RedirectAttributesModelMap) model).addFlashAttribute(MESSAGE_KEY, viewMessages);
//			} else {
//				model.addAttribute(MESSAGE_KEY, viewMessages);
//			}
			model.addAttribute(MESSAGE_KEY, viewMessages);
		}
		ViewMessage viewMessage = new ViewMessage();
		viewMessage.setMessage(message);
		viewMessages.add(viewMessage);
	}

	
	public String getMessages(Model model){
		String messages="";
		if(model.containsAttribute(MESSAGE_KEY)){
			List<ViewMessage> viewMessages = (List<ViewMessage>) model.asMap().get(MESSAGE_KEY);
			if(CollectionUtils.isNotEmpty(viewMessages)){
                for(ViewMessage viewMessage:viewMessages){
                	messages+=viewMessage.getMessage();
                	messages+="<br>";
                }
			}
		}
		return messages;
	}
	
}
