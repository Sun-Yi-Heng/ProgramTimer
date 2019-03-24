package sunyiheng.entity;

public class ApplicationBean {
    private int id;
    private String name;
    private String path;
    private long totalTime;

    public ApplicationBean(int id, String name, String path, long totalTime) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
