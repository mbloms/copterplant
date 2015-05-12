package se.mad.copterplant.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileUtil {
	public static String readFile(String path){
		FileHandle file = Gdx.files.local(path);
		return file.readString();
	}

}
