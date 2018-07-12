package com.jinan.haosuanjia.ui.framgment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jinan.haosuanjia.R;
import com.jinan.haosuanjia.bean.AuotationDetailBean;
import com.jinan.haosuanjia.bean.LabourServicesBean;
import com.jinan.haosuanjia.request.BaseHandlerJsonObject;
import com.jinan.haosuanjia.request.module.AuctionModule;
import com.jinan.haosuanjia.ui.base.BaseFragment;
import com.jinan.haosuanjia.utils.ParseJson;
import com.jinan.haosuanjia.utils.ShowToastUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 行情-大蒜价格
 * Created by Administrator on 2018/1/15.
 */

public class QuotationOneFragment extends BaseFragment implements View.OnClickListener {
//    private XListView lv_activity_main;
    private ViewPager view_pager;
    private TextView tv_zt_more;
    private RelativeLayout rl_zoushi_one;
    private RelativeLayout rl_zoushi_two;
    private RelativeLayout rl_zoushi_three;
    private RelativeLayout rl_zoushi_four;
    private ImageView iv_zoushi_one;
    private ImageView iv_zoushi_two;
    private ImageView iv_zoushi_three;
    private ImageView iv_zoushi_four;
    private TextView tv_zoushi_one;
    private TextView tv_zoushi_two;
    private TextView tv_zoushi_three;
    private TextView tv_zoushi_four;
    private Spinner spinner_data;
    LineChart chart;
    LineChart chart2;
    private Typeface mTf;
//    List<ActivityListBean> activityList; // 动态数组
//    ActivityListAdapter feedAdapter;
//    private int next=0;
//    private int limit=20;
//    private String cityId="1";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_CLASSID = "classId";
    private String mParam1;
    private String classId;
    private String country="165";
    private String cropid="4";
    private String month="6";
    private int index=1;

    List<AuotationDetailBean> activityList=new ArrayList<>(); // 动态数组

    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
//    String longitude="";
//    String latitude="";
//    TextView viewEmpty;
//    View v_default;
    public static QuotationOneFragment newInstance(String param1,String classId) {
        QuotationOneFragment fragment = new QuotationOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_CLASSID, classId);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            classId = getArguments().getString(ARG_CLASSID);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_quotation_one_fragment, container, false);
        this.inflater = inflater;
        mTf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
        init(view);
        loadData2(1);

//        chartItem=new LineChartItem(generateDataLine(1), context);

        return view;
    }

    private void loadData(int index,List<AuotationDetailBean> activityList) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        chart.setData((LineData) generateDataLine(1,index,activityList));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        chart.animateX(200);
        chart.animateX(200);
