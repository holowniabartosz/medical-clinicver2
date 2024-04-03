package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.service.patient_service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    @Operation(summary = "Get the list of all patients")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class)))
                    })
    })
    @GetMapping
    public Page<PatientDTO> findAll(@RequestParam("page") int page, @RequestParam("size") int size, Pageable pageable) {
        return patientService.findAll(pageable);
    }

    @Operation(summary = "Get a patient by its email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the patient",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content)
    })
    @GetMapping("/{email}")
    public PatientDTO findByEmail(@PathVariable String email) {
        return patientService.findByEmail(email);
    }

    @Operation(summary = "Add a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Entity with this email already exists",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO save(@RequestBody PatientDTOwithPassword patientDTOwithPassword) {
        return patientService
                .save(patientDTOwithPassword);
    }

    @Operation(summary = "Delete a patient by its email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content)
    })
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteByEmail(@PathVariable String email) {
        patientService
                .deleteByEmail(email);
        return "Removed patient with e-mail: " + email;
    }

    @Operation(summary = "Update a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content)
    })
    @PutMapping("/{email}")
    public PatientDTO update(@PathVariable String email, @RequestBody PatientDTO patientDTO) {
        patientService.update(email, patientDTO);
        return patientService.findByEmail(patientDTO.getEmail());
    }

    @Operation(summary = "Update patient's password (find patient by email)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChangePasswordCommand.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content)
    })
    @PatchMapping("password/{email}")
    public ChangePasswordCommand updatePatientPassword(@PathVariable String email,
                                                       @RequestBody ChangePasswordCommand pass) {
        patientService.editPatientPassword(email, pass);
        return pass;
    }
}

