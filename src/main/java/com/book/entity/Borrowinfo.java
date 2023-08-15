package com.book.entity;

import java.util.Date;

/**
 * Borrowinfo
 *  借阅信息类对应数据库borrowinfo表
 * @author hcj
 * @date 2023-07-26
 */
public class Borrowinfo {
    /**
     * 借阅号
     */
    private String borrowid;
    /**
     * 图书编号
     */
    private String bookid;
    /**
     * 借阅人
     */
    private String borrower;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 借阅时间
     */
    private Date borrowtime;
    /**
     * 归还时间
     */
    private Date returntime;
    /**
     * 是否归还  0:未归还  1：已归还
     */
    private Integer back;

    public Borrowinfo() {
    }

    public String getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(String borrowid) {
        this.borrowid = borrowid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(Date borrowtime) {
        this.borrowtime = borrowtime;
    }

    public Date getReturntime() {
        return returntime;
    }

    public void setReturntime(Date returntime) {
        this.returntime = returntime;
    }

    public Integer getBack() {
        return back;
    }

    public void setBack(Integer back) {
        this.back = back;
    }

    @Override
    public String toString() {
        return "Borrowinfo{" +
                "borrowid='" + borrowid + '\'' +
                ", bookid='" + bookid + '\'' +
                ", borrower='" + borrower + '\'' +
                ", phone='" + phone + '\'' +
                ", borrowtime=" + borrowtime +
                ", returntime=" + returntime +
                ", back=" + back +
                '}';
    }
}
