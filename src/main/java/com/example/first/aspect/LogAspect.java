package com.example.first.aspect;

import com.example.first.Util.HttpContextUtils;
import com.example.first.Util.IPUtils;
import com.example.first.annocation.Log;
import com.example.first.bean.SysLog;
import com.example.first.cluster.dao.SysLogDao;
import com.example.first.service.impl.SysLogServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Repository
public class LogAspect {
    @Autowired
    private SysLogDao sysLogDao;

    @Pointcut("@annotation(com.example.first.annocation.Log)")
    public void pointcut() {}
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - beginTime;
        saveLog(proceedingJoinPoint,time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint proceedingJoinPoint,long time) {
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if(logAnnotation != null) {
            sysLog.setOperation(logAnnotation.value());
        }
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        // 请求的方法参数值
        Object[] args = proceedingJoinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }
        // 获取request
        HttpServletRequest req = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(req));
        // 模拟一个用户名
        sysLog.setUsername("mrbird");
        sysLog.setTime((int)time);
        sysLog.setCreateTime(new Date());
        sysLog.setMethod(methodName);
        // 保存系统日志
        sysLogDao.saveSysLog(sysLog);
    }

}
