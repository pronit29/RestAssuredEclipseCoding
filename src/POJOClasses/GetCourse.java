package POJOClasses;

public class GetCourse {

    private String instructor;
    private String url;
    private String services;
    private String expertise;
    private CoursesPojo courses;
    private String linkedIn;

    public String getInstructor() {
        return instructor;
    }
    public String getURL() {
        return url;
    }
    public String getServices() {
        return services;
    }
    public String getExpertise() {
        return expertise;
    }
    public CoursesPojo getCourses() {
        return courses;
    }
    public String getLinkedIn() {
        return linkedIn;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public void setURL(String url) {
        this.url = url;
    }
    public void setServices(String services) {
        this.services = services;
    }
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
    public void setCourse(CoursesPojo courses) {
        this.courses = courses;
    }
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }




}
