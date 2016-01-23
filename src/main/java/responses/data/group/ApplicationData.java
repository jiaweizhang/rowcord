package responses.data.group;

import rowcord.data.Application;

import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class ApplicationData {
    List<Application> applications;

    public ApplicationData(List<Application> applications) {
        this.applications = applications;
    }

    public List<Application> getApplications() {
        return applications;
    }
}
