package icstar.kbdsi.apps.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(name="category", columnNames = {"category_name"})})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private long categoryId;

    @Column(name="category_name")
    private String categoryName;


    @CreationTimestamp(source = SourceType.DB)
    @Column(name="createdAt")
    private Instant createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name="updatedAt")
    private Instant updatedAt;

    @Column(name="isDeleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
