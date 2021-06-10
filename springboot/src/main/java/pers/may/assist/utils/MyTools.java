package pers.may.assist.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import pers.may.assist.pojo.Constant;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyTools {

    /**
     * .Description://根据字符日期返回星期几
     * .Author:麦克劳林
     * .@Date: 2018/12/29
     */
    public String getWeek(String dateTime){
        String week = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateTime);
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
            week = dateFm.format(date);
            week=week.replaceAll("星期","周");
        }catch (ParseException e){
            e.printStackTrace();
        }
        return week;
    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<Date> getDays(int intervals) {
        ArrayList<Date> pastDaysList = new ArrayList<>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String result = format.format(today);
        return today;
    }

    /**
     * 将文件磁盘路径转换为URL路径，去除盘符和更换反斜杠,末尾加/
     * @param originCode 文件磁盘路径
     * @return
     */
    public static String fixPath(String originCode){
        //将反斜杠用斜杠替换
        String newPath = originCode.replaceAll("\\\\", "/");
        //去掉盘符
        newPath = newPath.replaceAll("^[A-Z]:","");
        newPath = newPath+'/';
        return newPath;
    }

    /**
     *
     * @param url
     * @param jsonObject
     * @param encoding
     * @param sendType 0是get 1是post
     * @return
     */
    public static String send(String url, JSONObject jsonObject, String encoding,int sendType) {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        Iterator iter = jsonObject.entrySet().iterator();

        try {

        switch (sendType){

            //get请求
            case 0:
                //创建get方式请求对象

                URIBuilder uriBuilder = null;

                uriBuilder = new URIBuilder(url);

                //添加参数
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    uriBuilder.addParameter(entry.getKey().toString(), entry.getValue().toString());
                }

                HttpGet httpGet = new HttpGet(uriBuilder.build());


                //设置header信息
                //指定报文头【Content-type】、【User-Agent】
                httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
                //httpPost.setHeader("Content-type", "application/json");
                httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

                //执行请求操作，并拿到结果（同步阻塞）
                CloseableHttpResponse getResponse = client.execute(httpGet);
                //获取结果实体
                HttpEntity entity = getResponse.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, encoding);
                }
                EntityUtils.consume(entity);
                //释放链接
                getResponse.close();
                return body;

            //post请求
            case 1:

                //创建post方式请求对象
                HttpPost httpPost = new HttpPost(url);

                //装填参数
                String jsonStr = jsonObject.toString();
                jsonStr = jsonStr.replace("{","");
                jsonStr = jsonStr.replace("}","");
                jsonStr = jsonStr.replace(":","=");
                jsonStr = jsonStr.replace(",","&");
                jsonStr = jsonStr.replace("\"","");
//                System.out.println(jsonStr);
                StringEntity s = new StringEntity(jsonStr, "utf-8");
                //System.out.println(jsonObject.toString());
                //System.out.println(s);
                s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                        "application/json"));
                //设置参数到请求对象中
                httpPost.setEntity(s);
                //System.out.println("请求地址："+url);
                //System.out.println("请求参数："+s);

                //设置header信息
                //指定报文头【Content-type】、【User-Agent】
                httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
                //httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

                //执行请求操作，并拿到结果（同步阻塞）
                CloseableHttpResponse postResponse = client.execute(httpPost);
                //获取结果实体
                entity = postResponse.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, encoding);
                }
                EntityUtils.consume(entity);
                //释放链接
                postResponse.close();
                return body;

            default:
                return null;
        }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void resetToken(Constant constant){
        JSONObject param = new JSONObject();
        param.put("appid",constant.getAppId());
        param.put("grant_type","client_credential");
        param.put("secret",constant.getAppSecret());
        String resultStr = MyTools.send(constant.getTokenUrl(),param,"utf-8",0);
        JSONObject resultJson  = JSONObject.parseObject(resultStr);
        constant.setAccessToken(resultJson.getString("access_token"));
    }



}
