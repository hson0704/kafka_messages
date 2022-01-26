package database.Entity;

import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
public class Comment {
    private String id;
    private long creation_time;
    private String post_id;
    private String content;
    private String owner_id;
    private String parent_id;
    private Date creat_at;
    private Date update_at;
    private int count;

    public Comment() {
    }

    public Comment(JSONObject _comment) {
        this.id = _comment.get("id").toString();
        this.creation_time = Long.parseLong(_comment.get("creation_time").toString());
        this.post_id = _comment.get("post_id").toString();
        this.content = _comment.get("content").toString();
        this.owner_id = _comment.get("owner_id").toString();
        this.parent_id = ((_comment.has("parent_id")? _comment.get("parent_id").toString() : null));
        this.creat_at = null;
        this.update_at = null;
        this.count = 1;
    }

    public Comment(String id, long creation_time, String post_id,
                   String content, String owner_id, String parent_id,
                   Date creat_at, Date update_at, int count) {
        this.id = id;
        this.creation_time = creation_time;
        this.post_id = post_id;
        this.content = content;
        this.owner_id = owner_id;
        this.parent_id = parent_id;
        this.creat_at = creat_at;
        this.update_at = update_at;
        this.count = count;
    }

    @Id
    @Column(name = "id", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "creation_time", nullable = false)
    public long getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(long creation_time) {
        this.creation_time = creation_time;
    }

    @Column(name = "post_id", nullable = false)
    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @Column(name = "content", nullable = false, columnDefinition = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "owner_id")
    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    @Column(name = "parent_id")
    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Column(name = "creat_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(Date creat_at) {
        this.creat_at = creat_at;
    }

    @Column(name = "update_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    @Column(name = "count", columnDefinition = "int default 1")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
