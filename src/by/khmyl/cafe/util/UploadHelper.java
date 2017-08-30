package by.khmyl.cafe.util;

import java.io.File;
import java.util.Random;

import javax.servlet.http.Part;

import by.khmyl.cafe.constant.Constant;


public class UploadHelper {

	private String savePath;

	/**
	 * Instantiates a new upload helper.
	 *
	 * @param savePath path for saving uploaded files
	 */
	public UploadHelper(String savePath) {
		this.savePath=savePath;
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
	}

	/**
	 * Extract file name from request part.
	 *
	 * @param part the part
	 * @return filename if request contains it, {@code null} otherwise
	 * 
	 * @see Part
	 */ 
 	public String extractFileName(Part part) {
		String contentDisp = part.getHeader(Constant.CONTENT_DISPOSITION);
		for (String content : contentDisp.split(";")) {
			if (content.trim().startsWith(Constant.FILENAME)) {
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
		}
		return null;
	}

	/**
	 * Check image name and there is the same generate new.
	 *
	 * @param imageName image name that need to upload
	 * @return new image name if was found the same, the original otherwise
	 */
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

	/**
	 * Gets the save path.
	 *
	 * @return the save path
	 */
	public String getSavePath() {
		return savePath;
	}

	/**
	 * Sets the save path.
	 *
	 * @param savePath the new save path
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
