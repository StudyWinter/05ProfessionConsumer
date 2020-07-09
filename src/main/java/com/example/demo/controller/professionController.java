package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class professionController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/register")
    @RequestMapping("/register")
    public String register()
    {
        return "register";
    }

    //添加专业
    @RequestMapping("/addprofession")
    public String addprofession(HttpServletRequest request, Map<String,Object> map)
    {
        String major = request.getParameter("major");
        String pid = request.getParameter("pid");
        String proname = request.getParameter("proname");
        String code = request.getParameter("code");

        String s = restTemplate.postForObject("http://localhost:9000/rest/addprofession?major="
                + major + "&pid=" + pid + "&proname=" + proname + "&code=" + code,
                null, String.class);

        if("add".equals(s))    //添加成功
        {
            map.put("msg","add");
        }
        else
        {
            map.put("msg1","fault");
        }
        return "register";
    }


    //删除专业
    @RequestMapping("/deleteprofession")
    public String deleteprofession(HttpServletRequest request,Map<String,Object> map)
    {
        String proname = request.getParameter("proname");
        String s = restTemplate.postForObject("http://localhost:9000/rest/deleteprofession?proname="
                + proname, null, String.class);

        if("delete".equals(s))   //删除成功
        {
            map.put("msg2","delete");
        }
        else
        {
            map.put("msg3","fault");
        }
        return "register";
    }

    //修改专业信息
    @RequestMapping("/updateprofession")
    public String updateprofession(HttpServletRequest request,Map<String,Object> map)
    {
        String major = request.getParameter("major");
        String proname = request.getParameter("proname");
        String code = request.getParameter("code");

        String s = restTemplate.postForObject("http://localhost:9000/rest/updateprofession?major="
                + major + "&proname=" + proname + "&code=" + code, null, String.class);

        if("update".equals(s))    //更新成功
        {
            map.put("msg4","update");
        }
        else
        {
            map.put("msg5","fault");
        }
        return "register";
    }


    //查询用户，得到一个用户信息
    @RequestMapping("/getprofession")
    public String getprofession(HttpServletRequest request,Map<String,Object> map)
    {
        String proname = request.getParameter("proname");

        Profession profession = restTemplate.postForObject("http://localhost:9000/rest/getprofession?proname="
                + proname, null, Profession.class);

        if(profession!=null)
        {
            System.out.println(profession);
            map.put("msg6","get");
        }
        else
        {
            map.put("msg7","fault");
        }
        return "register";
    }


    //查询所用用户
    @RequestMapping("getallprofession")
    public String getallprofession(Model model)
    {
        //返回一个list的json字符串
        List list = restTemplate.postForObject("http://localhost:9000/rest/getallprofession",
                null, List.class);

        //json字符串和json队长直接转换需要加入依赖 fastjson
        for(Object o:list)
        {
            JSON json=(JSON) JSON.toJSON(o);  //json字符串转成json对象
            Profession profession = JSON.toJavaObject(json, Profession.class);  //json对象转换成Profession对象
            System.out.println(profession);
        }
        return "register";
    }
}
