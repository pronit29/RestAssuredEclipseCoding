package POJOClasses;

import java.util.List;

public class CoursesPojo {

    private List<WebAutomationPojo> webAutomation;
    private List<APIPojo> api;
    private List<MobilePojo> mobile;

    public List<WebAutomationPojo> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<WebAutomationPojo> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<APIPojo> getApi() {
        return api;
    }

    public void setApi(List<APIPojo> api) {
        this.api = api;
    }

    public List<MobilePojo> getMobile() {
        return mobile;
    }

    public void setMobile(List<MobilePojo> mobile) {
        this.mobile = mobile;
    }
}
