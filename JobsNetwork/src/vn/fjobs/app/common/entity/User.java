package vn.fjobs.app.common.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.fjobs.app.Constant;

public class User implements Serializable {

    private static final long serialVersionUID = 2706582719334413978L;
    private String name;
    private String email;
    private String password;
    private Date birthday;
    private int gender = Constant.GENDER_MALE;
    private String avatar;
    private boolean isReceiveEmailNotification = false;

    public User() {
    }

    public User(String name, String email, String password, Date birthday, int gender) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
    }

    public User(String name, Date birthday, int gender, String avatar) {
        super();
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isReceiveEmailNotification() {
        return isReceiveEmailNotification;
    }

    public void setReceiveEmailNotification(boolean receiveEmailNotification) {
        isReceiveEmailNotification = receiveEmailNotification;
    }

    @Override
    public String toString() {
        return "email=" + this.email + ", name=" + this.name + ", gender="
                + this.gender + ", birthday=" + this.birthday;
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(birthday);

        Calendar calendarAge = Calendar.getInstance(Locale.getDefault());
        int age = calendarAge.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (calendar.get(Calendar.MONTH) > calendarAge.get(Calendar.MONTH)
                || (calendar.get(Calendar.MONTH) == calendarAge.get(Calendar.MONTH) && calendar
                .get(Calendar.DATE) > calendarAge.get(Calendar.DATE))) {
            age--;
        }
        return age;
    }
}
