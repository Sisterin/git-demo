package com.system.entity.dto;

import java.util.List;

public class ImageMsgContent {
	
	private String UUID;
	
	private String ImageFormat;
	
	private List<ImageMsgContentDetail> ImageInfoArray;

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getImageFormat() {
		return ImageFormat;
	}

	public void setImageFormat(String imageFormat) {
		ImageFormat = imageFormat;
	}

	public List<ImageMsgContentDetail> getImageInfoArray() {
		return ImageInfoArray;
	}

	public void setImageInfoArray(List<ImageMsgContentDetail> imageInfoArray) {
		ImageInfoArray = imageInfoArray;
	}
	
	

}
