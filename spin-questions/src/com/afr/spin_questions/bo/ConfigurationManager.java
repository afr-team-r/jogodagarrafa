package com.afr.spin_questions.bo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import android.os.Environment;
import com.afr.spin_questions.beans.ConfigurationBean;

public class ConfigurationManager {

	private static ConfigurationBean instance = null;
	private static boolean defaultConfiguration = true;

	public static void init(InputStream is) {
		if (ConfigurationManager.instance == null) {

			ObjectMapper mapper = new ObjectMapper();

			try {
				instance = mapper.readValue(is, ConfigurationBean.class);
				is.close();
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean persistConfigurations() {

		if (ConfigurationManager.instance != null) {

			File sdFolder = Environment
					.getExternalStoragePublicDirectory(ConfigurationBean.SD_LOCATION);

			if (!sdFolder.exists()) {
				sdFolder.mkdirs();
			}

			File sdFile = new File(sdFolder,
					ConfigurationBean.DEFAULT_CONFIGURATION);

			ObjectMapper mapper = new ObjectMapper();

			try {
				mapper.writeValue(sdFile, ConfigurationManager.instance);
				return true;

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	public static ConfigurationBean getInstance() {
		return ConfigurationManager.instance;
	}

	public static boolean isDefaultConfiguration() {
		return defaultConfiguration;
	}

	public static void setDefaultConfiguration(boolean defaultConfiguration) {
		ConfigurationManager.defaultConfiguration = defaultConfiguration;
	}

}
