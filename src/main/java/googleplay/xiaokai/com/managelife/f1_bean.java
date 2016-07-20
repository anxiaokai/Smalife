package googleplay.xiaokai.com.managelife;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 孙晓凯 on 2016/5/30.
 */
public class f1_bean implements Serializable{

    /**
     * reason : 查询成功
     * result : {"title":"车次T124_火车时刻表及余票查询","list":{"train_no":"T124","train_type":"特快","start_station":"广州","start_station_type":"始","end_station":"长春","end_station_type":"终","start_time":"17:41","end_time":"06:56","run_time":"37小时15分","run_distance":"","price_list":{"item":[{"price_type":"硬座","price":"328"},{"price_type":"硬卧","price":"626"}]}}}
     * error_code : 0
     */

    private String reason;
    /**
     * title : 车次T124_火车时刻表及余票查询
     * list : {"train_no":"T124","train_type":"特快","start_station":"广州","start_station_type":"始","end_station":"长春","end_station_type":"终","start_time":"17:41","end_time":"06:56","run_time":"37小时15分","run_distance":"","price_list":{"item":[{"price_type":"硬座","price":"328"},{"price_type":"硬卧","price":"626"}]}}
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
         * train_no : T124
         * train_type : 特快
         * start_station : 广州
         * start_station_type : 始
         * end_station : 长春
         * end_station_type : 终
         * start_time : 17:41
         * end_time : 06:56
         * run_time : 37小时15分
         * run_distance :
         * price_list : {"item":[{"price_type":"硬座","price":"328"},{"price_type":"硬卧","price":"626"}]}
         */

        private ListBean list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            private String train_no;
            private String train_type;
            private String start_station;
            private String start_station_type;
            private String end_station;
            private String end_station_type;
            private String start_time;
            private String end_time;
            private String run_time;
            private String run_distance;
            private PriceListBean price_list;

            public String getTrain_no() {
                return train_no;
            }

            public void setTrain_no(String train_no) {
                this.train_no = train_no;
            }

            public String getTrain_type() {
                return train_type;
            }

            public void setTrain_type(String train_type) {
                this.train_type = train_type;
            }

            public String getStart_station() {
                return start_station;
            }

            public void setStart_station(String start_station) {
                this.start_station = start_station;
            }

            public String getStart_station_type() {
                return start_station_type;
            }

            public void setStart_station_type(String start_station_type) {
                this.start_station_type = start_station_type;
            }

            public String getEnd_station() {
                return end_station;
            }

            public void setEnd_station(String end_station) {
                this.end_station = end_station;
            }

            public String getEnd_station_type() {
                return end_station_type;
            }

            public void setEnd_station_type(String end_station_type) {
                this.end_station_type = end_station_type;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getRun_time() {
                return run_time;
            }

            public void setRun_time(String run_time) {
                this.run_time = run_time;
            }

            public String getRun_distance() {
                return run_distance;
            }

            public void setRun_distance(String run_distance) {
                this.run_distance = run_distance;
            }

            public PriceListBean getPrice_list() {
                return price_list;
            }

            public void setPrice_list(PriceListBean price_list) {
                this.price_list = price_list;
            }

            public static class PriceListBean {
                /**
                 * price_type : 硬座
                 * price : 328
                 */

                private List<ItemBean> item;

                public List<ItemBean> getItem() {
                    return item;
                }

                public void setItem(List<ItemBean> item) {
                    this.item = item;
                }

                public static class ItemBean implements Serializable{
                    private String price_type;
                    private String price;

                    public String getPrice_type() {
                        return price_type;
                    }

                    public void setPrice_type(String price_type) {
                        this.price_type = price_type;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }
                }
            }
        }
    }
}
