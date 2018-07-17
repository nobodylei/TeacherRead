package com.rjwl.reginet.teacherread.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 * 书库对象
 */

public class BookStack implements Serializable {

    private String classify;
    private List<Book> book;

    public BookStack() {

    }
    public BookStack(String classify, List<Book> bookList){
        this.classify = classify;
        this.book = bookList;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookStack{" +
                "stackClass='" + classify + '\'' +
                ", bookList=" + book +
                '}';
    }
}
