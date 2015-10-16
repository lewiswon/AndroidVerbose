package com.lewis.downloaddemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class InstructDAO {
	SQLiteDatabase database = null;
	private String listJson = "", objectJsonString = "";
	private DatabaseInterface listener;

	public InstructDAO() {
		database = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory() + "/downloaddemo/instruct.db3", null, SQLiteDatabase.OPEN_READONLY);
	}

	public void setListener(DatabaseInterface mListener) {
		this.listener = mListener;
	}

	public void query(int page, String... params) {
		listJson = "";
		String where = "";
		String[] values = null;
		page -= 1;
		switch (params.length) {
		case 1:
			where = "me_name LIKE ?";
			values = new String[] { "%" + params[0] + "%" };
			break;
		case 2:
			where = "me_name LIKE ? and me_zhuzhi LIKE ?";
			values = new String[] { "%" + params[0] + "%", "%" + params[1] + "%" };
			break;
		case 3:
			where = "me_name LIKE ? and me_zhuzhi LIKE ? and me_jingji LIKE ?";
			values = new String[] { "%" + params[0] + "%", "%" + params[1] + "%", "%" + params[2] + "%" };
			break;
		case 4:
			values = new String[] { "%" + params[0] + "%", "%" + params[1] + "%", "%" + params[2] + "%", "%" + params[3] + "%" };
			where = "me_name LIKE ? and me_zhuzhi LIKE ? and me_jingji LIKE ? and me_xianghuzhuoyong LIKE ?";
			break;
		default:
			break;
		}
		long l = System.currentTimeMillis();
		//		Cursor cursor = database.query("be_app_instruct", new String[] { "me_name" }, "me_name LIKE ? ", new String[] { "%" + params[0] + "%" }, null, null, null, "LIMIT 20 OFFSET 0");
		Cursor cursor = database.rawQuery("SELECT me_name,me_source,me_uid FROM be_app_instruct WHERE " + where + " LIMIT 20 OFFSET " + page * 20, values);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				String cellString = "{";
				Log.i("name", cursor.getString(0) + "  " + i + " in " + cursor.getCount() + "==" + cursor.getColumnCount());
				for (int j = 0; j < cursor.getColumnCount(); j++) {
					Log.i("column name", cursor.getColumnName(j) + ":" + cursor.getString(j));
					if (j == cursor.getColumnCount() - 1) {
						cellString += "\"" + cursor.getColumnName(j).trim() + "\":\"" + cursor.getString(j).trim().replaceAll("\n", "") + "\"";

					} else {

						cellString += "\"" + cursor.getColumnName(j).trim() + "\":\"" + cursor.getString(j).trim().replaceAll("\n", "") + "\",";
					}
				}
				cellString += "},";
				cursor.moveToNext();
				listJson += cellString;
			}

		}
		//		listJson = "{\"list\":[" + listJson + "]}";
		if (listJson.length() > 2) {

			String json = "{\"list\":[" + listJson.substring(0, listJson.length() - 1) + "]}";
			Log.i("json one", "time use" + (System.currentTimeMillis() - l) + json);
			listener.onSuccess(json);
		} else {
			String json = "{\"list\":[]}";
			listener.onSuccess(json);
		}

	}

	public void queryById(String id) {
		long l = System.currentTimeMillis();
		Cursor cursor = database.rawQuery("SELECT * FROM be_app_instruct WHERE me_uid = ? ", new String[] { id });

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				String cellString = "{";
				Log.i("name", cursor.getString(0) + "  " + i + " in " + cursor.getCount() + "==" + cursor.getColumnCount());
				for (int j = 0; j < cursor.getColumnCount(); j++) {
					Log.i("column name", cursor.getColumnName(j) + ":" + cursor.getString(j));
					if (j == cursor.getColumnCount() - 1) {
						String value = "";
						if (cursor.getString(j) != null) {
							value = cursor.getString(j).replaceAll("\n", "");
						}
						cellString += "\"" + cursor.getColumnName(j).trim() + "\":\"" + value + "\"";

					} else {
						String value = "";
						if (cursor.getString(j) != null) {
							value = cursor.getString(j).replaceAll("\n", "");
						}
						cellString += "\"" + cursor.getColumnName(j).trim() + "\":\"" + value + "\",";
					}
				}
				if (i == cursor.getCount() - 1) {

					cellString += "}";
				} else {

					cellString += "},";
				}

				objectJsonString = cellString;
				cursor.moveToNext();
			}
			listener.onSuccess(objectJsonString);
			Log.i("objectJson", "time use:" + (System.currentTimeMillis() - l) + objectJsonString);
		}
	}
}