//        v_default.setVisibility(View.VISIBLE);
//        viewEmpty.setVisibility(View.VISIBLE);
//        viewEmpty.setText(getString(R.string.no_content_activity));
        List<ILineDataSet> sets = chart.getData()
                .getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setDrawValues(!set.isDrawValuesEnabled());
        }

        chart.invalidate();
    }
    private void loadData2(int index) {
        chart2.getDescription().setEnabled(false);
        chart2.setDrawGridBackground(true);

        XAxis xAxis = chart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = chart2.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart2.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        chart2.setData((LineData) generateDataLine2(1,index));
        chart2.animateX(200);
        List<ILineDataSet> sets = chart2.getData()
                .getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setDrawValues(!set.isDrawValuesEnabled());
        }

        chart2.invalidate();
    }

    private void init(View view) {
        context=getActivity();
        inflater = LayoutInflater.from(context);
        rl_zoushi_one = (RelativeLayout) view.findViewById(R.id.rl_zoushi_one);
        rl_zoushi_two = (RelativeLayout) view.findViewById(R.id.rl_zoushi_two);
        rl_zoushi_three = (RelativeLayout) view.findViewById(R.id.rl_zoushi_three);
        rl_zoushi_four = (RelativeLayout) view.findViewById(R.id.rl_zoushi_four);
        iv_zoushi_one = (ImageView) view.findViewById(R.id.iv_zoushi_one);
        iv_zoushi_two = (ImageView) view.findViewById(R.id.iv_zoushi_two);
        iv_zoushi_three = (ImageView) view.findViewById(R.id.iv_zoushi_three);
        iv_zoushi_four = (ImageView) view.findViewById(R.id.iv_zoushi_four);
        tv_zoushi_one = (TextView) view.findViewById(R.id.tv_zoushi_one);
        tv_zoushi_two = (TextView) view.findViewById(R.id.tv_zoushi_two);
        tv_zoushi_three = (TextView) view.findViewById(R.id.tv_zoushi_three);
        tv_zoushi_four = (TextView) view.findViewById(R.id.tv_zoushi_four);
        spinner_data = (Spinner) view.findViewById(R.id.spinner_data);
        chart = (LineChart) view.findViewById(R.id.chart);
        chart2 = (LineChart) view.findViewById(R.id.chart2);
        rl_zoushi_one.setOnClickListener(this);
        rl_zoushi_two.setOnClickListener(this);
        rl_zoushi_three.setOnClickListener(this);
        rl_zoushi_four.setOnClickListener(this);
        //数据
        data_list = new ArrayList<String>();
        data_list.add("1月");
        data_list.add("2月");
        data_list.add("3月");
        data_list.add("4月");
        data_list.add("5月");
        data_list.add("6月");
        data_list.add("7月");
        data_list.add("8月");
        data_list.add("9月");
        data_list.add("10月");
        data_list.add("11月");
        data_list.add("12月");

        //适配器
        arr_adapter= new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_data.setAdapter(arr_adapter);
        spinner_data.setSelection(2,true);
        spinner_data.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
//                tv_zoushi_four.setText("您选择的是：" + arg2+"个");//文本说明
                ShowToastUtil.Short("您选择的是：" + data_list.get(arg2));
                spinner_data.setSelection(arg2,true);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                tv_zoushi_four.setText("Nothing");

                ShowToastUtil.Short("您选择的是：Nothing" );
            }
        });

    }
    private LineData generateDataLine(int cnt,int index,List<AuotationDetailBean> arrayList) {

        ArrayList<Entry> e1 = new ArrayList<>();
        if (arrayList!=null)
        for (int i = 0; i < arrayList.size(); i++) {
//            if(i==0) {
//                e1.add(new Entry(i, arrayList.get(i).price));
//            }
                e1.add(new Entry(arrayList.get(i).day, arrayList.get(i).price));
        }
        LineDataSet d1=null;
        if(index==1){
            d1= new LineDataSet(e1, "金乡走势");
        }else if(index==2){
            d1= new LineDataSet(e1, "邳州走势");
        }else if(index==3){
            d1= new LineDataSet(e1, "莱芜走势");
        }else if(index==4){
            d1= new LineDataSet(e1, "沧州走势");
        }

        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

//        ArrayList<Entry> e2 = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            e2.add(new Entry(i, e1.get(i).getY() - 2));
//        }
//
//        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
//        d2.setLineWidth(2.5f);
//        d2.setCircleRadius(4.5f);
//        d2.setHighLightColor(Color.rgb(244, 117, 117));
//        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
//        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[4]);
//        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
//        sets.add(d2);

        LineData cd = new LineData(sets);
        return cd;
    }
    private LineData generateDataLine2(int cnt,int index) {

        ArrayList<Entry> e1 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 5) + 10));
        }
        LineDataSet d1=null;
        if(index==1){
            d1= new LineDataSet(e1, "金乡走势");
        }else if(index==2){
            d1= new LineDataSet(e1, "邳州走势");
        }else if(index==3){
            d1= new LineDataSet(e1, "莱芜走势");
        }else if(index==4){
            d1= new LineDataSet(e1, "沧州走势");
        }

        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            e2.add(new Entry(i, e1.get(i).getY() - 2));
        }

        LineDataSet d2 = new LineDataSet(e2, "金乡县");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);
        ArrayList<Entry> e3 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            e3.add(new Entry(i, e2.get(i).getY() - 2));
        }

        LineDataSet d3= new LineDataSet(e3, "金乡县");
        d3.setLineWidth(2.5f);
        d3.setCircleRadius(4.5f);
        d3.setHighLightColor(Color.rgb(244, 117, 117));
