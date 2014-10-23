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
    public Object auditSave(ProceedingJoinPoint pjp) {
        String operation = null;
        Object[] arguments = pjp.getArgs();
        Object returnValue = null;
        if (arguments.length > 0) {
            if (!(arguments[0] instanceof IAuditable)) {
                try {
                    returnValue = pjp.proceed();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return returnValue;
            } else {
                Object object = arguments[0];
                operation = determineOperation(object);
                IXMLConvertible preImage = null;
                IXMLConvertible postImage = null;
                IAuditable memObject = null;
                IAuditable dbObject = null;
                if (!operation.equalsIgnoreCase(IAuditable.OPERATION_INSERT)
                        && object instanceof IAuditable
                        && object instanceof IXMLConvertible) {
                    memObject = (IAuditable) object;
                    GenericManager gm = (GenericManager) pjp.getTarget();
                    dbObject = (IAuditable) gm.get(memObject.getId());
                    try {
                        dbObject = dbObject.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    preImage = (IXMLConvertible) object;
                }
                try {
                    returnValue = pjp.proceed();
                    if (returnValue instanceof IAuditable
                            && returnValue instanceof IXMLConvertible) {
                        postImage = (IXMLConvertible) returnValue;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                if (shouldAudit(operation, memObject, dbObject)) {
                    Audit auditLog = new Audit();
                    auditLog.setOperation(operation);
                    if (preImage != null) {
                        auditLog.setPreImage(XStreamUtils.getXMLFromObject(preImage, preImage.getClass().getName(),
                                preImage.getFieldsAliases(), preImage.getFieldsToBeOmitted()));
                    }
                    if (postImage != null) {
                        auditLog.setPostImage(XStreamUtils.getXMLFromObject(postImage, postImage.getClass().getName(),
                                postImage.getFieldsAliases(), postImage.getFieldsToBeOmitted()));
                    }
                    SecurityContext sc = SecurityContextHolder.getContext();
                    Authentication auth = sc.getAuthentication();
                    User currentUser = UserSecurityAdvice.getCurrentUser(auth, null);
                    auditLog.setId(0L);
                    auditLog.setOperation(operation);
                    auditLog.setEntityId(memObject.getId());
                    auditLog.setEntityName(postImage.getClassAlias());
                    auditLog.setUser(currentUser);
                    auditLog.setAuditDate(new Date());
                    auditManager.save(auditLog);
                }
            }
        }
        return returnValue;
    }

    @Around("execution(* com.sweroad.service.CrashManager.saveCrash(..))")
    public Object auditCrash(ProceedingJoinPoint pjp) {
        return auditSave(pjp);
    }

    private String determineOperation(Object argument) {
        IAuditable auditable = (IAuditable) argument;
        if (auditable.getId() == null || auditable.getId().equals(0L)) {
            return IAuditable.OPERATION_INSERT;
        } else if (auditable.isRemoved()) {
            return IAuditable.OPERATION_REMOVE;
        } else {
            return IAuditable.OPERATION_UPDATE;
        }
    }

    private boolean shouldAudit(String operation, IAuditable dbObject, IAuditable memObject) {
        if(memObject == null) {
            return false;
        }
        if (dbObject != null || memObject != null) {
            if (operation.equals(IAuditable.OPERATION_UPDATE)) {
                if (dbObject == null || memObject == null) {
                    return false;
                }
                if (!dbObject.isUpdated(memObject)) {
                    return false;
                }
            }
        }
        return true;
    }
}
