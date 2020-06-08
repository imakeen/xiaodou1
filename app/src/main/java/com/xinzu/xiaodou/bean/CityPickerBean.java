package com.xinzu.xiaodou.bean;

import java.util.List;

public class CityPickerBean {


    /**
     * cityList : [{"adCode":"431201","city":"怀化市","spell":"huaihua001"},{"adCode":"130982","city":"任丘市","spell":"renqiu001"},{"adCode":"500000","city":"重庆市","spell":"chongqing001"},{"adCode":"411600","city":"周口市","spell":"zhoukou001"},{"adCode":"410200","city":"开封市","spell":"kaifeng001"},{"adCode":"441200","city":"肇庆市","spell":"zhaoqing001"},{"adCode":"230800","city":"佳木斯市","spell":"jiamusi"},{"adCode":"350500","city":"泉州市","spell":"quanzhou"},{"adCode":"220200","city":"吉林市","spell":"jilin001"},{"adCode":"321200","city":"泰州市","spell":"taizhou001"},{"adCode":"650100","city":"乌鲁木齐市","spell":"wulumuqi001"},{"adCode":"450100","city":"南宁市","spell":"nanning001"},{"adCode":"320900","city":"盐城市","spell":"yancheng001"},{"adCode":"null","city":"襄阳市","spell":"xiangyang001"},{"adCode":"430100","city":"长沙市","spell":"changsha001"},{"adCode":"350300","city":"莆田市","spell":"putian"},{"adCode":"130100","city":"石家庄市","spell":"shijiazhuang"},{"adCode":"410100","city":"郑州市","spell":"zhengzhou001"},{"adCode":"510700","city":"绵阳市","spell":"mianyang001"},{"adCode":"440100","city":"广州市","spell":"guangzhou001"},{"adCode":"220100","city":"长春市","spell":"changchun001"},{"adCode":"610800","city":"榆林市","spell":"yulin"},{"adCode":"630100","city":"西宁市","spell":"xining001"},{"adCode":"150100","city":"呼和浩特市","spell":"huhehaote001"},{"adCode":"152201","city":"乌兰浩特市","spell":"wulanhaote"},{"adCode":"150300","city":"乌海市","spell":"wuhai"},{"adCode":"150200","city":"包头市","spell":"baotou001"},{"adCode":"420100","city":"武汉市","spell":"wuhan001"},{"adCode":"130800","city":"承德市","spell":"chengde001"},{"adCode":"140200","city":"大同市","spell":"datong"},{"adCode":"null","city":"扎鲁特旗","spell":"zhaluteqi"},{"adCode":"230300","city":"鸡西市","spell":"jixi"},{"adCode":"330782","city":"义乌市","spell":"yiwu001"},{"adCode":"620700","city":"张掖市","spell":"zhangye"},{"adCode":"341500","city":"六安市","spell":"luan"},{"adCode":"350200","city":"厦门市","spell":"xiamen001"},{"adCode":"150700","city":"呼伦贝尔市","spell":"hulunbeier"},{"adCode":"411500","city":"信阳市","spell":"xinyang001"},{"adCode":"620100","city":"兰州市","spell":"lanzhou001"},{"adCode":"340800","city":"安庆市","spell":"anqing001"},{"adCode":"511300","city":"南充市","spell":"nanchong001"},{"adCode":"141000","city":"临汾市","spell":"xsb001"},{"adCode":"451100","city":"贺州市","spell":"hezhou"},{"adCode":"442000","city":"中山市","spell":"zshan"},{"adCode":"null","city":"喀什市","spell":"kashi001"},{"adCode":"null","city":"万宁市","spell":"wanning"},{"adCode":"330100","city":"杭州市","spell":"hangzhou"},{"adCode":"620400","city":"白银市","spell":"BY"},{"adCode":"220500","city":"通化市","spell":"tonghua001"},{"adCode":"null","city":"昌吉市","spell":"changji"},{"adCode":"null","city":"金寨县","spell":"jinzhai001"},{"adCode":"320100","city":"南京市","spell":"nanjing"},{"adCode":"150600","city":"鄂尔多斯市","spell":"eerduosi001"},{"adCode":"null","city":"库尔勒市","spell":"kuerle"},{"adCode":"131000","city":"廊坊市","spell":"langfang"},{"adCode":"460200","city":"三亚市","spell":"sanya"},{"adCode":"130900","city":"沧州市","spell":"cangzhou001"},{"adCode":"410300","city":"洛阳市","spell":"luoyang001"},{"adCode":"610100","city":"西安市","spell":"xiaan"},{"adCode":"430300","city":"湘潭市","spell":"xiangtan"},{"adCode":"370400","city":"枣庄市","spell":"zaozhuang"},{"adCode":"520300","city":"遵义市","spell":"zunyi001"},{"adCode":"230700","city":"伊春市","spell":"yichun001"},{"adCode":"null","city":"库车县","spell":"kuchexian01"},{"adCode":"370200","city":"青岛市","spell":"qingdao001"},{"adCode":"652201","city":"哈密市","spell":"hami001"},{"adCode":"140700","city":"晋中市","spell":"jinzhong"},{"adCode":"110100","city":"北京市","spell":"beijing001"},{"adCode":"350900","city":"宁德市","spell":"ningde"},{"adCode":"411300","city":"南阳市","spell":"nanyang001"},{"adCode":"130300","city":"秦皇岛市","spell":"qhd01"},{"adCode":"370300","city":"淄博市","spell":"zibo001"},{"adCode":"530700","city":"丽江市","spell":"lijiang"},{"adCode":"210200","city":"大连市","spell":"dalian001"},{"adCode":"450800","city":"贵港市","spell":"guigang"},{"adCode":"210700","city":"锦州市","spell":"jinzhou001"},{"adCode":"null","city":"福清市","spell":"fuqing"},{"adCode":"null","city":"阿克苏市","spell":"akesu"},{"adCode":"410700","city":"新乡市","spell":"xinxiang001"},{"adCode":"140400","city":"长治市","spell":"Changzhi"},{"adCode":"532900","city":"大理白族自治州","spell":"dali001"},{"adCode":"150500","city":"通辽市","spell":"tongliao001"},{"adCode":"310100","city":"上海市","spell":"shanghai001"},{"adCode":"130500","city":"邢台市","spell":"xingtai"},{"adCode":"320800","city":"淮安市","spell":"huaian001"},{"adCode":"610200","city":"铜川市","spell":"tongchuanshi"},{"adCode":"130682","city":"定州市","spell":"dingzhou001"},{"adCode":"610400","city":"咸阳市","spell":"xianyang001"},{"adCode":"411000","city":"许昌市","spell":"xuchang001"},{"adCode":"411400","city":"商丘市","spell":"shangqiu"},{"adCode":"230200","city":"齐齐哈尔市","spell":"qiqihaer001"},{"adCode":"371000","city":"威海市","spell":"weihai001"},{"adCode":"340400","city":"淮南市","spell":"huainan"},{"adCode":"340100","city":"合肥市","spell":"hefei001"},{"adCode":"440300","city":"深圳市","spell":"shenzhen001"},{"adCode":"210100","city":"沈阳市","spell":"shenyang001"},{"adCode":"140100","city":"太原市","spell":"taiyuan001"},{"adCode":"610600","city":"延安市","spell":"yanan"},{"adCode":"130400","city":"邯郸市","spell":"handan01"},{"adCode":"140800","city":"运城市","spell":"yuncheng"},{"adCode":"120100","city":"天津市","spell":"tianjin001"},{"adCode":"530100","city":"昆明市","spell":"kunming001"},{"adCode":"450200","city":"柳州市","spell":"liuzhou"},{"adCode":"370100","city":"济南市","spell":"jinan001"},{"adCode":"450300","city":"桂林市","spell":"guilin001"},{"adCode":"350100","city":"福州市","spell":"fuzhou001"},{"adCode":"450600","city":"防城港市","spell":"fcgang"},{"adCode":"420500","city":"宜昌市","spell":"yichang001"},{"adCode":"230100","city":"哈尔滨市","spell":"haerbin001"},{"adCode":"540100","city":"拉萨市","spell":"lasa001"},{"adCode":"654002","city":"伊宁市","spell":"yining001"},{"adCode":"460100","city":"海口市","spell":"haikou001"},{"adCode":"510100","city":"成都市","spell":"chengdu"},{"adCode":"130200","city":"唐山市","spell":"tangshan001"},{"adCode":"null","city":"晋江市","spell":"jinjiang"},{"adCode":"440600","city":"佛山市","spell":"foshan"},{"adCode":"370481","city":"滕州市","spell":"tengzhou001"},{"adCode":"140900","city":"忻州市","spell":"xinzhou"},{"adCode":"520100","city":"贵阳市","spell":"guiyang001"},{"adCode":"130700","city":"张家口市","spell":"zhangjiakou001"},{"adCode":"341100","city":"滁州市","spell":"chuzhou001"},{"adCode":"510400","city":"攀枝花市","spell":"panzhihua001"},{"adCode":"null","city":"景洪市","spell":"jinghong001"},{"adCode":"131100","city":"衡水市","spell":"hengshui"},{"adCode":"130600","city":"保定市","spell":"baoding"},{"adCode":"640100","city":"银川市","spell":"yinchuan001"},{"adCode":"450700","city":"钦州市","spell":"qinzhou"},{"adCode":"230600","city":"大庆市","spell":"daqing"}]
     * message : OK
     * status : 1
     */

    private String message;
    private int status;
    private List<CityListBean> cityList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        /**
         * adCode : 431201
         * city : 怀化市
         * spell : huaihua001
         */

        private String adCode;
        private String city;
        private String spell;

        public String getAdCode() {
            return adCode;
        }

        public void setAdCode(String adCode) {
            this.adCode = adCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }
    }
}
