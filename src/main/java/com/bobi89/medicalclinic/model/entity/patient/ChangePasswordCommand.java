package com.bobi89.medicalclinic.model.entity.patient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangePasswordCommand {
    private final String oldPassword;
    private final String newPassword;
}
