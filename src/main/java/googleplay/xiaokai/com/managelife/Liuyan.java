package googleplay.xiaokai.com.managelife;

import cn.bmob.v3.BmobObject;

/**
 * Created by 孙晓凯 on 2016/7/5.
 */
public class Liuyan extends BmobObject {
    private String username;
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
