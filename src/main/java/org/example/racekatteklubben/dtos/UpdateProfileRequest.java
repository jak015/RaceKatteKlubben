package org.example.racekatteklubben.dtos;

public class UpdateProfileRequest {
    private String memberName;
    private String email;
    private String oldPassword;
    private String password;

    public UpdateProfileRequest(String memberName, String email, String oldPassword, String password) {
        this.memberName = memberName;
        this.email = email;
        this.oldPassword = oldPassword;
        this.password = password;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
