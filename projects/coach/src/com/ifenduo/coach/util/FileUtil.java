package com.ifenduo.coach.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
	public void String2File(String content, String url) {

	};
	public static String File2String(File file) throws IOException {
		InputStream is = null;
		String result = null;
		try {
			is = new FileInputStream(file);
			result = readTextFromInputStream(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return result;
	}
	public static void string2File(String data, File file) throws IOException {
		DataOutputStream ds = null;
		try {
			ds = new DataOutputStream(new FileOutputStream(file));
			ds.write(data.getBytes());
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}
	public static String readTextFromInputStream(InputStream is) throws IOException {
		BufferedReader reader;
		String line;
		StringBuilder builder = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return builder.toString();
	}

}
