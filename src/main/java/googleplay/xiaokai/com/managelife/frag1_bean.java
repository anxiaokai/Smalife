package googleplay.xiaokai.com.managelife;

import java.io.Serializable;

/**
 * Created by 孙晓凯 on 2016/5/4.
 */
public class frag1_bean implements Serializable{
    private String image;
    private String title;
    private String content;
    private String resource;
    private String time;
    private String url;

    public frag1_bean() {

    }

    public frag1_bean(String image, String title, String content, String resource, String time, String url) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.resource = resource;
        this.time = time;
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "frag1_bean{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", resource='" + resource + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
