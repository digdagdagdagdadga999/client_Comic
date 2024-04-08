package datdvph44632.fpoly.assignment_networking.models;

public class BinhLuan {
    private String _id;
    private String content;
    private String userId;

    private String username;
    private String date;

    private String truyenId;

    public BinhLuan() {
    }

    public BinhLuan(String _id, String content, String userId, String username, String date, String truyenId) {
        this._id = _id;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.date = date;
        this.truyenId = truyenId;
    }

    public BinhLuan(String content, String userId, String username, String date, String truyenId) {
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.date = date;
        this.truyenId = truyenId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTruyenId() {
        return truyenId;
    }

    public void setTruyenId(String truyenId) {
        this.truyenId = truyenId;
    }
}
