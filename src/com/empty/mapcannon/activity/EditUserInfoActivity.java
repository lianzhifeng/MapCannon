
package com.empty.mapcannon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empty.mapcannon.R;
import com.empty.mapcannon.adapter.DateArrayAdapter;
import com.empty.mapcannon.adapter.ProvinceAdapter;
import com.empty.mapcannon.async.BaseHttpAsyncTask;
import com.empty.mapcannon.db.UserInfoDBHandler;
import com.empty.mapcannon.db.UserInfoDBHandler.Key;
import com.empty.mapcannon.model.RegisterInfo;
import com.empty.mapcannon.util.StringUtil;
import com.empty.mapcannon.view.WheelTwoColumnDialog;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class EditUserInfoActivity extends BaseActivity implements OnClickListener {
    private TextView tv_content_home;
    private TextView tv_content_life;
    private EditText et_nickname;
    private TextView tv_gender;
    private RelativeLayout rl_home;
    private RelativeLayout rl_life;
    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_title;
    private boolean scrolling = false;
    private WheelTwoColumnDialog dialog;
    private RegisterInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        findView();
        bindView();
        initData();
    }

    private void initData() {
        this.tv_right.setText("保存");
        this.tv_title.setText("编辑个人资料");
        info = new RegisterInfo();
        String nickname = readPreference(BaseActivity.NAME_USERINFO, Key.NICKNAME);
        String gender = readPreference(BaseActivity.NAME_USERINFO, Key.GENDER);
        String province = readPreference(BaseActivity.NAME_USERINFO, Key.PROVINCE);
        String city = readPreference(BaseActivity.NAME_USERINFO, Key.CITY);
        String phone = readPreference(BaseActivity.NAME_USERINFO, Key.PHONE);
        if (!TextUtils.isEmpty(nickname)) {
            et_nickname.setText(nickname);
        }
        if (!TextUtils.isEmpty(gender)) {
            tv_gender.setText(gender);
        }
        if (!TextUtils.isEmpty(city)) {
            tv_content_life.setText(city);
        }
        if (!TextUtils.isEmpty(province)) {
            tv_content_home.setText(province);
        }
        if (!TextUtils.isEmpty(phone)) {
            info.setPhone(phone);
        }
    }

    private void bindView() {
        this.tv_left.setOnClickListener(this);
        this.tv_right.setOnClickListener(this);
        this.tv_content_home.setOnClickListener(this);
        this.tv_content_life.setOnClickListener(this);
        this.tv_gender.setOnClickListener(this);
    }

    private void findView() {
        this.tv_content_home = ((TextView) findViewById(R.id.tv_content_home));
        this.tv_content_life = ((TextView) findViewById(R.id.tv_content_life));
        this.tv_gender = ((TextView) findViewById(R.id.tv_gender));
        this.et_nickname = ((EditText) findViewById(R.id.et_nickname));
        this.tv_gender = ((TextView) findViewById(R.id.tv_gender));
        this.rl_home = ((RelativeLayout) findViewById(R.id.rl_home));
        this.rl_life = ((RelativeLayout) findViewById(R.id.rl_life));
        this.tv_left = (TextView) findViewById(R.id.tv_left);
        this.tv_right = (TextView) findViewById(R.id.tv_right);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content_home:
                getCityDialog(tv_content_home);
                break;
            case R.id.tv_content_life:
                getCityDialog(tv_content_life);
                break;
            case R.id.tv_gender:
                getGenderDialog();
                break;
            case R.id.tv_left:
                back();
                break;
            case R.id.tv_right:
                modifyUserInfo(info);
                break;
        }
    }

    private void back() {
        finish();
    }

    private void getGenderDialog() {
        View localView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.dialog_age_layout, null);
        final WheelView localWheelView = (WheelView) localView.findViewById(R.id.wv_day);
        localWheelView.setViewAdapter(new DateArrayAdapter(this, new String[] {
                "男", "女"
        }, 0));
        this.dialog = new WheelTwoColumnDialog(this, 2131296437, localView);
        this.dialog.setOnConfirmListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = localWheelView.getCurrentItem();
                String str = "男";
                if (i == 1)
                    str = "女";
                EditUserInfoActivity.this.tv_gender.setText(str);
                EditUserInfoActivity.this.dialog.dismiss();
            }
        });
    }

    private void getCityDialog(final TextView paramTextView) {
        View localView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.dialog_city_layout, null);
        final WheelView localWheelView1 = (WheelView) localView.findViewById(R.id.province);
        localWheelView1.setVisibleItems(5);
        localWheelView1.setViewAdapter(new ProvinceAdapter(this));
        final String[][] arrayOfString = {
                {
                        "安庆", "蚌埠", "巢湖", "池州", "滁州", "阜阳", "合肥", "淮北", "淮南", "黄山", "六安", "马鞍山",
                        "宿州", "铜陵", "芜湖", "宣城", "亳州"
                }, {
                        "北京市", "密云县", "延庆县"
                }, {
                        "福州", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "厦门", "漳州"
                }, {
                        "白银", "定西", "甘南藏族自治州", "嘉峪关", "金昌", "酒泉", "兰州", "临夏回族自治州", "陇南", "平凉", "庆阳",
                        "天水", "武威", "张掖"
                }, {
                        "潮州", "东莞", "佛山", "广州", "河源", "惠州", "江门", "揭阳", "茂名", "梅州", "清远", "汕头",
                        "汕尾", "韶关", "深圳", "阳江", "云浮", "湛江", "肇庆", "中山", "珠海"
                }, {
                        "百色", "北海", "崇左", "防城港", "桂林", "贵港", "河池", "贺州", "来宾", "柳州", "南宁", "钦州",
                        "梧州", "玉林"
                }, {
                        "安顺", "毕节", "贵阳", "六盘水", "黔东南苗族侗族自治州", "黔南布依族苗族自治州", "黔西南布依族苗族自治州", "铜仁",
                        "遵义"
                }, {
                        "白沙黎族自治县", "保亭黎族苗族自治县", "昌江黎族自治县", "澄迈县", "定安县", "东方", "海口", "乐东黎族自治县",
                        "临高县", "陵水黎族自治县", "琼海", "琼中黎族苗族自治县", "三亚", "屯昌县", "万宁", "文昌", "五指山", "儋州"
                }, {
                        "保定", "沧州", "承德", "邯郸", "衡水", "廊坊", "秦皇岛", "石家庄", "唐山", "邢台", "张家口"
                }, {
                        "安阳", "鹤壁", "济源", "焦作", "开封", "洛阳", "南阳", "平顶山", "三门峡", "商丘", "新乡", "信阳",
                        "许昌", "郑州", "周口", "驻马店", "漯河", "濮阳"
                }, {
                        "大庆", "大兴安岭", "哈尔滨", "鹤岗", "黑河", "鸡西", "佳木斯", "牡丹江", "七台河", "齐齐哈尔", "双鸭山",
                        "绥化", "伊春"
                }, {
                        "鄂州", "恩施土家族苗族自治州", "黄冈", "黄石", "荆门", "荆州", "潜江", "神农架林区", "十堰", "随州", "天门",
                        "武汉", "仙桃", "咸宁", "襄樊", "孝感", "宜昌"
                }, {
                        "常德", "长沙", "郴州", "衡阳", "怀化", "娄底", "邵阳", "湘潭", "湘西土家族苗族自治州", "益阳", "永州",
                        "岳阳", "张家界", "株洲"
                }, {
                        "白城", "白山", "长春", "吉林", "辽源", "四平", "松原", "通化", "延边朝鲜族自治州"
                }, {
                        "常州", "淮安", "连云港", "南京", "南通", "苏州", "宿迁", "泰州", "无锡", "徐州", "盐城", "扬州",
                        "镇江"
                }, {
                        "抚州", "赣州", "吉安", "景德镇", "九江", "南昌", "萍乡", "上饶", "新余", "宜春", "鹰潭"
                }, {
                        "鞍山", "本溪", "朝阳", "大连", "丹东", "抚顺", "阜新", "葫芦岛", "锦州", "辽阳", "盘锦", "沈阳",
                        "铁岭", "营口"
                }, {
                        "阿拉善盟", "巴彦淖尔盟", "包头", "赤峰", "鄂尔多斯", "呼和浩特", "呼伦贝尔", "通辽", "乌海", "乌兰察布盟",
                        "锡林郭勒盟", "兴安盟"
                }, {
                        "固原", "石嘴山", "吴忠", "银川"
                }, {
                        "果洛藏族自治州", "海北藏族自治州", "海东", "海南藏族自治州", "海西蒙古族藏族自治州", "黄南藏族自治州", "西宁",
                        "玉树藏族自治州"
                }, {
                        "滨州", "德州", "东营", "菏泽", "济南", "济宁", "莱芜", "聊城", "临沂", "青岛", "日照", "泰安",
                        "威海", "潍坊", "烟台", "枣庄", "淄博"
                }, {
                        "长治", "大同", "晋城", "晋中", "临汾", "吕梁", "朔州", "太原", "忻州", "阳泉", "运城"
                }, {
                        "安康", "宝鸡", "汉中", "商洛", "铜川", "渭南", "西安", "咸阳", "延安", "榆林"
                }, {
                        "崇明县", "上海市"
                }, {
                        "阿坝藏族羌族自治州", "巴中", "成都", "达州", "德阳", "甘孜藏族自治州", "广安", "广元", "乐山",
                        "凉山彝族自 治州", "眉山", "绵阳", "南充", "内江", "攀枝花", "遂宁", "雅安", "宜宾", "资阳", "自贡",
                        "泸州"
                }, {
                        "蓟县", "静海县", "宁河县", "天津市"
                }, {
                        "阿里", "昌都", "拉萨", "林芝", "那曲", "日喀则", "山南"
                }, {
                        "阿克苏", "阿拉尔", "巴音郭楞蒙古自治州", "博尔塔拉蒙古自治州", "昌吉回族自治州", "哈密", "和田", "喀什",
                        "克 拉玛依", "克孜勒苏柯尔克孜自治州", "石河子", "图木舒克", "吐鲁番", "乌鲁木齐", "五家渠", "伊犁哈萨克自治州"
                }, {
                        " 保山", "楚雄彝族自治州", "大理白族自治州", "德宏傣族景颇族自治州", "迪庆藏族自治州", "红河哈尼族彝族自治州", "昆明",
                        "丽江", "临 沧", "怒江傈傈族自治州", "曲靖", "思茅", "文山壮族苗族自治州", "西双版纳傣族自治州", "玉溪", "昭通"
                }, {
                        "杭州", "湖州", "嘉兴", "金华", "丽水", "宁波", "绍兴", "台州", "温州", "舟山", "衢州"
                }, {
                        "重庆市", "沙坪坝", "渝中", "江北", "南岸", "九龙坡", "大渡口", "渝北", "北碚", "万盛", "双桥", "巴南",
                        "万州", "涪陵", "黔江", "长寿", "城口县", "大足县", "垫江县", "丰都县", "奉节县", "合川市", "江津市",
                        "开县", "梁平县", "南川市", "彭 水苗族土家族自治县", "荣昌县", "石柱土家族自治县", "铜梁县", "巫山县", "巫溪县",
                        "武隆县", "秀山土家族苗族自治县", "永川市", " 酉阳土家族苗族自治县", "云阳县", "忠县", "潼南县", "璧山县", "綦江县"
                }
        };
        final WheelView localWheelView2 = (WheelView) localView.findViewById(R.id.city);
        localWheelView2.setVisibleItems(5);
        localWheelView1.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView paramAnonymousWheelView, int paramAnonymousInt1,
                    int paramAnonymousInt2) {
                if (!EditUserInfoActivity.this.scrolling)
                    EditUserInfoActivity.this.updateCities(localWheelView2, arrayOfString,
                            paramAnonymousInt2);
            }
        });
        localWheelView1.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingFinished(WheelView paramAnonymousWheelView) {
                EditUserInfoActivity.this.updateCities(localWheelView2, arrayOfString,
                        localWheelView1.getCurrentItem());
            }

            public void onScrollingStarted(WheelView paramAnonymousWheelView) {
            }
        });
        localWheelView1.setCurrentItem(29);
        this.dialog = new WheelTwoColumnDialog(this, R.style.Dialog_Fullscreen, localView);
        this.dialog.setOnConfirmListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                String str = arrayOfString[localWheelView1.getCurrentItem()][localWheelView2
                        .getCurrentItem()];
                paramTextView.setText(str);
                EditUserInfoActivity.this.dialog.dismiss();
            }
        });
    }

    private void updateCities(WheelView paramWheelView, String[][] paramArrayOfString,
            int paramInt) {
        ArrayWheelAdapter localArrayWheelAdapter = new ArrayWheelAdapter(this,
                paramArrayOfString[paramInt]);
        localArrayWheelAdapter.setTextSize(18);
        paramWheelView.setViewAdapter(localArrayWheelAdapter);
        paramWheelView.setCurrentItem(0);
    }

    private boolean validate() {
        if ((StringUtil.isBlank(this.et_nickname.getText().toString().trim()))
                || (StringUtil.isBlank(this.tv_gender.getText().toString().trim()))) {
            showMyToast("请完善必填选项！");
            return false;
        }
        return true;
    }

    private void modifyUserInfo(final RegisterInfo paramModifyPersonInfo) {
        if (validate()) {
            new BaseHttpAsyncTask<Void, Void, Boolean>(this) {
                protected void onCompleteTask(Boolean paramAnonymousBaseResult) {
                    if (paramAnonymousBaseResult) {
                        showMyToast("填写成功");
                        writePreference(BaseActivity.NAME_USERINFO, Key.NICKNAME, info.getNickname());
                        writePreference(BaseActivity.NAME_USERINFO, Key.GENDER, info.getGender()); 
                        if (!StringUtil.isEmpty(info.getCity())) {
                            writePreference(BaseActivity.NAME_USERINFO, Key.CITY, info.getCity());
                        }
                        if (!StringUtil.isEmpty(info.getProvince())) {
                            writePreference(BaseActivity.NAME_USERINFO, Key.PROVINCE, info.getProvince());
                        }
                        EditUserInfoActivity.this.finish();
                    } else {
                        showMyToast("修改信息失败, 请重试");
                    }
                }

                protected Boolean run(Void[] paramAnonymousArrayOfVoid) {
                    info.setNickname(et_nickname.getText().toString());
                    info.setGender(tv_gender.getText().toString());
                    String province = tv_content_life.getText().toString();
                    if (!StringUtil.isEmpty(province)) {
                        info.setProvince(province);
                    }
                    String city = tv_content_home.getText().toString();
                    if (!StringUtil.isEmpty(city)) {
                        info.setCity(city);
                    }
                    return UserInfoDBHandler.getInstance().editInfo(info);
                }
            }.execute();
        }
    }
}
