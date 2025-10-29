package com.znv.manage.controller.tip;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.bean.user.MessagePrecinct;
import com.znv.manage.bean.user.MessageUser;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.MessageManage;
import com.znv.manage.common.bean.tip.MessagePrecinctBean;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.dao.tip.MessageManageDao;
import com.znv.manage.dao.tip.MessagePrecinctBeanDao;
import com.znv.manage.service.tip.MessageManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
@Api(tags = "消息管理")
public class MessageManageController {
    @Autowired
    private MessageManageDao messageManageDao;

    @Autowired
    private MessageManageService messageManageService;

    @Autowired
    private MessagePrecinctBeanDao messagePrecinctBeanDao;

    @Value("${filePath}")
    private String parentDirPath;

    @Value("${urlPath}")
    private String fileUrlAddress;

    @Value("${filePath}")
    private String filePath;

    @Value("${urlPath}")
    private String urlPath;

    @ApiOperation("查询消息详情")
    @RequestMapping(value = "/queryMessage", method = RequestMethod.GET)
    @ResponseBody
    public Result queryMessage(
            @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(value = "messageId", required = false) String messageId,
            @RequestParam(value = "messageContent", required = false) String messageContent,
            @RequestParam(value = "messageTitle", required = false) String messageTitle,
            @RequestParam(value = "messageType", required = false) String messageType,
            @RequestParam(value = "sendPrecinctName", required = false) String sendPrecinctName,
            @RequestParam(value = "receivePrecinctName", required = false) String receivePrecinctName,
            @RequestParam(value = "receivePerson", required = false) String receivePerson
    ) {
        Result result = new Result();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = messageManageDao.queryMessage(messageId, messageContent, messageTitle, messageType, sendPrecinctName, receivePerson, receivePrecinctName);
        PageInfo pageInfo = new PageInfo(page);
        if (list.isEmpty()) {
            result.setMessage("查询结果不存在");
        } else {
            result.setData(pageInfo);
            result.setMessage("success");
        }
        return result;
    }

