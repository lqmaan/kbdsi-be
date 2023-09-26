package icstar.kbdsi.apps.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name="budgeting")
public class Budgeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="budget_id")
    private long budgetId;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="category")
    private long categoryId;

    @Column(name="amount")
    private Integer amount;

    @Column(name="description", length = 255)
    private String description;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name="createdAt")
    private Instant createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name="updatedAt")
    private Instant updatedAt;

    @Column(name="isDeleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name="updatedBy")
    private String updatedBy;

    public Budgeting() {
    }
    public Budgeting(String name, String type, long categoryId, Integer amount, String description) {
        this.name = name;
        this.type = type;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
