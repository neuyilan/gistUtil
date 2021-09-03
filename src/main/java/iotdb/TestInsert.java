package iotdb;

import com.alibaba.fastjson.JSON;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liutong
 * @date 2021/7/6 18:04
 * @Description TODO
 */
public class TestInsert {

  public static void main(String[] args) {

    SessionPool sessionPool = new SessionPool("172.16.48.4",8685,"root","root",3);
    TestInsert io = new TestInsert();
    List<String> deviceIdsList = new ArrayList<>();
    List<Long> tsList = new ArrayList<>();
    List<List<String>> measurementsList = new ArrayList<>();
    List<List<TSDataType>> tsDataTypeList = new ArrayList<>();
    List<List<Object>> valuesList = new ArrayList<>();
    String deviceId = "root."+"ww"+"."+"www"+"."+"wwwwww";
    deviceIdsList.add(deviceId);
    tsList.add(11111111111111L);
    measurementsList.add(io.setMeasurements());
    tsDataTypeList.add(io.setDataType());
    valuesList.add(io.setValues());
    try {
      sessionPool.insertRecords(deviceIdsList,tsList,measurementsList,tsDataTypeList,valuesList);
    } catch (IoTDBConnectionException e) {
      e.printStackTrace();
    } catch (StatementExecutionException e) {
      e.printStackTrace();
    }
  }


  public List<Object> setValues(){

    String str = "{\"dataSourceType\":\"2\",\"deviceCode\":\"\",\"deviceProps\":\"6\",\"eventCode\":\"w5\",\"eventId\":\"twkJtyY7_1624522489620_1\",\"eventLevel\":\"1\",\"eventName\":\"w5\",\"modelCode\":\"sys_warn_twkJtyY7_1624522489596_2\",\"pointCode\":\"warn_process_1\",\"pointName\":\"warn_process_1\",\"operator\":\">\",\"operatorContent\":\"0\",\"relPropCode\":\"warn_process_1\",\"valueType\":\"long\",\"tags\":\"daling\",\"tenantId\":\"tenant_system\",\"timestamp\":\"1625561831972\",\"warnCode\":\"w5\",\"warnDuration\":\"12000119\",\"warnName\":\"w5\",\"warnType\":\"alarm\",\"restore\":\"0\"}\n";
    Map map = JSON.parseObject(str, Map.class);

    List<Object> values = new ArrayList<>();
    values.add(map.get("dataSourceType"));
    values.add(map.get("deviceCode"));
    values.add(map.get("deviceProps"));
    values.add(map.get("eventCode"));
    values.add(map.get("eventId"));
    values.add(map.get("eventLevel"));
    values.add(map.get("eventName"));
    values.add(map.get("modelCode"));
    values.add(map.get("pointCode"));
    values.add(map.get("pointName"));
    values.add(map.get("operator"));
    values.add(map.get("operatorContent"));
    values.add(map.get("relPropCode"));
    values.add(map.get("valueType"));
    values.add(map.get("tags"));
    values.add(map.get("tenantId"));
    values.add(map.get("timestamp"));
    values.add(map.get("warnCode"));
    values.add(map.get("warnDuration"));
    values.add(map.get("warnName"));
    values.add(map.get("warnType"));
    values.add(map.get("restore"));


        /*List<Object> values = new ArrayList<>();
        values.add(consumerMessage.getEventRst().getEventCode());
        values.add(consumerMessage.getEventRst().getEventName());
        values.add(consumerMessage.getEventRst().getWarnType());
        values.add(consumerMessage.getEventRst().getTags() == null ? "" : consumerMessage.getEventRst().getTags());
        values.add(consumerMessage.getEventRst().getEventLevel()+"");
        values.add(consumerMessage.getEventRst().getWarnId());
        values.add(consumerMessage.getEventRst().getDeviceCode() == null ? "" : consumerMessage.getEventRst().getDeviceCode());
        values.add(consumerMessage.getEventRst().getDeviceName() == null ? "" : consumerMessage.getEventRst().getDeviceName());
        values.add(consumerMessage.getEventRst().getPointName() == null ? "" : consumerMessage.getEventRst().getPointName());
        values.add(consumerMessage.getEventRst().getPointCode() == null ? "" : consumerMessage.getEventRst().getPointCode());
        values.add(consumerMessage.getEventRst().getDeviceTemplateCode() == null ? "" :consumerMessage.getEventRst().getDeviceTemplateCode());
        values.add(consumerMessage.getEventRst().getTemplateName() == null ? "" : consumerMessage.getEventRst().getTemplateName());
        values.add(consumerMessage.getEventRst().getRules() == null ? "" : (consumerMessage.getEventRst().getRules().get(0).getValueType()) == null ? "" : consumerMessage.getEventRst().getRules().get(0).getValueType());
        values.add(consumerMessage.getEventRst().getRules() == null ? "" : (consumerMessage.getEventRst().getRules().get(0).getOperator()) == null ? "" : consumerMessage.getEventRst().getRules().get(0).getOperator());
        values.add(consumerMessage.getEventRst().getRules() == null ? "": (consumerMessage.getEventRst().getRules().get(0).getOperatorCode()) == null ? "": consumerMessage.getEventRst().getRules().get(0).getOperatorCode());
        values.add(consumerMessage.getEventRst().getRules() == null ? "": (consumerMessage.getEventRst().getRules().get(0).getOperatorContent()) == null ? "" : consumerMessage.getEventRst().getRules().get(0).getOperatorContent());
        values.add(consumerMessage.getEventRst().getRules() == null ? "" : (consumerMessage.getEventRst().getRules().get(0).getProcessFunc()) == null ? "" : consumerMessage.getEventRst().getRules().get(0).getProcessFunc());
        values.add(consumerMessage.getEventRst().getRules() == null ? "" : (consumerMessage.getEventRst().getRules().get(0).getRelPropCode()) == null ? "" : consumerMessage.getEventRst().getRules().get(0).getRelPropCode());
        Map<String, Object> deviceProps = consumerMessage.getEventRst().getDeviceProps();
        if (deviceProps != null) {
            if (deviceProps.size() > 1) {
                LOG.warn("Properties size is {}. Use the first one.", deviceProps.size());
            }
            Map.Entry<String, Object> next = deviceProps.entrySet().iterator().next();
            //String key = next.getKey();
            Object value = next.getValue();
            values.add(String.valueOf(value));
        }
        if(consumerMessage.isRestore()){
            values.add(1);
        }else{
            values.add(0);
        }*/

    return values;
  }

  public List<String> setMeasurements(){
    List<String> measurements = new ArrayList<>();
    measurements.add("event_code");
    measurements.add("event_name");
    measurements.add("warn_type");
    measurements.add("tags");
    measurements.add("level");
    measurements.add("warn_id");
    measurements.add("device_code");
    measurements.add("device_name");
    measurements.add("data_pint_name");
    measurements.add("data_point_code");
    measurements.add("template_code");
    measurements.add("template_name");
    measurements.add("value_type");
    measurements.add("operator");
    measurements.add("operator_code");
    measurements.add("operator_content");
    measurements.add("process_func");
    measurements.add("prop_code");
    measurements.add("prop_value");
    measurements.add("status");
    measurements.add("status1");
    measurements.add("status2");
    return measurements;
  }


  public List<TSDataType> setDataType(){
    List<TSDataType> tsDataTypes = new ArrayList<>();
        /*tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.TEXT);
        tsDataTypes.add(TSDataType.INT64);*/

    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    tsDataTypes.add(TSDataType.TEXT);
    return tsDataTypes;
  }


}
