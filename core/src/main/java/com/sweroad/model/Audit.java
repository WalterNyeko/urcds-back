package com.sweroad.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Frank on 10/13/14.
 */
@Entity
@Table(name = "audit")
public class Audit extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "entity_name", nullable = false)
    private String entityName;
    @Column(name = "entity_id", nullable = false)
    private Long entityId;
    @Column(name = "pre_image")
    private String preImage;
    @Column(name = "post_image")
    private String postImage;
    @Column(nullable = false)
    private String operation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "audit_date", nullable = false)
    private Date auditDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getPreImage() {
        return preImage;
    }

    public void setPreImage(String preImage) {
        this.preImage = preImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {

            return false;
        }
        if (!(o instanceof Audit)) {
            return false;
        }
        Audit other = (Audit) o;
        if (operation == null) {
            if (other.operation != null) {
                return false;
            }
        } else if (!operation.equals(other.operation)) {
            return false;
        }
        if (postImage == null) {
            if (other.postImage != null) {
                return false;
            }
        } else if (!postImage.equals(other.postImage)) {
            return false;
        }
        if (preImage == null) {
            if (other.preImage != null) {
                return false;
            }
        } else if (!preImage.equals(other.preImage)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((operation == null) ? 0 : operation.hashCode());
        result = prime * result
                + ((postImage == null) ? 0 : postImage.hashCode());
        result = prime * result
                + ((preImage == null) ? 0 : preImage.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Auditable [preImage=" + preImage + ", postImage=" + postImage
                + ", operation=" + operation + "]";
    }

}
