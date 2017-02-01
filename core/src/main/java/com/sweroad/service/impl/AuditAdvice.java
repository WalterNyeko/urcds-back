package com.sweroad.service.impl;

import com.sweroad.audit.IAuditable;
import com.sweroad.audit.IXMLConvertible;
import com.sweroad.model.Audit;
import com.sweroad.model.User;
import com.sweroad.service.GenericService;
import com.sweroad.service.UserService;
import com.sweroad.util.XStreamUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Frank on 10/13/14.
 */
@Aspect
@Component
public class AuditAdvice {
    @Autowired
    private GenericService<Audit, Long> auditService;
    @Autowired
    private UserService userService;

    @Around("execution(* com.sweroad.service.GenericService.save(..))")
    public Object auditSave(ProceedingJoinPoint joinPoint) {
        Object returnValue = null;
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length > 0) {
            if (!(arguments[0] instanceof IAuditable)) {
                try {
                    returnValue = joinPoint.proceed();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return returnValue;
            } else {
                Object object = arguments[0];
                IXMLConvertible preImage = null;
                IXMLConvertible postImage = null;
                IAuditable dbObject = null;
                IAuditable memObject = (IAuditable) object;
                GenericService gm = (GenericService) joinPoint.getTarget();
                if (memObject.getId() != null && !memObject.getId().equals(0L)) {
                    dbObject = (IAuditable) gm.get(memObject.getId());
                    try {
                        dbObject = dbObject.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                String operation = inferOperation(memObject, dbObject);
                if (!operation.equalsIgnoreCase(IAuditable.OPERATION_INSERT)
                        && object instanceof IXMLConvertible) {
                    preImage = (IXMLConvertible) object;
                }
                try {
                    returnValue = joinPoint.proceed();
                    if (returnValue instanceof IAuditable
                            && returnValue instanceof IXMLConvertible) {
                        postImage = (IXMLConvertible) returnValue;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                if (shouldAudit(operation, dbObject, memObject)) {
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
                    if (returnValue != null) {
                        User currentUser = userService.getCurrentUser();
                        auditLog.setId(0L);
                        auditLog.setOperation(operation);
                        auditLog.setEntityId(((IAuditable) returnValue).getId());
                        auditLog.setEntityName(postImage.getClassAlias());
                        auditLog.setUser(currentUser);
                        auditLog.setAuditDate(new Date());
                        auditService.save(auditLog);
                    }
                }
            }
        }
        return returnValue;
    }

    @Around("execution(* com.sweroad.service.CrashService.sav*(..))")
    public Object auditCrash(ProceedingJoinPoint joinPoint) {
        return auditSave(joinPoint);
    }

    @Around("execution(* com.sweroad.service.PatientService.sav*(..))")
    public Object auditPatient(ProceedingJoinPoint joinPoint) {
        return auditSave(joinPoint);
    }

    private String inferOperation(IAuditable memObject, IAuditable dbObject) {
        if (dbObject == null) {
            return IAuditable.OPERATION_INSERT;
        } else if (memObject.isRemoved() && !dbObject.isRemoved()) {
            return IAuditable.OPERATION_REMOVE;
        } else if (!memObject.isRemoved() && dbObject.isRemoved()) {
            return IAuditable.OPERATION_RESTORE;
        } else {
            return IAuditable.OPERATION_UPDATE;
        }
    }

    private boolean shouldAudit(String operation, IAuditable dbObject, IAuditable memObject) {
        if (operation.equals(IAuditable.OPERATION_INSERT)) {
            return true;
        }
        if (memObject == null) {
            return false;
        }
        if (operation.equals(IAuditable.OPERATION_UPDATE)) {
            if (dbObject == null || memObject == null) {
                return false;
            }
            return dbObject.isUpdated(memObject);
        }
        return true;
    }
}
