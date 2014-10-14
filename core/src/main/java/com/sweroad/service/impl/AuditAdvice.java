package com.sweroad.service.impl;

import com.sweroad.audit.IAuditable;
import com.sweroad.audit.IXMLConvertible;
import com.sweroad.model.Audit;
import com.sweroad.model.User;
import com.sweroad.model.VehicleType;
import com.sweroad.service.GenericManager;
import com.sweroad.service.UserSecurityAdvice;
import com.sweroad.util.XStreamUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Frank on 10/13/14.
 */
@Aspect
@Component
public class AuditAdvice {
    @Autowired
    private GenericManager<Audit, Long> auditManager;

    @Around("execution(* com.sweroad.service.GenericManager.save(..))")
    public void auditSave(ProceedingJoinPoint pjp) {
        String operation = null;
        Object[] arguments = pjp.getArgs();
        if (arguments.length > 0) {
            if (!(arguments[0] instanceof IAuditable)) {
                try {
                    pjp.proceed();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return;
            }
        } else {
            Object object = arguments[0];
            operation = determineOperation(object);
            IXMLConvertible preImage = null;
            IXMLConvertible postImage = null;
            if (!operation.equalsIgnoreCase(IAuditable.OPERATION_INSERT)
                    && object instanceof IAuditable
                    && object instanceof IXMLConvertible) {
                preImage = (IXMLConvertible) object;
            }
            try {
                Object returnValue = pjp.proceed();
                if (returnValue instanceof IAuditable
                        && returnValue instanceof IXMLConvertible) {
                    postImage = (IXMLConvertible) returnValue;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if(preImage != null || postImage != null) {
                Audit auditLog = new Audit();
                auditLog.setOperation(operation);
                if(preImage!=null) {
                    auditLog.setPreImage(XStreamUtils.getXMLFromObject(preImage, preImage.getClass().getName(),
                            preImage.getFieldsAliases(), preImage.getFieldsToBeOmitted()));
                }
                if(postImage!=null) {
                    auditLog.setPostImage(XStreamUtils.getXMLFromObject(postImage, postImage.getClass().getName(),
                            postImage.getFieldsAliases(), postImage.getFieldsToBeOmitted()));
                }
                SecurityContext sc = SecurityContextHolder.getContext();
                Authentication auth = sc.getAuthentication();
                User currentUser = UserSecurityAdvice.getCurrentUser(auth, null);
                auditLog.setUser(currentUser);
                auditLog.setAuditDate(new Date());
                auditManager.save(auditLog);
            }
        }
    }

    private String determineOperation(Object argument) {
        IAuditable auditable = (IAuditable) argument;
        if (auditable.getId() == null) {
            return IAuditable.OPERATION_INSERT;
        } else if (auditable.isRemoved()) {
            return IAuditable.OPERATION_REMOVE;
        } else {
            return IAuditable.OPERATION_UPDATE;
        }
    }
}
