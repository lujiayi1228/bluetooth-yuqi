package com.jml.ble.model;

/**
 * Created by WYK on 2018/7/10.
 */
public class BoxKey {

    public static final int BOX_SEND_ID = 0x51;  // 箱子一般发送的ID

    public static final int OPEN_BOX = 0x01; // 这边比较的是type
    public static final int LOCK_STATE = 0x02;
    public static final int CELL = 0x03;
    public static final int TEMPERATURE = 0x04;

    public static final int SET_TIME = 0x45; // 这边没有type 所以比较的是id
    public static final int GET_TIME = 0x46; //
    public static final int GET_RECORD = 0x47; //

    public static final int HELM_NOTIFY_ID = 0x54;

    public static final int HELM_STATE = 0x01;
    public static final int HELM_PRESS = 0x02;
    public static final int HELM_CELL = 0x03;
    public static final int ACCELEROMETER = 0x04;

    public static BoxTypeModel openBox(int index) {
        return new BoxTypeModel(index, "OPEN_BOX", BOX_SEND_ID, OPEN_BOX, new int[]{0x01, 0x01});
    }

    public static BoxTypeModel getLockState(int index) {
        return new BoxTypeModel(index, "GET_LOCK_STATE", BOX_SEND_ID, LOCK_STATE, new int[]{0x01, 0x02});
    }

    public static BoxTypeModel getCell(int index) {
        return new BoxTypeModel(index, "GET_CELL", BOX_SEND_ID, CELL, new int[]{0x01, 0x03});
    }

    // 温度
    public static BoxTypeModel getTemperature(int index) {
        return new BoxTypeModel(index, "GET_TEMPERATURE", BOX_SEND_ID, TEMPERATURE, new int[]{0x01, 0x04});
    }

    // 设置时间
//    public static BoxTypeModel setTime(int index, int[] time) { // AD 类型（长度类型数据） 这边没有长度跟类型
//        return new BoxTypeModel(index, "SET_TIME", SET_TIME, 0x00, time);
//    }

    // 拿到时间
    public static BoxTypeModel getTime(int index) {
        return new BoxTypeModel(index, "GET_TIME", GET_TIME, 0x00, new int[]{});
    }

    // 获取操作记录 startTime 开时时间 7位 interval 间隔时间 2位
    // 修改为传条数
    public static BoxTypeModel getRecord(int index, int number) {
        return new BoxTypeModel(index, "GET_RECORD", GET_RECORD, 0x00, new int[]{number});
    }


    //////////////////////////////////////////////// 头盔开始 ///////////////////////////////////
    //////////////////////////////////////////////// 头盔开始 ///////////////////////////////////
    //////////////////////////////////////////////// 头盔开始 ///////////////////////////////////
    public static BoxTypeModel getHelmState(int index) {
        return new BoxTypeModel(index, "HELM_STATE", 0x53, HELM_STATE, new int[]{0x01, 0x01});
    }

    public static BoxTypeModel getHelmPress(int index) {
        return new BoxTypeModel(index, "HELM_PRESS", 0x53, HELM_PRESS, new int[]{0x01, 0x02});
    }

    public static BoxTypeModel getHelmCell(int index) {
        return new BoxTypeModel(index, "HELM_CELL", 0x53, HELM_CELL, new int[]{0x01, 0x03});
    }

    // 接收是 0x54
    public static BoxTypeModel getHelmAccelerometer(int index) {
        return new BoxTypeModel(index, "ACCELEROMETER", 0x54, ACCELEROMETER, new int[]{0x01, 0x04});
    }

}