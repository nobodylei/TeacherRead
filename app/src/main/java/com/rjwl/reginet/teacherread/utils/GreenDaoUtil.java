package com.rjwl.reginet.teacherread.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rjwl.reginet.greendao.DaoMaster;
import com.rjwl.reginet.greendao.DaoSession;
import com.rjwl.reginet.greendao.TeacherDao;
import com.rjwl.reginet.teacherread.entity.Teacher;

/**
 * Created by Administrator on 2018/5/14.
 * 数据库辅助类
 */

public class GreenDaoUtil {
    private DaoMaster master;
    private DaoSession session;
    private SQLiteDatabase db;
    private TeacherDao teacherDao;
    private static GreenDaoUtil greenDaoUtil;

    private GreenDaoUtil(Context context) {
        db = new DaoMaster.DevOpenHelper(context,
                "read.db",
                null).getWritableDatabase();
        master = new DaoMaster(db);
        session = master.newSession();
        teacherDao = session.getTeacherDao();
    }

    public static GreenDaoUtil getDBUtil(Context context) {
        if (greenDaoUtil == null) {
            greenDaoUtil = new GreenDaoUtil(context);
        }
        return greenDaoUtil;
    }

    public void insertTea(Teacher teacher) {
        teacherDao.insert(teacher);
    }

    public Teacher getTeacherById(int id) {
        Teacher s = teacherDao.queryBuilder().
                where(TeacherDao.Properties.Id.eq(id)).unique();
        if (s != null) {
            return s;
        }
        return null;
    }

    public void updataStudent(Teacher teacher){
        if (teacher == null) {
            return;
        }
        teacherDao.update(teacher);
    }

    public void delAll() {
        teacherDao.deleteAll();
    }

}
