package com.znv.manage.service.impl.event;

import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.Event;
import com.znv.manage.common.bean.event.EventNodeContact;
import com.znv.manage.common.bean.event.EventTask;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.RIOUtil;
import com.znv.manage.common.utils.ThreadPool;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.dao.event.EventNodeContactDao;
import com.znv.manage.dao.event.EventTaskDao;
import com.znv.manage.service.event.EventTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TCfgEventTask表服务实现类
 *
 * @author 0049003788
 * @since 2022-12-09 16:33:00
 */
@Service
@Slf4j
public class EventTaskServiceImpl implements EventTaskService {
    @Resource
    private EventTaskDao eventTaskDao;

    @Resource
    private EventDao eventDao;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${userData.getToken.url}")
    private String getToken;

    @Value("${userData.sendMessage.url}")
    private String sendMessageUrl;

    @Value("${userData.appId}")
    private String appId;

    @Value("${userData.appSecret}")
    private String appSecret;

    @Value("${userData.url}")
    private String userDataUrl;

    @Value("${userData.paasid:wxbhyxgl}")
    String paasid;

    @Value("${userData.paasToken:0e361338083087514abb3e8b9144590d}")
    String paasToken;



    @Resource
    private EventNodeContactDao eventNodeContactDao;

    @Override
    public Result insert(EventTask eventTask) {
        Result result = new Result();
        try {
            List<EventTask> eventTasks = eventTask.taskToList();
            eventTaskDao.insertBatch(eventTasks);
            //发短信
            for (EventTask task : eventTasks) {
                if (!StringUtils.isEmpty(task.getPersonId())) {
                    String mobilePhone = eventTaskDao.selectPhoneByUserId(task.getPersonId());
                    if (!StringUtils.isEmpty(mobilePhone)) {
                        ThreadPool.getInstance().execute(() -> sendMessage(mobilePhone, "有新工单到达，请登录政务微信查收"));
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(EventTask eventTask) {
        Result result = new Result();
        try {
            eventTaskDao.update(eventTask);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteById(Integer id) {
        Result result = new Result();
        try {
            result.setData(eventTaskDao.deleteById(id) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBatch(String ids) {
        Result result = new Result();
        try {
            result.setData(eventTaskDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, String taskTitle, String eventTitle, Integer contactId, String person, String personId, String status, String eventId, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventTaskDao.queryByCondition(id, taskTitle, eventTitle, contactId, person, personId, status, eventId, null, null)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryEventTask(Integer eventId, String eventTitle, Integer eventTypeId, String sourceId,
                                 String streetName, String communityName, String startTime, String endTime,
                                 String status, String taskStartTime, String taskEndTime, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            PageInfo<Event> pageInfo = new PageInfo<>(eventDao.queryByCondition(eventId, eventTitle, eventTypeId,  null, status,null,
                    sourceId, streetName, communityName, startTime, endTime, null, null, null, null));
            for (Event event : pageInfo.getList()) {
                List<EventNodeContact> eventNodeContactList = eventNodeContactDao.queryByCondition(null, event.getId(), null, null, null);
                for (EventNodeContact eventNodeContact : eventNodeContactList) {
                    eventNodeContact.setEventTaskList(eventTaskDao.queryByCondition(null, null, null, eventNodeContact.getId(),
                            null, null, null, null, taskStartTime, taskEndTime));
                }
                event.setEventNodeList(eventNodeContactList);
            }
            result.setData(pageInfo);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    private void sendMessage(String mobile, String msg) {
        //获取token
        String token = getToken();

        if (StringUtils.isEmpty(token)) {
            throw new BusinessException("获取token失败");
        }
        //获取Url
        String urlFormat = "%s?appId=%s&token=%s&mobile=%s&msg=%s";
        String dataUrl = String.format(urlFormat, sendMessageUrl, appId, token, mobile, msg);
        String url = dataUrl.replace("{url}", userDataUrl);

        // 发送请求，获取token
        JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));
        log.info("Request to third platform api [url: {}], response result: {}", url, response);
    }

    public String getToken() {
        String token = "";
        try {
            //校验参数
            if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret) || StringUtils.isEmpty(getToken) || StringUtils.isEmpty(userDataUrl)) {
                return "请检查系统配置文件项是否正确";
            }
            // 封装url
            String urlFormat = "%s?appId=%s&appSecret=%s";
            String url = String.format(urlFormat, getToken, appId, appSecret);
            String tokenUrl = url.replace("{url}", userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(tokenUrl, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", tokenUrl, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !StringUtils.isEmpty(response.getString("data"))) {
                token = response.getString("data");
            }
        } catch (Exception e) {
            log.error("Get token url occur an exception! ", e);
        }
        return token;
    }
}
