package com.bobi89.medicalclinic.model.entity;

import lombok.Getter;

@Getter
public class ChangePasswordCommand {
    private String oldPassword;
    private String newPassword;
}
