package com.cnblogs.keyindex.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.cnblogs.keyindex.model.Comment;
import com.indexkey.repository.IRepository;
import com.indexkey.repository.dbutility.CursorFormatHelper;
import com.indexkey.repository.dbutility.SqliteHelper;

public class CommentRepository implements IRepository<Comment> {

	private final String TABLE_NAME = "FlashMessages";
	private final String COL_ID = "FeedId";
	private final String COL_AUTHOR = "Author";
	private final String COL_CONTENT = "Content";
	private final String COL_TIME = "GeneralTime";
	private final String COL_PARENT_ID = "ParentFeedId";

	private Context mContext;
	private SqliteHelper db;

	public CommentRepository(Context context) {
		mContext = context;
		db = new SqliteHelper(mContext);
	}

	@Override
	public List<Comment> onDeserializer(Cursor cursor) {
		if (cursor == null)
			return null;
		List<Comment> list = new ArrayList<Comment>();
		while (cursor.moveToNext()) {
			Comment msg = new Comment();
			msg.setAuthor(CursorFormatHelper.getCursorStringValue(COL_AUTHOR,
					cursor));
			msg.setCommentId(CursorFormatHelper.getCursorStringValue(COL_ID,
					cursor));
			msg.setGeneralTime(CursorFormatHelper.getCursorStringValue(
					COL_TIME, cursor));
			msg.setContent(CursorFormatHelper.getCursorStringValue(COL_CONTENT,
					cursor));
			msg.setParentId(CursorFormatHelper.getCursorStringValue(
					COL_PARENT_ID, cursor));
			list.add(msg);
		}
		return list;
	}

	@Override
	public void DeleteEntity(Comment entity) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("Delete from ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(String.format(" where %1s=? ", COL_ID));
		String[] parameters = new String[] { String.valueOf(entity
				.getCommentId()) };
		db.execSql(sqlStr.toString(), parameters);
	}

	@Override
	public void EditEntity(Comment entity) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("Update ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(String.format(" set %1s=?,%2s=?,%3s=?,%4s=? ",
				COL_AUTHOR, COL_CONTENT, COL_TIME, COL_PARENT_ID));
		sqlStr.append(String.format(" where %1s=? ", COL_ID));
		Object[] parameters = new Object[] { entity.getAuthor(),
				entity.getContent(), entity.getGeneralTime(),
				entity.getParentId(), entity.getCommentId() };
		db.execSql(sqlStr.toString(), parameters);
	}

	@Override
	public void SaveEntity(Comment entity) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("insert into ");
		sqlStr.append(TABLE_NAME);
		sqlStr.append(String.format(" set %1s=?,%2s=?,%3s=?,%4s=?,%5s=? ",
				COL_AUTHOR, COL_CONTENT, COL_TIME, COL_PARENT_ID, COL_ID));
		sqlStr.append(" values(?,?,?,?,?)");
		Object[] parameters = new Object[] { entity.getAuthor(),
				entity.getContent(), entity.getGeneralTime(),
				entity.getParentId(), entity.getCommentId() };
		db.execSql(sqlStr.toString(), parameters);
	}

	@Override
	public List<Comment> getAllEntity() {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(String.format("select * from %1s", TABLE_NAME));
		List<Comment> list = db.query(sqlStr.toString(), null, this);
		return list;
	}

	@Override
	public Comment getEntityByKey(String key) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(String.format("select * from %1s", TABLE_NAME));
		sqlStr.append(String.format(" where %1s=? ", COL_ID));
		String[] parameters = new String[] { key };
		List<Comment> list = db.query(sqlStr.toString(), parameters, this);
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