//        d3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);

        d3.setColor(getActivity().getResources().getColor(R.color.red));
        d3.setCircleColor(getActivity().getResources().getColor(R.color.red));
        d3.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        sets.add(d3);

        LineData cd = new LineData(sets);
        return cd;
    }
    @Override
    protected void initData() {
        getQuotationByCountry(country,cropid,month,index);
    }
    private void getQuotationByCountry(String country,String cropid,String month,final int index) {
        AuctionModule.getInstance().getQuotationByCountry(context,country,cropid,month,new BaseHandlerJsonObject() {
                    @Override
                    public void onGotJson(JSONObject result) {
                        try {
                            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result.toString());
                            if(jsonObject.getInteger("status")==1){
                                try{
                                    activityList = ParseJson.parseGetResultCollection(result, "data", AuotationDetailBean.class);
                                    loadData(index,activityList);
                                    loadData2(1);
                                }catch (Exception e ){
                                    e.printStackTrace();
//                                    ShowToastUtil.Short("没有更多数据！");
                                }
                            }else{
                                ShowToastUtil.Short(jsonObject.getString("msg"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowToastUtil.Short("解析异常！");
                        }
                    }

                    @Override
                    public void onGotError(String code, String error) {
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_zoushi_one:
                setTextColor(1);
//                country="1729";
                getQuotationByCountry(country,cropid,month,1);
//                loadData(1,activityList);
                loadData2(1);
                break;
            case R.id.rl_zoushi_two:
                setTextColor(2);
//                country="1240";
                getQuotationByCountry(country,cropid,month,2);
//                loadData(2,activityList);
                loadData2(2);
                break;
            case R.id.rl_zoushi_three:
                setTextColor(3);
//                country="173";
                getQuotationByCountry(country,cropid,month,3);
//                loadData(3,activityList);
                loadData2(3);
                break;
            case R.id.rl_zoushi_four:
                setTextColor(4);
//                country="46";
                getQuotationByCountry(country,cropid,month,4);
//                loadData(4,activityList);
                loadData2(4);
                break;
            default:
                    break;
        }
    }

    private void setTextColor(int index) {
        iv_zoushi_one.setVisibility(View.INVISIBLE);
        iv_zoushi_two.setVisibility(View.INVISIBLE);
        iv_zoushi_three.setVisibility(View.INVISIBLE);
        iv_zoushi_four.setVisibility(View.INVISIBLE);
        tv_zoushi_one.setTextColor(getActivity().getResources().getColor(R.color.black));
        tv_zoushi_two.setTextColor(getActivity().getResources().getColor(R.color.black));
        tv_zoushi_three.setTextColor(getActivity().getResources().getColor(R.color.black));
        tv_zoushi_four.setTextColor(getActivity().getResources().getColor(R.color.black));
        if (index==1){
            iv_zoushi_one.setVisibility(View.VISIBLE);
            tv_zoushi_one.setTextColor(getActivity().getResources().getColor(R.color.color_main));
        }else  if (index==2){
            iv_zoushi_two.setVisibility(View.VISIBLE);
            tv_zoushi_two.setTextColor(getActivity().getResources().getColor(R.color.color_main));
        }else  if (index==3){
            iv_zoushi_three.setVisibility(View.VISIBLE);
            tv_zoushi_three.setTextColor(getActivity().getResources().getColor(R.color.color_main));
        }else  if (index==4){
            iv_zoushi_four.setVisibility(View.VISIBLE);
            tv_zoushi_four.setTextColor(getActivity().getResources().getColor(R.color.color_main));
        }

    }
}
