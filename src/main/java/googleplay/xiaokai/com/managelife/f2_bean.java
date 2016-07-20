package googleplay.xiaokai.com.managelife;

import java.util.List;

/**
 * Created by 孙晓凯 on 2016/6/1.
 */
public class f2_bean {

    /**
     * reason : 查询成功
     * result : {"title":"北京市长途汽车站_时刻表","list":[{"name":"北京站配载站","tel":"010-65598100","adds":"北京站西邮通街北鲜鱼巷（此站为八王坟配载站）"},{"name":"北京市六里桥长途汽车站","tel":"010-83831716","adds":"丰台区六里桥南里甲19号"},{"name":"北京市赵公口长途汽车站","tel":"010-67229491","adds":"丰台区南三环中路34号院"},{"name":"八王坟长途客运站","tel":"010-87712171 /87718844","adds":"北京市朝阳区西大望路17号"},{"name":"北京市四惠长途汽车站","tel":"010-65574804","adds":"北京市朝阳区建国路68号"}]}
     * error_code : 0
     */

    private String reason;
    /**
     * title : 北京市长途汽车站_时刻表
     * list : [{"name":"北京站配载站","tel":"010-65598100","adds":"北京站西邮通街北鲜鱼巷（此站为八王坟配载站）"},{"name":"北京市六里桥长途汽车站","tel":"010-83831716","adds":"丰台区六里桥南里甲19号"},{"name":"北京市赵公口长途汽车站","tel":"010-67229491","adds":"丰台区南三环中路34号院"},{"name":"八王坟长途客运站","tel":"010-87712171 /87718844","adds":"北京市朝阳区西大望路17号"},{"name":"北京市四惠长途汽车站","tel":"010-65574804","adds":"北京市朝阳区建国路68号"}]
     */

    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String title;
        /**
         * name : 北京站配载站
         * tel : 010-65598100
         * adds : 北京站西邮通街北鲜鱼巷（此站为八王坟配载站）
         */

        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String name;
            private String tel;
            private String adds;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getAdds() {
                return adds;
            }

            public void setAdds(String adds) {
                this.adds = adds;
            }
        }
    }
}
