package com.rjwl.reginet.teacherread.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/8.
 * 老师对象
 */
@Entity
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private long teacherId;
    @Property
    private int id;
    @Property
    private String userame;
    @Property
    private String password;
    @Property
    private String name;
    @Property
    private String gender;
    @Property
    private String schoolname;
    @Property
    private String cardID;
    @Property
    private int classID;
    @Property
    private int gradeID;//年级
    @Property
    private int schoolID;
    @Property
    private String teachercard;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getTeachercard() {
        return teachercard;
    }

    public void setTeachercard(String teachercard) {
        this.teachercard = teachercard;
    }

    @Generated(hash = 1630413260)
    public Teacher() {
    }

    @Generated(hash = 912144533)
    public Teacher(long teacherId, int id, String userame, String password,
            String name, String gender, String schoolname, String cardID,
            int classID, int gradeID, int schoolID, String teachercard) {
        this.teacherId = teacherId;
        this.id = id;
        this.userame = userame;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.schoolname = schoolname;
        this.cardID = cardID;
        this.classID = classID;
        this.gradeID = gradeID;
        this.schoolID = schoolID;
        this.teachercard = teachercard;
    }


    

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

   

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardID() {
        return this.cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public int getClassID() {
        return this.classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getGradeID() {
        return this.gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public String getUserame() {
        return this.userame;
    }

    public void setUserame(String userame) {
        this.userame = userame;
    }
}
