package by.khmyl.cafe.util;

import java.io.File;
import java.util.Random;

import javax.servlet.http.Part;

public class UploadHelper {

	private String savePath;

	public UploadHelper(String savePath) {
		this.setSavePath(savePath);
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
	}

	public String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		for (String content : contentDisp.split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
		}
		return null;
	}

	public String checkImageName(String imageName) {
		File file = new File(savePath + File.separator + imageName);
		if (file.exists()) {
			int dot = imageName.indexOf('.');
			String name = imageName.substring(0, dot);
			String format = imageName.substring(dot);
			name += "_" + new Random().nextInt(9);
			imageName = name + format;

		}
		return imageName;

	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