    @ApiOperation("查询组织")
    @RequestMapping(value = "/queryPrecinct", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> queryPrecinct(@RequestParam(value = "roleId", required = false) String roleId

    ) {
        List<Map<String, Object>> list = messageManageDao.queryPrecinct(roleId);
        return list;
    }

    @ApiOperation("新增消息详情")
    @RequestMapping(value = "/saveMessageInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMessageInfo(
            @RequestParam(value = "messageContent", required = false) String messageContent,
            @RequestParam(value = "messageTitle", required = false) String messageTitle,
            @RequestParam(value = "sendPerson",required = false) String sendPerson,
            @RequestParam(value = "sendPrecinctName", required = false) String sendPrecinctName,
            @RequestParam(value = "sendPhone", required = false) String sendPhone,
            @RequestParam(value = "messagePrecinctBean", required = false) String data,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "messageLevel",required = false) String messageLevel,
            @RequestParam(value = "messageCategory",required = false) String messageCategory
    ) {
        Result ret = new Result();

        try {
            MessageManage messageManage = new MessageManage(null, messageContent, messageTitle, sendPerson, sendPrecinctName, sendPhone, null, messageLevel, messageCategory);
            System.out.println(messageManage);
            messageManageService.saveMessageInfo(messageManage);
            String id = messageManage.getMessageId();
            if (null != files) {
                List<String> messageFilePath = new ArrayList<>();
                saveFiles(files, messageFilePath);
                String ImgUrl = !CollectionUtils.isEmpty(messageFilePath) ? String.join(",", messageFilePath) : null;
                if(ImgUrl != null){
                    messageManageService.saveImgUrl(ImgUrl, id);
                }
            }

            List<MessagePrecinctBean> list = JSONObject.parseArray(data, MessagePrecinctBean.class);
            Set<MessagePrecinctBean> precinctBeanList = new HashSet<>();
            for (int j = 0; j < list.size(); j++) {
                if (StringUtils.isEmpty(list.get(j).getReceivePerson())) { // 如果接收人为空，则默认发送给改组织下的所有人
                    List<MessagePrecinct> messageUser = messageManageDao.queryMessageUser(String.valueOf(list.get(j).getReceivePrecinctName()),null);
                    if(!CollectionUtils.isEmpty(messageUser)) {
                        List<MessageUser> users = messageUser.get(0).getUsers();
                        for (int i = 0; i < users.size(); i++) {
                            MessagePrecinctBean messagePrecinctBean = new MessagePrecinctBean();
                            messagePrecinctBean.setPMessageId(id);
                            messagePrecinctBean.setMessageType("1");
                            messagePrecinctBean.setReceivePrecinctName(String.valueOf(list.get(j).getReceivePrecinctName()));
                            messagePrecinctBean.setReceivePerson(users.get(i).getTrueName());
                            messagePrecinctBean.setReceivePhone(users.get(i).getPhone());
                            precinctBeanList.add(messagePrecinctBean);
                        }
                    }
                } else {
                    MessagePrecinctBean messagePrecinctBean = new MessagePrecinctBean();
                    messagePrecinctBean.setPMessageId(id);
                    messagePrecinctBean.setMessageType("1");
                    messagePrecinctBean.setReceivePrecinctName(String.valueOf(list.get(j).getReceivePrecinctName()));
                    messagePrecinctBean.setReceivePerson(String.valueOf(list.get(j).getReceivePerson()));
                    messagePrecinctBean.setReceivePhone(String.valueOf(list.get(j).getReceivePhone()));
                    precinctBeanList.add(messagePrecinctBean);
                }
            }
            List<MessagePrecinctBean> precinctBeanList1 = new ArrayList<>(precinctBeanList);
            if (CollectionUtils.isEmpty(precinctBeanList1)){
                ret.setMessage("所选科室暂无接收人！");
                ret.setCode(ResultCodeEnum.INVALIDREQUEST.getCode());
            }else {
                messagePrecinctBeanDao.saveMessagePrecinctInfo(precinctBeanList1);

                ret.setCode(ResultCodeEnum.NORMAL.getCode());
                ret.setMessage(ResultCodeEnum.NORMAL.getName());
            }
        } catch (Exception e) {
            ret.setMessage(e.getMessage());
            ret.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("编辑消息状态")
    @RequestMapping(value = "/updateMessageType", method = RequestMethod.POST)
    @ResponseBody
    public Result updateMessageType(@RequestParam(value = "Id") String Id,
                                            @RequestParam(value = "messageType",required = false) String messageType) {
        Result result = new Result();
        messageManageDao.updateMessageType(Id, messageType);
        return result;
    }

    @ApiOperation(value = "附件上传接口")
    @PostMapping("/saveFiles")
    public Result saveFiles(@RequestParam(value = "files", required = false) MultipartFile[] files) {
        return messageManageService.saveFiles(files);
    }

    @ApiOperation(value = "查询接收人组织")
    @GetMapping("/queryMessagePrecinct")
    public List<MessagePrecinctBean> queryMessagePrecinct(@RequestParam(value = "pMessageId",required = false) String pMessageId){
        return messagePrecinctBeanDao.queryMessagePrecinct(pMessageId);
    }

    private void saveFiles(MultipartFile[] files,  List<String> savePath) {
        try {
            for (MultipartFile file : files) {
                String s = CommonUtils.uploadFile(file, filePath, urlPath);
                savePath.add(s);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    @ApiOperation(value = "已发消息列表")
    @GetMapping("/queryMessageInfo")
    public Result queryMessageInfo(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
                                   @RequestParam(value = "sendPrecinctName", required = false) String sendPrecinctName,
                                   @RequestParam(value = "sendPhone", required = false) String sendPhone) {
        Result result = new Result();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = messageManageDao.queryMessageInfo(sendPrecinctName, sendPhone);
        PageInfo pageInfo = new PageInfo(page);
        if (list.isEmpty()) {
            result.setMessage("查询结果不存在");
        } else {
            result.setData(pageInfo);
            result.setMessage("success");
        }
        return result;
    }



    @ApiOperation("查询已发消息详情")
    @RequestMapping(value = "/queryMessage1", method = RequestMethod.GET)
    @ResponseBody
    public List<MessageManage> queryMessage1(
            @RequestParam(value = "messageId", required = false) String messageId
    ) {
        return messageManageDao.queryMessage1(messageId);
    }

    @ApiOperation("统计未读消息")
    @RequestMapping(value = "/countMessage", method = RequestMethod.GET)
    @ResponseBody
    public int countMessage(@RequestParam(value = "receivePerson", required = false) String receivePerson) {
        return messagePrecinctBeanDao.countMessage(receivePerson);
    }

    @ApiOperation("删除消息")
    @RequestMapping(value = "/deleteMessage",method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteMessage(@RequestParam(value = "messageId") String messageId){
        messageManageDao.deleteMessage(messageId);
    }

    @ApiOperation("查询接收消息的组织和人")
    @RequestMapping(value = "/queryMessageUser",method = RequestMethod.GET)
    @ResponseBody
    public List<MessagePrecinct> queryMessageUser(@RequestParam(value = "precinctName", required = false) String precinctName,
                                                  @RequestParam(value = "roleId", required = false) String roleId){
      return  messageManageDao.queryMessageUser(precinctName,roleId);
    }

}
