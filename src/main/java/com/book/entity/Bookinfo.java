package com.book.entity;

/**
 * Bookinfo
 *   图书信息类对应数据库bookinfo表
 * @author hcj
 * @date 2023-07-26
 */
public class Bookinfo {
    /**
     * 图书编号
    */
    private String bookid;
    /**
     * 图书名称
     */
    private String bookname;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 作者
     */
    private String author;
    /**
     * 图书类别 1文学类 2科技类 3艺术类 4教育类 5其他
     */
    private Integer booktype;
    /**
     * 图书类别 字符串类型的 (非数据库字段)
     */
    private String booktypeStr;
    /**
     * 剩余数量
     */
    private Integer remain;

    public Bookinfo() {
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBooktype() {
        return booktype;
    }

    public void setBooktype(Integer booktype) {
        this.booktype = booktype;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public String getBooktypeStr() {
        String booktypeStr = "";
        if(booktype==1){
            booktypeStr = "文学类";
        }
        if(booktype==2){
            booktypeStr = "科技类";
        }
        if(booktype==3){
            booktypeStr = "艺术类";
        }
        if(booktype==4){
            booktypeStr = "教育类";
        }
        if(booktype==5){
            booktypeStr = "其他";
        }
        return booktypeStr;
    }

    public void setBooktypeStr(String booktypeStr) {
        this.booktypeStr = booktypeStr;
    }

    @Override
    public String toString() {
        return "Bookinfo{" +
                "bookid='" + bookid + '\'' +
                ", bookname='" + bookname + '\'' +
                ", publisher='" + publisher + '\'' +
                ", author='" + author + '\'' +
                ", booktype=" + booktype +
                ", remain=" + remain +
                '}';
    }
}
