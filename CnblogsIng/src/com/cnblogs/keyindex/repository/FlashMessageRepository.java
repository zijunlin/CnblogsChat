package com.cnblogs.keyindex.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.cnblogs.keyindex.model.FlashMessage;
import com.indexkey.repository.IRepository;
import com.indexkey.repository.dbutility.CursorFormatHelper;
import com.indexkey.repository.dbutility.SqliteHelper;

public class FlashMessageRepository implements IRepository<FlashMessage> {

	private final String TABLE_NAME = "FlashMessages";
	private final String COL_ID = "FeedId";
	private final String COL_AUTHOR = "Author";
	private final String COL_CONTENT = "Content";
	private final String COL_TIME = "GeneralTime";
	private final String COL_HEAD_IMAGE = "HeadImageUrl";
	private final String COL_CONTENT_URI = "ContentURI";
	private final String COL_IS_SHINING = "IsShining";
	private final String COL_IS_NEWPERSON = "IsNewPerson";
	private final String COL_HAS_COMMENT = "HasComment";
	private final String COL_MESSAGE_TYPE = "MessageType";

	private Context mContext;
	private SqliteHelper db;

	public FlashMessageRepository(Context context) {
		mContext = context;
		db = new SqliteHelper(mContext);
	}

	@Override
	public List<FlashMessage> onDeserializer(Cursor cursor) {
		if (cursor == null)
			return null;
		List<FlashMessage> list = new ArrayList<FlashMessage>();
		while (cursor.moveToNext()) {
			FlashMessage msg = new FlashMessage();
			msg.setAuthorName(CursorFormatHelper.getCursorStringValue(
					COL_AUTHOR, cursor));
			msg.setFeedId(CursorFormatHelper.getCursorStringValue(COL_ID,
					cursor));
			msg.setGeneralTime(CursorFormatHelper.getCursorStringValue(
					COL_TIME, cursor));
			msg.setHasCommnets(CursorFormatHelper.getCursorBooleanValue(
					COL_HAS_COMMENT, cursor));
			msg.setHeadImageUrl(CursorFormatHelper.getCursorStringValue(
					COL_HEAD_IMAGE, cursor));
			msg.setNewPerson(CursorFormatHelper.getCursorBooleanValue(
					COL_IS_NEWPERSON, cursor));
			msg.setSendContent(CursorFormatHelper.getCursorStringValue(
					COL_CONTENT, cursor));
			msg.setShine(CursorFormatHelper.getCursorBooleanValue(
					COL_IS_SHINING, cursor));
			msg.setMessageType(CursorFormatHelper.getCursorIntegerValue(
					COL_MESSAGE_TYPE, cursor));
			msg.setContentURI(CursorFormatHelper.getCursorStringValue(
					COL_CONTENT_URI, cursor));
			list.add(msg);
		}
		return list;
	}

	@Override
	public void DeleteEntity(FlashMessage entity) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("Delete from ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(String.format(" where %1s=? ", COL_ID));
		String[] parameters = new String[] { String.valueOf(entity.getFeedId()) };
		db.execSql(sqlStr.toString(), parameters);
	}

	@Override
	public void EditEntity(FlashMessage entity) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("Update ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(String.format(
				" set %1s=?,%2s=?,%3s=?,%4s=?,%5s=?,%6s=?,%7s=?,%8s=?,%9s=? ",
				COL_AUTHOR, COL_CONTENT, COL_CONTENT_URI, COL_HAS_COMMENT,
				COL_HEAD_IMAGE, COL_IS_NEWPERSON, COL_IS_SHINING,
				COL_MESSAGE_TYPE, COL_TIME));
		sqlStr.append(String.format(" where %1s=? ", COL_ID));
		Object[] parameters = new Object[] { entity.getAuthorName(),
				entity.getSendContent(), entity.getContentURI(),
				entity.HasComments(), entity.getHeadImageUrl(),
				entity.IsNewPerson(), entity.IsShining(),
				entity.getMessageType(), entity.getGeneralTime() };
		db.execSql(sqlStr.toString(), parameters);

	}

	@Override
	public void SaveEntity(FlashMessage entity) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("insert into ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(String.format(
				"(%1s,%2s,%3s,%4s,%5s,%6s,%7s,%8s,%9s,%10s,)", COL_AUTHOR,
				COL_CONTENT, COL_CONTENT_URI, COL_HAS_COMMENT, COL_HEAD_IMAGE,
				COL_IS_NEWPERSON, COL_IS_SHINING, COL_MESSAGE_TYPE, COL_TIME,
				COL_ID));
		sqlStr.append(" values(?,?,?,?,?,?,?,?,?,?)");
		Object[] parameters = new Object[] { entity.getAuthorName(),
				entity.getSendContent(), entity.getContentURI(),
				entity.HasComments(), entity.getHeadImageUrl(),
				entity.IsNewPerson(), entity.IsShining(),
				entity.getMessageType(), entity.getGeneralTime() };
		db.execSql(sqlStr.toString(), parameters);

	}

	@Override
	public List<FlashMessage> getAllEntity() {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(String.format("select * from %1s", TABLE_NAME));
		List<FlashMessage> list = db.query(sqlStr.toString(), null, this);
		return list;

	}

	/**
	 * 
	 * @param pageIndex base 0 the page index
	 * @param pageSize
	 * @return
	 */
	public List<FlashMessage> queryMessageBy(int pageIndex, int pageSize) {

		
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(String.format("select * from %1s", TABLE_NAME));
		sqlStr.append(String.format(" order by %1s desc", COL_ID));
		sqlStr.append(String.format(" limit %1d", (pageIndex+1) * pageSize));
		sqlStr.append(String.format(" offset %1d", pageIndex  * pageSize));
		List<FlashMessage> list = db.query(sqlStr.toString(), null, this);
		return list;

	}

	@Override
	public FlashMessage getEntityByKey(String key) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(String.format("select * from %1s", TABLE_NAME));
		sqlStr.append(String.format(" where %1s=? ", COL_ID));
		String[] parameters = new String[] { key };
		List<FlashMessage> list = db.query(sqlStr.toString(), parameters, this);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void EditEntity(HashMap<String, Object> ColAndValue) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("Update ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(" set ");
		int i = 0;
		for (String item : ColAndValue.keySet()) {
			if (i == 0) {
				sqlStr.append(item + "=?");
			}

			i++;
			sqlStr.append("," + item + "=?");
			if (i == ColAndValue.size()) {
				sqlStr.append(String.format(" where %1s=? ", item));
			}
		}

		Object[] parameters = ColAndValue.values().toArray();
		db.execSql(sqlStr.toString(), parameters);

	}

}
