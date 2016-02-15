
package me.imid.swipebacklayout.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    /**
     * baseurl : http://zy.yaozh.com/bjsp
     * data : {"me_shengchanqiye":"青岛博新生物技术有限公司","me_type":"1","regtime":"","me_bjid":"7","me_uid":"7","me_baojiangongneng":"抗突变","me_zhuyaoyuanliao":"黄芪、淀粉","me_name":"301牌保康胶囊"}
     */
    private String baseurl;
    private DataEntity data;

    public static String getPrefString(Context context, String key, final String defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key, final String value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getPrefBoolean(Context context, final String key,
            final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }

    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key, final float value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(Context context, final String key, final long value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).commit();
    }

    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context, final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public DataEntity getData() {
        return data;
    }


    public static class DataEntity {

        /**
         * me_context : <br>【药品名称】 <br>通用名称：碳酸钙颗粒剂<br>商品名称：<br>英文名称：<br>汉语拼音：<br>【成份】 <br>【性状】 <br>【作用类别】本品为矿物质类非处方药药品。<br>【适应症】 用于预防和治疗钙缺乏症，如骨质疏松、手足抽搐症、骨发育不全、佝偻病以及儿童、妊娠和哺乳期妇女、绝经期妇女、老年人钙的补充。<br>【规格】 每包含钙0.25克<br>【用法用量】 口服。温水冲服，一日1～4包，分次服用。<br>【不良反应】<br>1．嗳气、便秘。<br>2．偶可发生奶-碱综合征，表现为高血钙、碱中毒及肾功能不全。（因服用牛奶及碳酸钙、或单用碳酸钙引起）。<br>3．过量长期服用可引起胃酸分泌反跳性增高，并可发生高钙血症。<br>【禁忌】 高钙血症、高钙尿症、含钙肾结石或有肾结石病史患者禁用。<br>【注意事项】 <br>1.心肾功能不全者慎用。<br>2.对本品过敏者禁用，过敏体质者慎用。<br>3.本品性状发生改变时禁止使用。<br>4.请将本品放在儿童不能接触的地方。<br>5.儿童必须在成人监护下使用。<br>6.如正在使用其他药品，使用本品前请咨询医师或药师。<br>【药物相互作用】<br>1.本品不宜与洋地黄类药物合用。<br>2.大量饮用含酒精和咖啡因的饮料以及大量吸烟，均会抑制钙剂的吸收。<br>3.大量进食富含纤维素的食物能抑制钙的吸收，因钙与纤维素结合成不易吸收的化合物。<br>4.本品与苯妥英钠及四环素类同用，二者吸收减少。<br>5.维生素D、避孕药、雌激素能增加钙的吸收。<br>6.含铝的抗酸药与本品同服时，铝的吸收增多。<br>7.本品与噻嗪类利尿药合用时，易发生高钙血症（因增加肾小管对钙的重吸收）。<br>8.本品与含钾药物合用时，应注意心律失常的发生。<br>9.如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。<br><span style="color=#0369b1">【药理作用】</span> 本品参与骨骼的形成与骨折后骨组织的再建以及肌肉收缩、神经传递、凝血机制并降低毛细血管的渗透性等。<br>【贮藏】  【包装】  【有效期】  【执行标准】  【批准文号】  【说明书修订日期】<br>【生产企业】<br>企业名称：    生产地址：    邮政编码：      电话号码：     传真号码：     网址：<br>如有问题可与生产企业联系<br><br>
         * me_xianghuzhuoyong : 1.本品不宜与洋地黄类药物合用。2.大量饮用含酒精和咖啡因的饮料以及大量吸烟，均会抑制钙剂的吸收。3.大量进食富含纤维素的食物能抑制钙的吸收，因钙与纤维素结合成不易吸收的化合物。4.本品与苯妥英钠及四环素类同用，二者吸收减少。5.维生素D、避孕药、雌激素能增加钙的吸收。6.含铝的抗酸药与本品同服时，铝的吸收增多。7.本品与噻嗪类利尿药合用时，易发生高钙血症（因增加肾小管对钙的重吸收）。8.本品与含钾药物合用时，应注意心律失常的发生。9.如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。
         * me_kezhunriqi :
         * me_guige : 每包含钙0.25克
         * me_zhuyi : 1.心肾功能不全者慎用。2.对本品过敏者禁用，过敏体质者慎用。3.本品性状发生改变时禁止使用。4.请将本品放在儿童不能接触的地方。5.儿童必须在成人监护下使用。6.如正在使用其他药品，使用本品前请咨询医师或药师。
         * me_baozhuang :
         * me_xiugairiqi :
         * me_changjia :
         * me_yunfu :
         * me_laonian :
         * me_xishoufenbu :
         * me_zhuzhi :
         * me_zhucang :
         * me_yaoliduli :
         * me_uid : 6959
         * me_atc : A12A
         * me_yaoli : 本品参与骨骼的形成与骨折后骨组织的再建以及肌肉收缩、神经传递、凝血机制并降低毛细血管的渗透性等。
         * me_brandname :
         * me_source : CFDA化学药(OTC)说明书
         * me_zuoyongyongtu :
         * me_chengxujiliang :
         * me_xingzhuang :
         * me_name : 碳酸钙颗粒
         * me_jieguopanding :
         * me_duli :
         * me_youxiaoqi :
         * me_yaodai :
         * me_yongfa : 口服。温水冲服，一日1～4包，分次服用。
         * me_shiyongduixiang :
         * me_ertong :
         * me_zhixingbiaozhun :
         * me_fanying : 1．嗳气、便秘。2．偶可发生奶-碱综合征，表现为高血钙、碱中毒及肾功能不全。（因服用牛奶及碳酸钙、或单用碳酸钙引起）。3．过量长期服用可引起胃酸分泌反跳性增高，并可发生高钙血症。
         * me_chengfen :
         * me_pinyin :
         * me_pizhunwenhao :
         * me_yibao : 乙
         * me_jingji : 高钙血症、高钙尿症、含钙肾结石或有肾结石病史患者禁用。
         * me_zuoyongleibie : 本品为矿物质类非处方药药品。
         * me_jiehzongduixiang :
         * me_guoliang :
         * me_enname :
         */
        private String me_context;
        private String me_xianghuzhuoyong;
        private String me_kezhunriqi;
        private String me_guige;
        private String me_zhuyi;
        private String me_baozhuang;
        private String me_xiugairiqi;
        private String me_changjia;
        private String me_yunfu;
        private String me_laonian;
        private String me_xishoufenbu;
        private String me_zhuzhi;
        private String me_zhucang;
        private String me_yaoliduli;
        private String me_uid;
        private String me_atc;
        private String me_yaoli;
        private String me_brandname;
        private String me_source;
        private String me_zuoyongyongtu;
        private String me_chengxujiliang;
        private String me_xingzhuang;
        private String me_name;
        private String me_jieguopanding;
        private String me_duli;
        private String me_youxiaoqi;
        private String me_yaodai;
        private String me_yongfa;
        private String me_shiyongduixiang;
        private String me_ertong;
        private String me_zhixingbiaozhun;
        private String me_fanying;
        private String me_chengfen;
        private String me_pinyin;
        private String me_pizhunwenhao;
        private String me_yibao;
        private String me_jingji;
        private String me_zuoyongleibie;
        private String me_jiehzongduixiang;
        private String me_guoliang;
        private String me_enname;

        public void setMe_context(String me_context) {
            this.me_context = me_context;
        }

        public void setMe_xianghuzhuoyong(String me_xianghuzhuoyong) {
            this.me_xianghuzhuoyong = me_xianghuzhuoyong;
        }

        public void setMe_kezhunriqi(String me_kezhunriqi) {
            this.me_kezhunriqi = me_kezhunriqi;
        }

        public void setMe_guige(String me_guige) {
            this.me_guige = me_guige;
        }

        public void setMe_zhuyi(String me_zhuyi) {
            this.me_zhuyi = me_zhuyi;
        }

        public void setMe_baozhuang(String me_baozhuang) {
            this.me_baozhuang = me_baozhuang;
        }

        public void setMe_xiugairiqi(String me_xiugairiqi) {
            this.me_xiugairiqi = me_xiugairiqi;
        }

        public void setMe_changjia(String me_changjia) {
            this.me_changjia = me_changjia;
        }

        public void setMe_yunfu(String me_yunfu) {
            this.me_yunfu = me_yunfu;
        }

        public void setMe_laonian(String me_laonian) {
            this.me_laonian = me_laonian;
        }

        public void setMe_xishoufenbu(String me_xishoufenbu) {
            this.me_xishoufenbu = me_xishoufenbu;
        }

        public void setMe_zhuzhi(String me_zhuzhi) {
            this.me_zhuzhi = me_zhuzhi;
        }

        public void setMe_zhucang(String me_zhucang) {
            this.me_zhucang = me_zhucang;
        }

        public void setMe_yaoliduli(String me_yaoliduli) {
            this.me_yaoliduli = me_yaoliduli;
        }

        public void setMe_uid(String me_uid) {
            this.me_uid = me_uid;
        }

        public void setMe_atc(String me_atc) {
            this.me_atc = me_atc;
        }

        public void setMe_yaoli(String me_yaoli) {
            this.me_yaoli = me_yaoli;
        }

        public void setMe_brandname(String me_brandname) {
            this.me_brandname = me_brandname;
        }

        public void setMe_source(String me_source) {
            this.me_source = me_source;
        }

        public void setMe_zuoyongyongtu(String me_zuoyongyongtu) {
            this.me_zuoyongyongtu = me_zuoyongyongtu;
        }

        public void setMe_chengxujiliang(String me_chengxujiliang) {
            this.me_chengxujiliang = me_chengxujiliang;
        }

        public void setMe_xingzhuang(String me_xingzhuang) {
            this.me_xingzhuang = me_xingzhuang;
        }

        public void setMe_name(String me_name) {
            this.me_name = me_name;
        }

        public void setMe_jieguopanding(String me_jieguopanding) {
            this.me_jieguopanding = me_jieguopanding;
        }

        public void setMe_duli(String me_duli) {
            this.me_duli = me_duli;
        }

        public void setMe_youxiaoqi(String me_youxiaoqi) {
            this.me_youxiaoqi = me_youxiaoqi;
        }

        public void setMe_yaodai(String me_yaodai) {
            this.me_yaodai = me_yaodai;
        }

        public void setMe_yongfa(String me_yongfa) {
            this.me_yongfa = me_yongfa;
        }

        public void setMe_shiyongduixiang(String me_shiyongduixiang) {
            this.me_shiyongduixiang = me_shiyongduixiang;
        }

        public void setMe_ertong(String me_ertong) {
            this.me_ertong = me_ertong;
        }

        public void setMe_zhixingbiaozhun(String me_zhixingbiaozhun) {
            this.me_zhixingbiaozhun = me_zhixingbiaozhun;
        }

        public void setMe_fanying(String me_fanying) {
            this.me_fanying = me_fanying;
        }

        public void setMe_chengfen(String me_chengfen) {
            this.me_chengfen = me_chengfen;
        }

        public void setMe_pinyin(String me_pinyin) {
            this.me_pinyin = me_pinyin;
        }

        public void setMe_pizhunwenhao(String me_pizhunwenhao) {
            this.me_pizhunwenhao = me_pizhunwenhao;
        }

        public void setMe_yibao(String me_yibao) {
            this.me_yibao = me_yibao;
        }

        public void setMe_jingji(String me_jingji) {
            this.me_jingji = me_jingji;
        }

        public void setMe_zuoyongleibie(String me_zuoyongleibie) {
            this.me_zuoyongleibie = me_zuoyongleibie;
        }

        public void setMe_jiehzongduixiang(String me_jiehzongduixiang) {
            this.me_jiehzongduixiang = me_jiehzongduixiang;
        }

        public void setMe_guoliang(String me_guoliang) {
            this.me_guoliang = me_guoliang;
        }

        public void setMe_enname(String me_enname) {
            this.me_enname = me_enname;
        }

        public String getMe_context() {
            return me_context;
        }

        public String getMe_xianghuzhuoyong() {
            return me_xianghuzhuoyong;
        }

        public String getMe_kezhunriqi() {
            return me_kezhunriqi;
        }

        public String getMe_guige() {
            return me_guige;
        }

        public String getMe_zhuyi() {
            return me_zhuyi;
        }

        public String getMe_baozhuang() {
            return me_baozhuang;
        }

        public String getMe_xiugairiqi() {
            return me_xiugairiqi;
        }

        public String getMe_changjia() {
            return me_changjia;
        }

        public String getMe_yunfu() {
            return me_yunfu;
        }

        public String getMe_laonian() {
            return me_laonian;
        }

        public String getMe_xishoufenbu() {
            return me_xishoufenbu;
        }

        public String getMe_zhuzhi() {
            return me_zhuzhi;
        }

        public String getMe_zhucang() {
            return me_zhucang;
        }

        public String getMe_yaoliduli() {
            return me_yaoliduli;
        }

        public String getMe_uid() {
            return me_uid;
        }

        public String getMe_atc() {
            return me_atc;
        }

        public String getMe_yaoli() {
            return me_yaoli;
        }

        public String getMe_brandname() {
            return me_brandname;
        }

        public String getMe_source() {
            return me_source;
        }

        public String getMe_zuoyongyongtu() {
            return me_zuoyongyongtu;
        }

        public String getMe_chengxujiliang() {
            return me_chengxujiliang;
        }

        public String getMe_xingzhuang() {
            return me_xingzhuang;
        }

        public String getMe_name() {
            return me_name;
        }

        public String getMe_jieguopanding() {
            return me_jieguopanding;
        }

        public String getMe_duli() {
            return me_duli;
        }

        public String getMe_youxiaoqi() {
            return me_youxiaoqi;
        }

        public String getMe_yaodai() {
            return me_yaodai;
        }

        public String getMe_yongfa() {
            return me_yongfa;
        }

        public String getMe_shiyongduixiang() {
            return me_shiyongduixiang;
        }

        public String getMe_ertong() {
            return me_ertong;
        }

        public String getMe_zhixingbiaozhun() {
            return me_zhixingbiaozhun;
        }

        public String getMe_fanying() {
            return me_fanying;
        }

        public String getMe_chengfen() {
            return me_chengfen;
        }

        public String getMe_pinyin() {
            return me_pinyin;
        }

        public String getMe_pizhunwenhao() {
            return me_pizhunwenhao;
        }

        public String getMe_yibao() {
            return me_yibao;
        }

        public String getMe_jingji() {
            return me_jingji;
        }

        public String getMe_zuoyongleibie() {
            return me_zuoyongleibie;
        }

        public String getMe_jiehzongduixiang() {
            return me_jiehzongduixiang;
        }

        public String getMe_guoliang() {
            return me_guoliang;
        }

        public String getMe_enname() {
            return me_enname;
        }
    }
}
