/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.codebaseOld;

/**
 *
 * Hoang Anh Dep Trai
 */
public class Slider {

    private int sliderId;
    private String content;
    private String link;
    private String status;
    private String backLink;

    public Slider() {
    }

    public Slider(int sliderId, String content, String link, String status, String backLink) {
        this.sliderId = sliderId;
        this.content = content;
        this.link = link;
        this.status= status;
        this.backLink = backLink;
    }

    public String getBackLink() {
        return backLink;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public int getSliderId() {
        return sliderId;
    }

    public Slider setSliderId(int sliderId) {
        this.sliderId = sliderId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Slider setContent(String content) {
        this.content = content;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Slider setLink(String link) {
        this.link = link;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    @Override
    public String toString() {
        return "Slider{" + "sliderId=" + sliderId + ", content=" + content + ", link=" + link + ", backLink=" + backLink + '}';
    }

}
